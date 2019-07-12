package com.company.project.monitor.controller;

import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.entity.QueryRequest;
import com.company.project.common.exception.AdminException;
import com.company.project.monitor.entity.Log;
import com.company.project.monitor.service.ILogService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @GetMapping("list")
    @RequiresPermissions("log:view")
    public AdminResponse logList(Log log, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.logService.findLogs(log, request));
        return new AdminResponse().success().data(dataTable);
    }

    @GetMapping("delete/{ids}")
    @RequiresPermissions("log:delete")
    public AdminResponse deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) throws AdminException {
        try {
            String[] logIds = ids.split(StringPool.COMMA);
            this.logService.deleteLogs(logIds);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "删除日志失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("log:export")
    public void export(QueryRequest request, Log lg, HttpServletResponse response) throws AdminException {
        try {
            List<Log> logs = this.logService.findLogs(lg, request).getRecords();
            ExcelKit.$Export(Log.class, response).downXlsx(logs, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }
}
