package com.company.project.system.controller;


import com.company.project.common.annotation.Log;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.entity.DeptTree;
import com.company.project.common.entity.QueryRequest;
import com.company.project.common.exception.AdminException;
import com.company.project.system.entity.Dept;
import com.company.project.system.service.IDeptService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
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
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @GetMapping("select/tree")
    public List<DeptTree<Dept>> getDeptTree() throws AdminException {
        try {
            return this.deptService.findDepts();
        } catch (Exception e) {
            String message = "获取部门树失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("tree")
    public AdminResponse getDeptTree(Dept dept) throws AdminException {
        try {
            List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
            return new AdminResponse().success().data(depts);
        } catch (Exception e) {
            String message = "获取部门树失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("dept:add")
    public AdminResponse addDept(@Valid Dept dept) throws AdminException {
        try {
            this.deptService.createDept(dept);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "新增部门失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("删除部门")
    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    public AdminResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws AdminException {
        try {
            String[] ids = deptIds.split(StringPool.COMMA);
            this.deptService.deleteDepts(ids);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "删除部门失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("修改部门")
    @PostMapping("update")
    @RequiresPermissions("dept:update")
    public AdminResponse updateDept(@Valid Dept dept) throws AdminException {
        try {
            this.deptService.updateDept(dept);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改部门失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws AdminException {
        try {
            List<Dept> depts = this.deptService.findDepts(dept, request);
            ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }
}
