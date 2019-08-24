package com.company.project.monitor.controller;

import com.company.project.common.entity.AdminResponse;
import com.company.project.common.exception.AdminException;
import com.company.project.common.utils.DateUtil;
import com.company.project.monitor.endpoint.AdminHttpTraceEndpoint;
import com.company.project.monitor.entity.AdminHttpTrace;
import com.company.project.monitor.helper.AdminActuatorHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.project.monitor.endpoint.AdminHttpTraceEndpoint.AdminHttpTraceDescriptor;

/**
 * @author ADMIN
 */
@Slf4j
@RestController
@RequestMapping("admin/actuator")
public class AdminActuatorController {

    @Autowired
    private AdminHttpTraceEndpoint httpTraceEndpoint;

    @Autowired
    private AdminActuatorHelper actuatorHelper;

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    public AdminResponse httpTraces(String method, String url) throws AdminException {
        try {
            AdminHttpTraceDescriptor traces = httpTraceEndpoint.traces();
            List<HttpTrace> httpTraceList = traces.getTraces();
            List<AdminHttpTrace> adminHttpTraces = new ArrayList<>();

            httpTraceList.forEach(t -> {
                AdminHttpTrace adminHttpTrace = new AdminHttpTrace();
                adminHttpTrace.setRequestTime(DateUtil.formatInstant(t.getTimestamp(), DateUtil.FULL_TIME_SPLIT_PATTERN));
                adminHttpTrace.setMethod(t.getRequest().getMethod());
                adminHttpTrace.setUrl(t.getRequest().getUri());
                adminHttpTrace.setStatus(t.getResponse().getStatus());
                adminHttpTrace.setTimeTaken(t.getTimeTaken());

                if (StringUtils.isNotBlank(method) && StringUtils.isNotBlank(url)) {
                    if (StringUtils.equalsIgnoreCase(method, adminHttpTrace.getMethod())
                            && StringUtils.containsIgnoreCase(adminHttpTrace.getUrl().toString(), url)) {
                        adminHttpTraces.add(adminHttpTrace);
                    }
                } else if (StringUtils.isNotBlank(method)) {
                    if (StringUtils.equalsIgnoreCase(method, adminHttpTrace.getMethod())) {
                        adminHttpTraces.add(adminHttpTrace);
                    }
                } else if (StringUtils.isNotBlank(url)) {
                    if (StringUtils.containsIgnoreCase(adminHttpTrace.getUrl().toString(), url)) {
                        adminHttpTraces.add(adminHttpTrace);
                    }
                } else {
                    adminHttpTraces.add(adminHttpTrace);
                }
            });

            Map<String, Object> data = new HashMap<>();
            data.put("rows", adminHttpTraces);
            data.put("total", adminHttpTraces.size());

            return new AdminResponse().success().data(data);
        } catch (Exception e) {
            String message = "请求追踪失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }
}
