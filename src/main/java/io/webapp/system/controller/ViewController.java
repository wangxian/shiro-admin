package io.webapp.system.controller;

import io.webapp.common.authentication.ShiroHelper;
import io.webapp.common.controller.BaseController;
import io.webapp.common.entity.AdminConstant;
import io.webapp.common.utils.AdminUtil;
import io.webapp.common.utils.DateUtil;
import io.webapp.system.entity.User;
import io.webapp.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ADMIN
 */
@Controller("systemView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IUserService userService;
    private final ShiroHelper shiroHelper;

    @GetMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request) {

        if (AdminUtil.isAjaxRequest(request)) {
            throw new ExpiredSessionException();
        } else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName(AdminUtil.view("login"));
            return mav;
        }
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return AdminUtil.view("error/403");
    }


    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        AuthorizationInfo authorizationInfo = shiroHelper.getCurrentUserAuthorizationInfo();

        User user = super.getCurrentUser();
        User currentUserDetail = userService.findByName(user.getUsername());
        currentUserDetail.setPassword("It's a secret");

        model.addAttribute("user", currentUserDetail);
        model.addAttribute("permissions", authorizationInfo.getStringPermissions());
        model.addAttribute("roles", authorizationInfo.getRoles());

        return "index";
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "layout")
    public String layout() {
        return AdminUtil.view("layout");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return AdminUtil.view("system/user/passwordUpdate");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "user/profile")
    public String userProfile() {
        return AdminUtil.view("system/user/userProfile");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "user/avatar")
    public String userAvatar() {
        return AdminUtil.view("system/user/avatar");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "user/profile/update")
    public String profileUpdate() {
        return AdminUtil.view("system/user/profileUpdate");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "system/user")
    @RequiresPermissions("user:view")
    public String systemUser() {
        return AdminUtil.view("system/user/user");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "system/user/add")
    @RequiresPermissions("user:add")
    public String systemUserAdd() {
        return AdminUtil.view("system/user/userAdd");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "system/user/detail/{username}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return AdminUtil.view("system/user/userDetail");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "system/user/update/{username}")
    @RequiresPermissions("user:update")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return AdminUtil.view("system/user/userUpdate");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "system/role")
    @RequiresPermissions("role:view")
    public String systemRole() {
        return AdminUtil.view("system/role/role");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "system/menu")
    @RequiresPermissions("menu:view")
    public String systemMenu() {
        return AdminUtil.view("system/menu/menu");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "system/dept")
    @RequiresPermissions("dept:view")
    public String systemDept() {
        return AdminUtil.view("system/dept/dept");
    }

    @RequestMapping(AdminConstant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return AdminUtil.view("index");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "404")
    public String error404() {
        return AdminUtil.view("error/404");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "403")
    public String error403() {
        return AdminUtil.view("error/403");
    }

    @GetMapping(AdminConstant.VIEW_PREFIX + "500")
    public String error500() {
        return AdminUtil.view("error/500");
    }

    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        model.addAttribute("user", user);

        if (transform) {
            String sex = user.getSex();
            if (User.SEX_MALE.equals(sex)) {
                user.setSex("男");
            } else if (User.SEX_FEMALE.equals(sex)) {
                user.setSex("女");
            } else {
                user.setSex("保密");
            }
        }

        if (user.getLastLoginTime() != null) {
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        }
    }
}
