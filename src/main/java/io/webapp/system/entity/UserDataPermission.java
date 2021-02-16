package io.webapp.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author MrBird
 */
@Data
@TableName("t_user_data_permission")
public class UserDataPermission {

    @TableId("user_id")
    private Long userId;

    @TableId("dept_id")
    private Long deptId;
}
