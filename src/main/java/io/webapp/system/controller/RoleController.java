package io.webapp.system.controller;


import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.controller.BaseController;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.entity.QueryRequest;
import io.webapp.common.exception.AdminException;
import io.webapp.system.entity.Role;
import io.webapp.system.service.IRoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RestController
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final IRoleService roleService;

    @GetMapping
    public AdminResponse getAllRoles(Role role) {
        return new AdminResponse().success().data(roleService.findRoles(role));
    }

    @GetMapping("list")
    @RequiresPermissions("role:view")
    public AdminResponse roleList(Role role, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.roleService.findRoles(role, request));
        return new AdminResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("role:add")
    @ControllerEndpoint(operation = "新增角色", exceptionMessage = "新增角色失败")
    public AdminResponse addRole(@Valid Role role) {
        this.roleService.createRole(role);
        return new AdminResponse().success();
    }

    @GetMapping("delete/{roleIds}")
    @RequiresPermissions("role:delete")
    @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public AdminResponse deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        this.roleService.deleteRoles(roleIds);
        return new AdminResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("role:update")
    @ControllerEndpoint(operation = "修改角色", exceptionMessage = "修改角色失败")
    public AdminResponse updateRole(Role role) {
        this.roleService.updateRole(role);
        return new AdminResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("role:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Role role, HttpServletResponse response) throws AdminException {
        List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
        ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
    }

}
