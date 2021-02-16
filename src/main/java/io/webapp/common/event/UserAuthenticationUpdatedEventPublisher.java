package io.webapp.common.event;

import io.webapp.common.annotation.Publisher;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * {@link UserAuthenticationUpdatedEvent}事件发布器
 */
@Getter
@Publisher
public class UserAuthenticationUpdatedEventPublisher implements ApplicationEventPublisherAware, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 广播{@link UserAuthenticationUpdatedEvent}事件
     *
     * @param userId 用户ID集合
     */
    public void publishEvent(Set<Long> userId) {
        UserAuthenticationUpdatedEvent event = new UserAuthenticationUpdatedEvent(applicationContext);
        event.setUserIds(userId);
        applicationEventPublisher.publishEvent(event);
    }
}
