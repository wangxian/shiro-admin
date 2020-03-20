package com.company.project.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.company.project.common.annotation.ControllerEndpoint;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.entity.DeptTree;
import com.company.project.common.entity.QueryRequest;
import com.company.project.common.exception.AdminException;
import com.company.project.system.entity.Dept;
import com.company.project.system.service.IDeptService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("dept")
@RequiredArgsConstructor
public class DeptController {

    private final IDeptService deptService;

    @GetMapping("select/tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public List<DeptTree<Dept>> getDeptTree() throws AdminException {
        return this.deptService.findDepts();
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public AdminResponse getDeptTree(Dept dept) throws AdminException {
        List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
        return new AdminResponse().success().data(depts);
    }

    @PostMapping
    @RequiresPermissions("dept:add")
    @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    public AdminResponse addDept(@Valid Dept dept) {
        this.deptService.createDept(dept);
        return new AdminResponse().success();
    }

    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    @ControllerEndpoint(operation = "删除部门", exceptionMessage = "删除部门失败")
    public AdminResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws AdminException {
        String[] ids = deptIds.split(StringPool.COMMA);
        this.deptService.deleteDepts(ids);
        return new AdminResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("dept:update")
    @ControllerEndpoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    public AdminResponse updateDept(@Valid Dept dept) throws AdminException {
        this.deptService.updateDept(dept);
        return new AdminResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws AdminException {
        List<Dept> depts = this.deptService.findDepts(dept, request);
        ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
    }
}
