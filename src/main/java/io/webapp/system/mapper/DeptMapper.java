package io.webapp.system.mapper;

import io.webapp.system.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author ADMIN
 */
public interface DeptMapper extends BaseMapper<Dept> {
    /**
     * 递归删除部门
     *
     * @param deptId deptId
     */
    void deleteDepts(String deptId);
}
