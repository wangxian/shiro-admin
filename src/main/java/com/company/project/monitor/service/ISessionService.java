package com.company.project.monitor.service;

import com.company.project.monitor.entity.ActiveUser;

import java.util.List;

/**
 * @author MrBird
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
}
