package com.company.project.system.controller;


import com.company.project.common.annotation.Log;
import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.entity.QueryRequest;
import com.company.project.common.exception.AdminException;
import com.company.project.system.entity.Role;
import com.company.project.system.service.IRoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

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

    @Log("新增角色")
    @PostMapping
    @RequiresPermissions("role:add")
    public AdminResponse addRole(@Valid Role role) throws AdminException {
        try {
            this.roleService.createRole(role);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "新增角色失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("删除角色")
    @GetMapping("delete/{roleIds}")
    @RequiresPermissions("role:delete")
    public AdminResponse deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) throws AdminException {
        try {
            this.roleService.deleteRoles(roleIds);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "删除角色失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("修改角色")
    @PostMapping("update")
    @RequiresPermissions("role:update")
    public AdminResponse updateRole(Role role) throws AdminException {
        try {
            this.roleService.updateRole(role);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改角色失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("role:export")
    public void export(QueryRequest queryRequest, Role role, HttpServletResponse response) throws AdminException {
        try {
            List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
            ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

}
