package io.webapp.common.configure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.webapp.common.interceptor.DataPermissionInterceptor;
import io.webapp.common.interceptor.DesensitizationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author ADMIN
 */
@MapperScan("io.webapp.*.mapper")
@Configuration(proxyBeanMethods = false)
public class MybatisPlusConfigure {
    /**
     * 分页插件
     */
    @Bean
    @Order(-2)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    /**
     * 注册数据权限
     */
    @Bean
    @Order(-1)
    public DataPermissionInterceptor dataPermissionInterceptor() {
        return new DataPermissionInterceptor();
    }

    /**
     * 数据脱敏
     */
    @Bean
    @Order(-1)
    public DesensitizationInterceptor desensitizationInterceptor() {
        return new DesensitizationInterceptor();
    }
}
