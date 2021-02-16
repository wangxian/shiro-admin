package io.webapp.system.service;

import io.webapp.system.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author ADMIN
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 通过角色 id 删除
     *
     * @param roleIds 角色 id
     */
    void deleteRoleMenusByRoleId(List<String> roleIds);

    /**
     * 通过菜单（按钮）id 删除
     *
     * @param menuIds 菜单（按钮）id
     */
    void deleteRoleMenusByMenuId(List<String> menuIds);

    /**
     * 通过菜单ID集合查找关联的用户ID集合
     *
     * @param menuIds 菜单ID集合
     * @return 用户ID集合
     */
    Set<Long> findUserIdByMenuIds(List<String> menuIds);
}
