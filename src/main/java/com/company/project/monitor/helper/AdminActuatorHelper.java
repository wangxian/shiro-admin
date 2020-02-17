package com.company.project.monitor.helper;

import com.company.project.monitor.endpoint.AdminMetricsEndpoint;
import com.company.project.common.annotation.Helper;
import com.company.project.common.utils.DateUtil;
import com.company.project.monitor.entity.JvmInfo;
import com.company.project.monitor.entity.ServerInfo;
import com.company.project.monitor.entity.TomcatInfo;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ADMIN
 */
@Helper
public class AdminActuatorHelper {

    private static final BigDecimal DECIMAL = new BigDecimal("1048576");

    @Autowired
    private AdminMetricsEndpoint metricsEndpoint;

    public List<AdminMetricsEndpoint.FebsMetricResponse> getMetricResponseByType(String type) {
        AdminMetricsEndpoint.ListNamesResponse listNames = metricsEndpoint.listNames();
        Set<String> names = listNames.getNames();

        Iterable<String> jvm = names.stream()
                .filter(Predicates.containsPattern(type)::apply)
                .collect(Collectors.toList());

        List<AdminMetricsEndpoint.FebsMetricResponse> metricResponseList = new ArrayList<>();

        jvm.forEach(s -> {
            AdminMetricsEndpoint.FebsMetricResponse metric = metricsEndpoint.metric(s, null);
            metricResponseList.add(metric);
        });
        return metricResponseList;
    }

    public JvmInfo getJvmInfoFromMetricData(List<AdminMetricsEndpoint.FebsMetricResponse> metrics) {
        JvmInfo jvmInfo = new JvmInfo();
        metrics.forEach(d -> {
            String name = d.getName();

            AdminMetricsEndpoint.Sample sample = d.getMeasurements().get(0);
            Double value = sample.getValue();

            switch (name) {
                case "jvm.memory.max":
                    jvmInfo.setJvmMemoryMax(convertToMB(value));
                    break;
                case "jvm.memory.committed":
                    jvmInfo.setJvmMemoryCommitted(convertToMB(value));
                    break;
                case "jvm.memory.used":
                    jvmInfo.setJvmMemoryUsed(convertToMB(value));
                    break;
                case "jvm.buffer.memory.used":
                    jvmInfo.setJvmBufferMemoryUsed(convertToMB(value));
                    break;
                case "jvm.buffer.count":
                    jvmInfo.setJvmBufferCount(value);
                    break;
                case "jvm.threads.daemon":
                    jvmInfo.setJvmThreadsdaemon(value);
                    break;
                case "jvm.threads.live":
                    jvmInfo.setJvmThreadsLive(value);
                    break;
                case "jvm.threads.peak":
                    jvmInfo.setJvmThreadsPeak(value);
                    break;
                case "jvm.classes.loaded":
                    jvmInfo.setJvmClassesLoaded(value);
                    break;
                case "jvm.classes.unloaded":
                    jvmInfo.setJvmClassesUnloaded(value);
                    break;
                case "jvm.gc.memory.allocated":
                    jvmInfo.setJvmGcMemoryAllocated(convertToMB(value));
                    break;
                case "jvm.gc.memory.promoted":
                    jvmInfo.setJvmGcMemoryPromoted(convertToMB(value));
                    break;
                case "jvm.gc.max.data.size":
                    jvmInfo.setJvmGcMaxDataSize(convertToMB(value));
                    break;
                case "jvm.gc.live.data.size":
                    jvmInfo.setJvmGcLiveDataSize(convertToMB(value));
                    break;
                default:
            }
        });
        return jvmInfo;
    }

    public TomcatInfo getTomcatInfoFromMetricData(List<AdminMetricsEndpoint.FebsMetricResponse> metrics) {
        TomcatInfo tomcatInfo = new TomcatInfo();

        metrics.forEach(d -> {
            String name = d.getName();
            AdminMetricsEndpoint.Sample sample = d.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "tomcat.sessions.created":
                    tomcatInfo.setTomcatSessionsCreated(value);
                    break;
                case "tomcat.sessions.expired":
                    tomcatInfo.setTomcatSessionsExpired(value);
                    break;
                case "tomcat.sessions.active.current":
                    tomcatInfo.setTomcatSessionsActiveCurrent(value);
                    break;
                case "tomcat.sessions.active.max":
                    tomcatInfo.setTomcatSessionsActiveMax(value);
                    break;
                case "tomcat.sessions.rejected":
                    tomcatInfo.setTomcatSessionsRejected(value);
                    break;
                case "tomcat.global.error":
                    tomcatInfo.setTomcatGlobalError(value);
                    break;
                case "tomcat.global.sent":
                    tomcatInfo.setTomcatGlobalSent(value);
                    break;
                case "tomcat.global.request.max":
                    tomcatInfo.setTomcatGlobalRequestMax(value);
                    break;
                case "tomcat.threads.current":
                    tomcatInfo.setTomcatThreadsCurrent(value);
                    break;
                case "tomcat.threads.config.max":
                    tomcatInfo.setTomcatThreadsConfigMax(value);
                    break;
                case "tomcat.threads.busy":
                    tomcatInfo.setTomcatThreadsBusy(value);
                    break;
                default:
            }
        });
        return tomcatInfo;
    }

    public ServerInfo getServerInfoFromMetricData(List<AdminMetricsEndpoint.FebsMetricResponse> jdbcInfo,
                                                  List<AdminMetricsEndpoint.FebsMetricResponse> systemInfo,
                                                  List<AdminMetricsEndpoint.FebsMetricResponse> processInfo) {
        ServerInfo serverInfo = new ServerInfo();

        jdbcInfo.forEach(j -> {
            String name = j.getName();
            AdminMetricsEndpoint.Sample sample = j.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "jdbc.connections.active":
                    serverInfo.setJdbcConnectionsActive(value);
                    break;
                case "jdbc.connections.max":
                    serverInfo.setJdbcConnectionsMax(value);
                    break;
                case "jdbc.connections.min":
                    serverInfo.setJdbcConnectionsMin(value);
                    break;
                default:
            }
        });

        systemInfo.forEach(s -> {
            String name = s.getName();
            AdminMetricsEndpoint.Sample sample = s.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "system.cpu.count":
                    serverInfo.setSystemCpuCount(value);
                    break;
                case "system.cpu.usage":
                    serverInfo.setSystemCpuUsage(value);
                    break;
                default:
            }
        });

        processInfo.forEach(p -> {
            String name = p.getName();
            AdminMetricsEndpoint.Sample sample = p.getMeasurements().get(0);
            Double value = sample.getValue();
            switch (name) {
                case "process.cpu.usage":
                    serverInfo.setProcessCpuUsage(value);
                    break;
                case "process.uptime":
                    serverInfo.setProcessUptime(value);
                    break;
                case "process.start.time":
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(20);
                    numberFormat.setGroupingUsed(false);
                    long timeMillis = Long.parseLong(StringUtils.replace(numberFormat.format(value), ".", ""));
                    String startTime = DateUtil.getDateFormat(new Date(timeMillis), DateUtil.FULL_TIME_SPLIT_PATTERN);
                    serverInfo.setProcessStartTime(startTime);
                default:
            }
        });
        return serverInfo;
    }

    private static Double convertToMB(Object value) {
        return new BigDecimal(String.valueOf(value))
                .divide(DECIMAL, 3, RoundingMode.HALF_UP).doubleValue();
    }
}
