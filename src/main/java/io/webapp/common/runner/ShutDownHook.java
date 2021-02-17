package io.webapp.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShutDownHook {
    @EventListener(classes = {ContextClosedEvent.class})
    public void onApplicationClosed(@NonNull ApplicationEvent event) {
        log.info("ADMIN管理系统已关闭，Bye！");
    }
}
