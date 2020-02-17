package com.company.project.system.controller;

import com.company.project.common.annotation.Limit;
import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.exception.AdminException;
import com.company.project.common.service.ValidateCodeService;
import com.company.project.common.utils.MD5Util;
import com.company.project.monitor.entity.LoginLog;
import com.company.project.monitor.service.ILoginLogService;
import com.company.project.system.entity.User;
import com.company.project.system.service.IUserService;
import com.wf.captcha.base.Captcha;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ADMIN
 */
@Validated
@RestController
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private ILoginLogService loginLogService;

    @PostMapping("login")
    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    public AdminResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String verifyCode,
            boolean rememberMe, HttpServletRequest request) throws AdminException {

        HttpSession session = request.getSession();
        validateCodeService.check(session.getId(), verifyCode);

        password = MD5Util.encrypt(username.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);

        try {
            super.login(token);

            // 保存登录日志
            LoginLog loginLog = new LoginLog();
            loginLog.setUsername(username);
            loginLog.setSystemBrowserInfo();
            this.loginLogService.saveLoginLog(loginLog);

            return new AdminResponse().success();
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            throw new AdminException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AdminException("认证失败！");
        }
    }

    @PostMapping("regist")
    public AdminResponse regist(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws AdminException {

        User user = userService.findByName(username);
        if (user != null) {
            throw new AdminException("该用户名已存在");
        }

        this.userService.regist(username, password);
        return new AdminResponse().success();
    }

    @GetMapping("index/{username}")
    public AdminResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        // 更新登录时间
        this.userService.updateLoginTime(username);
        Map<String, Object> data = new HashMap<>();

        // 获取系统访问记录
        Long totalVisitCount = this.loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);

        Long todayVisitCount = this.loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);

        Long todayIp = this.loginLogService.findTodayIp();
        data.put("todayIp", todayIp);

        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);

        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = this.loginLogService.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);

        return new AdminResponse().success().data(data);
    }

    @GetMapping("images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, AdminException {
        validateCodeService.create(request, response);
    }
}
