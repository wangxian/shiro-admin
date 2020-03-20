package io.webapp.monitor.controller;

import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.controller.BaseController;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.entity.QueryRequest;
import io.webapp.monitor.entity.SystemLog;
import io.webapp.monitor.service.ILogService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author ADMIN
 */
@Slf4j
@RestController
@RequestMapping("log")
@RequiredArgsConstructor
public class LogController extends BaseController {

    private final ILogService logService;

    @GetMapping("list")
    @RequiresPermissions("log:view")
    public AdminResponse logList(SystemLog log, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.logService.findLogs(log, request));
        return new AdminResponse().success().data(dataTable);
    }

    @GetMapping("delete/{ids}")
    @RequiresPermissions("log:delete")
    @ControllerEndpoint(exceptionMessage = "删除日志失败")
    public AdminResponse deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] logIds = ids.split(StringPool.COMMA);
        this.logService.deleteLogs(logIds);
        return new AdminResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("log:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, SystemLog lg, HttpServletResponse response) {
        List<SystemLog> logs = this.logService.findLogs(lg, request).getRecords();
        ExcelKit.$Export(SystemLog.class, response).downXlsx(logs, false);
    }
}
