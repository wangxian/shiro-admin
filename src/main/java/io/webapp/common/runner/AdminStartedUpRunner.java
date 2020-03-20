package io.webapp.common.runner;

import io.webapp.common.properties.AdminProperties;
import io.webapp.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author ADMIN
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminStartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final AdminProperties adminProperties;
    private final RedisService redisService;

    @Value("${server.port:8080}")
    private String port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 测试 Redis连接是否正常
            redisService.hasKey("admin_test");
        } catch (Exception e) {
            log.error(" ____   __    _   _ ");
            log.error("| |_   / /\\  | | | |");
            log.error("|_|   /_/--\\ |_| |_|__");
            log.error("                        ");
            log.error("ADMIN 启动失败，{}", e.getMessage());
            log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");

            // 关闭 ADMIN
            context.close();
        }

        if (context.isActive()) {
            InetAddress address = InetAddress.getLocalHost();
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            String loginUrl = adminProperties.getShiro().getLoginUrl();
            if (StringUtils.isNotBlank(contextPath)) {
                url += contextPath;
            }

            if (StringUtils.isNotBlank(loginUrl)) {
                url += loginUrl;
            }

            log.info(" __    ___   _      ___   _     ____ _____  ____ ");
            log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
            log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
            log.info("                                                      ");
            log.info("ADMIN 权限系统启动完毕，地址：{}", url);
        }
    }
}
