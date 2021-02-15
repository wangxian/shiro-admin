package io.webapp.common.annotation;

import io.webapp.common.entity.DesensitizationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Laizp
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitization {

    /**
     * 脱敏规则类型
     */
    DesensitizationType type();

    /**
     * 附加值, 自定义正则表达式等
     */
    String[] attach() default "";
}
