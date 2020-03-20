package io.webapp.monitor.controller;

import io.webapp.common.entity.AdminResponse;
import io.webapp.monitor.entity.ActiveUser;
import io.webapp.monitor.service.ISessionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ADMIN
 */
@RestController
@RequestMapping("session")
@RequiredArgsConstructor
public class SessionController {

    private final ISessionService sessionService;

    @GetMapping("list")
    @RequiresPermissions("online:view")
    public AdminResponse list(String username) {
        List<ActiveUser> list = sessionService.list(username);
        Map<String, Object> data = new HashMap<>(2);

        data.put("rows", list);
        data.put("total", CollectionUtils.size(list));

        return new AdminResponse().success().data(data);
    }

    @GetMapping("delete/{id}")
    @RequiresPermissions("user:kickout")
    public AdminResponse forceLogout(@PathVariable String id) {
        sessionService.forceLogout(id);
        return new AdminResponse().success();
    }
}
