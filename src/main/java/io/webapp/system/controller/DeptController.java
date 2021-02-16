package io.webapp.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.entity.DeptTree;
import io.webapp.common.entity.QueryRequest;
import io.webapp.common.exception.AdminException;
import io.webapp.system.entity.Dept;
import io.webapp.system.service.IDeptService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("dept")
@RequiredArgsConstructor
public class DeptController {

    private final IDeptService deptService;

    @GetMapping("select/tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public List<DeptTree<Dept>> getDeptTree() throws AdminException {
        return this.deptService.findDept();
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public AdminResponse getDeptTree(Dept dept) throws AdminException {
        List<DeptTree<Dept>> depts = this.deptService.findDept(dept);
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
        this.deptService.deleteDept(ids);
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
        List<Dept> depts = this.deptService.findDept(dept, request);
        ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
    }
}
