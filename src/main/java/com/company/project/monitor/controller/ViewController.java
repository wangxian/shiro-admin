package com.company.project.monitor.controller;

import com.company.project.common.entity.AdminConstant;
import com.company.project.common.utils.AdminUtil;
import com.company.project.monitor.entity.JvmInfo;
import com.company.project.monitor.entity.ServerInfo;
import com.company.project.monitor.entity.TomcatInfo;
import com.company.project.monitor.helper.AdminActuatorHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.company.project.monitor.endpoint.AdminMetricsEndpoint.FebsMetricResponse;

/**
 * @author ADMIN
 */
@Controller("monitorView")
@RequestMapping(AdminConstant.VIEW_PREFIX + "monitor")
public class ViewController {

    @Autowired
    private AdminActuatorHelper actuatorHelper;

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
        List<FebsMetricResponse> jvm = actuatorHelper.getMetricResponseByType("jvm");
        JvmInfo jvmInfo = actuatorHelper.getJvmInfoFromMetricData(jvm);
        model.addAttribute("jvm", jvmInfo);
        return AdminUtil.view("monitor/jvmInfo");
    }

    @GetMapping("tomcat")
    @RequiresPermissions("tomcat:view")
    public String tomcatInfo(Model model) {
        List<FebsMetricResponse> tomcat = actuatorHelper.getMetricResponseByType("tomcat");
        TomcatInfo tomcatInfo = actuatorHelper.getTomcatInfoFromMetricData(tomcat);
        model.addAttribute("tomcat", tomcatInfo);
        return AdminUtil.view("monitor/tomcatInfo");
    }

    @GetMapping("server")
    @RequiresPermissions("server:view")
    public String serverInfo(Model model) {
        List<FebsMetricResponse> jdbcInfo = actuatorHelper.getMetricResponseByType("jdbc");
        List<FebsMetricResponse> systemInfo = actuatorHelper.getMetricResponseByType("system");
        List<FebsMetricResponse> processInfo = actuatorHelper.getMetricResponseByType("process");

        ServerInfo serverInfo = actuatorHelper.getServerInfoFromMetricData(jdbcInfo, systemInfo, processInfo);
        model.addAttribute("server", serverInfo);
        return AdminUtil.view("monitor/serverInfo");
    }
}
