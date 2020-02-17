package com.company.project.common.configure;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ADMIN
 */
@Configuration
public class AdminMonitorConfigure {

    @Bean
    public HttpTraceRepository inMemoryHttpTraceRepository(){
        return new InMemoryHttpTraceRepository();
    }
}
