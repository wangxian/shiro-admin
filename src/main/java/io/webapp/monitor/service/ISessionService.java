package io.webapp.monitor.service;

import io.webapp.monitor.entity.ActiveUser;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.List;

/**
 * @author ADMIN
 */
public interface ISessionService {

    /**
     * 获取在线用户列表
     *
     * @param username 用户名
     * @return List<ActiveUser>
     */
    List<ActiveUser> list(String username);

    /**
     * 踢出用户
     *
     * @param sessionId sessionId
     */
    void forceLogout(String sessionId);

    /**
     * 通过用户ID获取Principal集合
     *
     * @param userId 用户ID
     * @return List<SimplePrincipalCollection>
     */
    List<SimplePrincipalCollection> getPrincipals(Long userId);
}
