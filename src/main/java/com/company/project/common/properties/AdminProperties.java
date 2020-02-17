package com.company.project.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ADMIN
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:admin.properties"})
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {
    private ShiroProperties shiro = new ShiroProperties();
    private SwaggerProperties swagger = new SwaggerProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private int maxBatchInsertNum = 1000;
}
