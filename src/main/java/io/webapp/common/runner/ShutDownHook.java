package io.webapp.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShutDownHook implements DisposableBean {

    @Override
    public void destroy() {
        log.info("ADMIN管理系统已关闭，Bye！");
    }
}
