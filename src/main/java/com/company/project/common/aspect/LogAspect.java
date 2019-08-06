package com.company.project.common.aspect;

import com.company.project.common.properties.AdminProperties;
import com.company.project.monitor.entity.Log;
import com.company.project.monitor.service.ILogService;
import com.company.project.system.entity.User;
import com.company.project.common.utils.HttpContextUtil;
import com.company.project.common.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author ADMIN
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private AdminProperties adminProperties;

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.company.project.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();

        // 执行方法
        result = point.proceed();

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

        // 设置 IP地址
        String ip = IPUtil.getIpAddr(request);

        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        if (adminProperties.isOpenAopLog()) {
            // 保存日志
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Log log = new Log();

            if (user != null) {
                log.setUsername(user.getUsername());
            }

            log.setIp(ip);
            log.setTime(time);
            logService.saveLog(point, log);
        }
        return result;
    }
}
