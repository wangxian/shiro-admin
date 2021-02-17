package io.webapp.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user_data_permission")
public class UserDataPermission {

    @TableField("user_id")
    private Long userId;

    @TableField("dept_id")
    private Long deptId;
}
