package com.company.project.system.controller;

import com.company.project.common.authentication.ShiroHelper;
import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminConstant;
import com.company.project.common.utils.DateUtil;
import com.company.project.common.utils.AdminUtil;
import com.company.project.system.entity.User;
import com.company.project.system.service.IUserService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ViewController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ShiroHelper shiroHelper;

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
        AuthorizationInfo authorizationInfo = shiroHelper.getCurrentuserAuthorizationInfo();
        User user = super.getCurrentUser();
        user.setPassword("It's a secret");
        model.addAttribute("user", userService.findByName(user.getUsername())); // 获取实时的用户信息
        model.addAttribute("permissions", authorizationInfo.getStringPermissions());
        model.addAttribute("roles",authorizationInfo.getRoles());
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
            if (User.SEX_MALE.equals(sex)) user.setSex("男");
            else if (User.SEX_FEMALE.equals(sex)) user.setSex("女");
            else user.setSex("保密");
        }
        if (user.getLastLoginTime() != null)
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
    }
}