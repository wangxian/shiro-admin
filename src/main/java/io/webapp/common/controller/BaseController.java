package io.webapp.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.webapp.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ADMIN
 */
public class BaseController {

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected void login(AuthenticationToken token) {
        getSubject().login(token);
    }

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        return getDataTable(pageInfo.getRecords(), pageInfo.getTotal());
    }

    protected Map<String, Object> getDataTable(Object rows, Object total) {
        Map<String, Object> data = new HashMap<>(3);
        data.put("rows", rows);
        data.put("total", total);
        return data;
    }

}
