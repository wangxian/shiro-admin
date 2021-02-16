package io.webapp.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.webapp.system.entity.RoleMenu;
import io.webapp.system.mapper.RoleMenuMapper;
import io.webapp.system.service.IRoleMenuService;
import io.webapp.system.service.IUserRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ADMIN
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    private final IUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(List<String> roleIds) {
        this.baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getRoleId, roleIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByMenuId(List<String> menuIds) {
        this.baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId, menuIds));
    }

    @Override
    public Set<Long> findUserIdByMenuIds(List<String> menuIds) {
        List<RoleMenu> roleMenus = this.baseMapper.selectList(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId, menuIds));
        if (CollectionUtils.isNotEmpty(roleMenus)) {
            List<String> roleIds = roleMenus.stream().map(RoleMenu::getRoleId)
                                            .map(String::valueOf)
                                            .collect(Collectors.toList());

            return userRoleService.findUserIdByRoleIds(roleIds);
        }

        return null;
    }
}
