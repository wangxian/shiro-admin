package io.webapp.common.authentication;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.webapp.common.entity.AdminConstant;
import io.webapp.common.service.RedisService;
import io.webapp.system.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.PrincipalCollection;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ShiroLogoutService {

    private final RedisService redisService;

    @Async(AdminConstant.ADMIN_SHIRO_THREAD_POOL)
    public void cleanCacheFragment(PrincipalCollection principals) {
        User principal = (User) principals.getPrimaryPrincipal();
        String key = RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX
                + ShiroRealm.class.getName()
                + StringPool.DOT + "authenticationCache" + StringPool.COLON + principal.getId();

        redisService.del(key);
        log.info("登出，异步清理用户缓存，cache key: [{}]", key);
    }
}
