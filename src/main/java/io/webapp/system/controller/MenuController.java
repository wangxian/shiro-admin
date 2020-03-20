package io.webapp.system.controller;


import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.controller.BaseController;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.entity.MenuTree;
import io.webapp.common.exception.AdminException;
import io.webapp.system.entity.Menu;
import io.webapp.system.entity.User;
import io.webapp.system.service.IMenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final IMenuService menuService;

    @GetMapping("{username}")
    public AdminResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws AdminException {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername()))
            throw new AdminException("您无权获取别人的菜单");
        MenuTree<Menu> userMenus = this.menuService.findUserMenus(username);
        return new AdminResponse().data(userMenus);
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取菜单树失败")
    public AdminResponse getMenuTree(Menu menu) {
        MenuTree<Menu> menus = this.menuService.findMenus(menu);
        return new AdminResponse().success().data(menus.getChilds());
    }

    @PostMapping
    @RequiresPermissions("menu:add")
    @ControllerEndpoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public AdminResponse addMenu(@Valid Menu menu) {
        this.menuService.createMenu(menu);
        return new AdminResponse().success();
    }

    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public AdminResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        this.menuService.deleteMeuns(menuIds);
        return new AdminResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("menu:update")
    @ControllerEndpoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public AdminResponse updateMenu(@Valid Menu menu) {
        this.menuService.updateMenu(menu);
        return new AdminResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Menu menu, HttpServletResponse response) {
        List<Menu> menus = this.menuService.findMenuList(menu);
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }
}
