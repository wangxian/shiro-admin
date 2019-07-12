package com.company.project.common.authentication;

import com.company.project.common.annotation.Helper;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @author ADMIN
 */
@Helper
public class ShiroHelper extends ShiroRealm {

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return AuthorizationInfo
     */
    public AuthorizationInfo getCurrentuserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
