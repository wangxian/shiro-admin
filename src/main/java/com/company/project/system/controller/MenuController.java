package com.company.project.system.controller;


import com.company.project.common.annotation.Log;
import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.entity.MenuTree;
import com.company.project.common.exception.AdminException;
import com.company.project.system.entity.Menu;
import com.company.project.system.entity.User;
import com.company.project.system.service.IMenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author ADMIN
 */
@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("{username}")
    public AdminResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws AdminException {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername()))
            throw new AdminException("您无权获取别人的菜单");
        MenuTree<Menu> userMenus = this.menuService.findUserMenus(username);
        return new AdminResponse().data(userMenus);
    }

    @GetMapping("tree")
    public AdminResponse getMenuTree(Menu menu) throws AdminException {
        try {
            MenuTree<Menu> menus = this.menuService.findMenus(menu);
            return new AdminResponse().success().data(menus.getChilds());
        } catch (Exception e) {
            String message = "获取菜单树失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("新增菜单/按钮")
    @PostMapping
    @RequiresPermissions("menu:add")
    public AdminResponse addMenu(@Valid Menu menu) throws AdminException {
        try {
            this.menuService.createMenu(menu);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "新增菜单/按钮失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("删除菜单/按钮")
    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    public AdminResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) throws AdminException {
        try {
            this.menuService.deleteMeuns(menuIds);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "删除菜单/按钮失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("修改菜单/按钮")
    @PostMapping("update")
    @RequiresPermissions("menu:update")
    public AdminResponse updateMenu(@Valid Menu menu) throws AdminException {
        try {
            this.menuService.updateMenu(menu);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改菜单/按钮失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    public void export(Menu menu, HttpServletResponse response) throws AdminException {
        try {
            List<Menu> menus = this.menuService.findMenuList(menu);
            ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }
}
