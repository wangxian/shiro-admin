package com.company.project.system.controller;

import com.company.project.common.annotation.Log;
import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.entity.QueryRequest;
import com.company.project.common.exception.AdminException;
import com.company.project.common.utils.MD5Util;
import com.company.project.system.entity.User;
import com.company.project.system.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author ADMIN
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("{username}")
    public User getUser(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findUserDetail(username);
    }

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return this.userService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    @GetMapping("list")
    @RequiresPermissions("user:view")
    public AdminResponse userList(User user, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.userService.findUserDetail(user, request));
        return new AdminResponse().success().data(dataTable);
    }

    @Log("新增用户")
    @PostMapping
    @RequiresPermissions("user:add")
    public AdminResponse addUser(@Valid User user) throws AdminException {
        try {
            this.userService.createUser(user);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "新增用户失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("删除用户")
    @GetMapping("delete/{userIds}")
    @RequiresPermissions("user:delete")
    public AdminResponse deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws AdminException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "删除用户失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("修改用户")
    @PostMapping("update")
    @RequiresPermissions("user:update")
    public AdminResponse updateUser(@Valid User user) throws AdminException {
        try {
            if (user.getUserId() == null)
                throw new AdminException("用户ID为空");
            this.userService.updateUser(user);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改用户失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @PostMapping("password/reset/{usernames}")
    @RequiresPermissions("user:password:reset")
    public AdminResponse resetPassword(@NotBlank(message = "{required}") @PathVariable String usernames) throws AdminException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "重置用户密码失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @PostMapping("password/update")
    public AdminResponse updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String newPassword) throws AdminException {
        try {
            User user = getCurrentUser();
            if (!StringUtils.equals(user.getPassword(), MD5Util.encrypt(user.getUsername(), oldPassword))) {
                throw new AdminException("原密码不正确");
            }
            userService.updatePassword(user.getUsername(), newPassword);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改密码失败，" + e.getMessage();
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("avatar/{image}")
    public AdminResponse updateAvatar(@NotBlank(message = "{required}") @PathVariable String image) throws AdminException {
        try {
            User user = getCurrentUser();
            this.userService.updateAvatar(user.getUsername(), image);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改头像失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @PostMapping("theme/update")
    public AdminResponse updateTheme(String theme, String isTab) throws AdminException {
        try {
            User user = getCurrentUser();
            this.userService.updateTheme(user.getUsername(), theme, isTab);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改系统配置失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @PostMapping("profile/update")
    public AdminResponse updateProfile(User user) throws AdminException {
        try {
            User currentUser = getCurrentUser();
            user.setUserId(currentUser.getUserId());
            this.userService.updateProfile(user);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改个人信息失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("user:export")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws AdminException {
        try {
            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
            ExcelKit.$Export(User.class, response).downXlsx(users, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }
}
