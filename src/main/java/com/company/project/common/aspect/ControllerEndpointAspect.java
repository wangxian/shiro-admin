package com.company.project.common.aspect;

import com.company.project.common.annotation.ControllerEndpoint;
import com.company.project.common.exception.AdminException;
import com.company.project.common.utils.AdminUtil;
import com.company.project.common.utils.HttpContextUtil;
import com.company.project.monitor.service.ILogService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author ADMIN
 */
@Aspect
@Component
public class ControllerEndpointAspect extends AspectSupport {

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.company.project.common.annotation.ControllerEndpoint)")
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
                HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
                logService.saveLog(point, targetMethod, request, operation, start);
            }
            return result;
        } catch (Throwable throwable) {
            String exceptionMessage = annotation.exceptionMessage();
            String message = throwable.getMessage();
            String error = AdminUtil.containChinese(message) ? exceptionMessage + "，" + message : exceptionMessage;
            throw new AdminException(error);
        }
    }
}
