package io.webapp.monitor.service.impl;

import com.google.common.collect.Lists;
import io.webapp.common.util.AddressUtil;
import io.webapp.common.util.DateUtil;
import io.webapp.monitor.entity.ActiveUser;
import io.webapp.monitor.service.ISessionService;
import io.webapp.system.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.webapp.monitor.entity.ActiveUser.OFFLINE;
import static io.webapp.monitor.entity.ActiveUser.ONLINE;

/**
 * @author ADMIN
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements ISessionService {

    private final SessionDAO sessionDAO;

    @Override
    public List<ActiveUser> list(String username) {
        String currentSessionId = (String) SecurityUtils.getSubject().getSession().getId();

        List<ActiveUser> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();

        for (Session session : sessions) {
            ActiveUser activeUser = new ActiveUser();
            User user;
            SimplePrincipalCollection principalCollection;

            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                user                = (User) principalCollection.getPrimaryPrincipal();
                activeUser.setUsername(user.getUsername());
                activeUser.setUserId(user.getUserId().toString());
            }

            activeUser.setId((String) session.getId());
            activeUser.setHost(session.getHost());
            activeUser.setStartTimestamp(DateUtil.getDateFormat(session.getStartTimestamp(), DateUtil.FULL_TIME_SPLIT_PATTERN));
            activeUser.setLastAccessTime(DateUtil.getDateFormat(session.getLastAccessTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));

            long timeout = session.getTimeout();
            activeUser.setStatus(timeout == 0L ? OFFLINE : ONLINE);
            String address = AddressUtil.getCityInfo(activeUser.getHost());
            activeUser.setLocation(address);
            activeUser.setTimeout(timeout);

            if (StringUtils.equals(currentSessionId, activeUser.getId())) {
                activeUser.setCurrent(true);
            }

            if (StringUtils.isBlank(username) || StringUtils.equalsIgnoreCase(activeUser.getUsername(), username)) {
                list.add(activeUser);
            }
        }
        return list;
    }

    @Override
    public void forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        session.stop();
        sessionDAO.delete(session);
    }

    @Override
    public List<SimplePrincipalCollection> getPrincipals(@NonNull Long userId) {
        List<SimplePrincipalCollection> collections = Lists.newArrayList();
        Collection<Session> sessions = sessionDAO.getActiveSessions();

        for (Session session : sessions) {
            SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) session
                    .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (simplePrincipalCollection != null) {
                User user = (User) simplePrincipalCollection.getPrimaryPrincipal();
                if (userId.equals(user.getUserId())) {
                    collections.add(simplePrincipalCollection);
                }
            }
        }

        return collections;
    }
}
