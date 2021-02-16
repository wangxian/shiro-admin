package io.webapp.others.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.webapp.common.annotation.DataPermission;
import io.webapp.others.entity.DataPermissionTest;

/**
 * methods = {"selectPage"} 是最终 mybatis plus 执行的最终方法，如 selectPage, selectList, selectCount等
 */
@DataPermission(methods = {"selectPage"})
public interface DataPermissionTestMapper extends BaseMapper<DataPermissionTest> {

}
