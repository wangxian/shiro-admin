package com.company.project.system.entity;

import com.company.project.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ADMIN
 */
@Data
@TableName("admin_menu")
@Excel("菜单信息表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 8571011372410167901L;

    /**
     * 菜单
     */
    public static final String TYPE_MENU = "0";

    /**
     * 按钮
     */
    public static final String TYPE_BUTTON = "1";

    public static final Long TOP_NODE = 0L;

    /**
     * 菜单/按钮ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 上级菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @TableField("menu_name")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ExcelField(value = "名称")
    private String menuName;

    /**
     * 菜单URL
     */
    @TableField("url")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "url")
    private String url;

    /**
     * 权限标识
     */
    @TableField("perms")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "权限")
    private String perms;

    /**
     * 图标
     */
    @TableField("icon")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "图标")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @TableField("type")
    @NotBlank(message = "{required}")
    @ExcelField(value = "类型", writeConverterExp = "0=按钮,1=菜单")
    private String type;

    /**
     * 排序
     */
    @TableField("order_num")
    private Long orderNum;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createdAt;

    /**
     * 修改时间
     */
    @TableField("updated_at")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date updatedAt;


}
