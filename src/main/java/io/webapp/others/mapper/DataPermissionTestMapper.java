package io.webapp.others.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.webapp.common.annotation.DataPermission;
import io.webapp.others.entity.DataPermissionTest;

/**
 * @author MrBird
 */
@DataPermission(methods = {"selectPage"})
public interface DataPermissionTestMapper extends BaseMapper<DataPermissionTest> {

}
