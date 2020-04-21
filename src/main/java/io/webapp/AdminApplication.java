package io.webapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ADMIN
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@MapperScan("io.webapp.*.mapper")
public class AdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
