package io.webapp.others.controller;

import io.webapp.common.controller.BaseController;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.entity.QueryRequest;
import io.webapp.others.entity.DataPermissionTest;
import io.webapp.others.service.IDataPermissionTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("datapermission/test")
@RequiredArgsConstructor
public class DataPermissionTestController extends BaseController {

    private final IDataPermissionTestService dataPermissionTestService;

    @GetMapping("list")
    @RequiresPermissions("others:datapermission")
    public AdminResponse dataPermissionTestList(QueryRequest request, DataPermissionTest dataPermissionTest) {
        Map<String, Object> dataTable = getDataTable(this.dataPermissionTestService.findDataPermissionTests(request, dataPermissionTest));
        return new AdminResponse().success().data(dataTable);
    }
}
