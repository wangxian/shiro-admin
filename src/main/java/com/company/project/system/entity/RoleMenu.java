package com.company.project.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ADMIN
 */
@Data
@TableName("admin_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -5200596408874170216L;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单/按钮ID
     */
    @TableField("menu_id")
    private Long menuId;


}
