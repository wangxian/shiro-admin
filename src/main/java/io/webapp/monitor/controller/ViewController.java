package io.webapp.monitor.controller;

import io.webapp.common.entity.AdminConstant;
import io.webapp.common.utils.AdminUtil;
import io.webapp.monitor.entity.JvmInfo;
import io.webapp.monitor.entity.ServerInfo;
import io.webapp.monitor.entity.TomcatInfo;
import io.webapp.monitor.helper.AdminActuatorHelper;
import io.webapp.monitor.endpoint.AdminMetricsEndpoint;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ADMIN
 */
@Controller("monitorView")
@RequestMapping(AdminConstant.VIEW_PREFIX + "monitor")
@RequiredArgsConstructor
public class ViewController {

    private final AdminActuatorHelper actuatorHelper;

    @GetMapping("online")
    @RequiresPermissions("online:view")
    public String online() {
        return AdminUtil.view("monitor/online");
    }

    @GetMapping("log")
    @RequiresPermissions("log:view")
    public String log() {
        return AdminUtil.view("monitor/log");
    }

    @GetMapping("loginlog")
    @RequiresPermissions("loginlog:view")
    public String loginLog() {
        return AdminUtil.view("monitor/loginLog");
    }

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    public String httptrace() {
        return AdminUtil.view("monitor/httpTrace");
    }

    @GetMapping("jvm")
    @RequiresPermissions("jvm:view")
    public String jvmInfo(Model model) {
        List<AdminMetricsEndpoint.AdminMetricResponse> jvm = actuatorHelper.getMetricResponseByType("jvm");
        JvmInfo jvmInfo = actuatorHelper.getJvmInfoFromMetricData(jvm);
        model.addAttribute("jvm", jvmInfo);
        return AdminUtil.view("monitor/jvmInfo");
    }

    @GetMapping("tomcat")
    @RequiresPermissions("tomcat:view")
    public String tomcatInfo(Model model) {
        List<AdminMetricsEndpoint.AdminMetricResponse> tomcat = actuatorHelper.getMetricResponseByType("tomcat");
        TomcatInfo tomcatInfo = actuatorHelper.getTomcatInfoFromMetricData(tomcat);
        model.addAttribute("tomcat", tomcatInfo);
        return AdminUtil.view("monitor/tomcatInfo");
    }

    @GetMapping("server")
    @RequiresPermissions("server:view")
    public String serverInfo(Model model) {
        List<AdminMetricsEndpoint.AdminMetricResponse> jdbcInfo = actuatorHelper.getMetricResponseByType("jdbc");
        List<AdminMetricsEndpoint.AdminMetricResponse> systemInfo = actuatorHelper.getMetricResponseByType("system");
        List<AdminMetricsEndpoint.AdminMetricResponse> processInfo = actuatorHelper.getMetricResponseByType("process");

        ServerInfo serverInfo = actuatorHelper.getServerInfoFromMetricData(jdbcInfo, systemInfo, processInfo);
        model.addAttribute("server", serverInfo);
        return AdminUtil.view("monitor/serverInfo");
    }
}
