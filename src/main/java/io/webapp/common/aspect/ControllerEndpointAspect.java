package io.webapp.common.aspect;

import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.exception.AdminException;
import io.webapp.common.utils.AdminUtil;
import io.webapp.monitor.service.ILogService;
import io.webapp.system.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * @author ADMIN
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ControllerEndpointAspect extends BaseAspectSupport {

    private final ILogService logService;

    @Pointcut("@annotation(io.webapp.common.annotation.ControllerEndpoint)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws AdminException {
        Object result;
        Method targetMethod = resolveMethod(point);
        ControllerEndpoint annotation = targetMethod.getAnnotation(ControllerEndpoint.class);

        String operation = annotation.operation();
        long start = System.currentTimeMillis();

        try {
            result = point.proceed();
            if (StringUtils.isNotBlank(operation)) {
                RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) attributes;
                String ip = StringUtils.EMPTY;

                if (servletRequestAttributes != null) {
                    ip = servletRequestAttributes.getRequest().getRemoteAddr();
                }

                // 设置操作用户
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                logService.saveLog(user, point, targetMethod, ip, operation, start);
            }
            return result;
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);

            String exceptionMessage = annotation.exceptionMessage();
            String message = throwable.getMessage();
            String error = AdminUtil.containChinese(message) ? exceptionMessage + "，" + message : exceptionMessage;
            throw new AdminException(error);
        }
    }
}
