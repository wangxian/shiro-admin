package io.webapp.common.listener;

import com.google.common.base.Stopwatch;
import io.webapp.common.annotation.Listener;
import io.webapp.common.authentication.ShiroRealm;
import io.webapp.common.event.UserAuthenticationUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * {@link UserAuthenticationUpdatedEvent}事件监听器
 */
@Slf4j
@Listener
@RequiredArgsConstructor
public class UserAuthenticationUpdatedEventListener {

    private final ShiroRealm realm;

    @EventListener
    public void onUserAuthenticationUpdated(@NonNull UserAuthenticationUpdatedEvent event) {
        Set<Long> userIds = event.getUserIds();
        if (CollectionUtils.isNotEmpty(userIds)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            userIds.forEach(realm::clearCache);
            event.cleanSet(userIds);

            log.info("清理用户授权缓存 [userId: {}], which took {}", userIds, stopwatch.stop());
        }
    }
}
