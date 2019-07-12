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
@TableName("admin_dept")
@Excel("部门信息表")
public class Dept implements Serializable {

    private static final long serialVersionUID = 5702271568363798328L;
    /**
     * 部门 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long deptId;

    /**
     * 上级部门 ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ExcelField(value = "部门名称")
    private String deptName;

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

    @TableField("updated_at")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

}
