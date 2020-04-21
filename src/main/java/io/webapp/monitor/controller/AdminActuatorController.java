package io.webapp.monitor.controller;

import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.utils.DateUtil;
import io.webapp.monitor.endpoint.AdminHttpTraceEndpoint;
import io.webapp.monitor.entity.AdminHttpTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ADMIN
 */
@Slf4j
@RestController
@RequestMapping("admin/actuator")
@RequiredArgsConstructor
public class AdminActuatorController {

    private final AdminHttpTraceEndpoint httpTraceEndpoint;

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    @ControllerEndpoint(exceptionMessage = "请求追踪失败")
    public AdminResponse httpTraces(String method, String url) {

        AdminHttpTraceEndpoint.AdminHttpTraceDescriptor traces = httpTraceEndpoint.traces();

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
                if (StringUtils.equalsIgnoreCase(method, adminHttpTrace.getMethod()) && StringUtils.containsIgnoreCase(adminHttpTrace.getUrl().toString(), url)) {
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

        Map<String, Object> data = new HashMap<>(2);
        data.put("rows", adminHttpTraces);
        data.put("total", adminHttpTraces.size());

        return new AdminResponse().success().data(data);
    }
}
