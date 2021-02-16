package io.webapp.system.service.impl;

import io.webapp.system.service.IDeptService;
import io.webapp.common.entity.DeptTree;
import io.webapp.common.entity.AdminConstant;
import io.webapp.common.entity.QueryRequest;
import io.webapp.common.utils.SortUtil;
import io.webapp.common.utils.TreeUtil;
import io.webapp.system.entity.Dept;
import io.webapp.system.mapper.DeptMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ADMIN
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    public List<DeptTree<Dept>> findDept() {
        List<Dept> deptList = this.baseMapper.selectList(new QueryWrapper<>());
        List<DeptTree<Dept>> trees = this.convertDept(deptList);
        return TreeUtil.buildDeptTree(trees);
    }

    @Override
    public List<DeptTree<Dept>> findDept(Dept dept) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        }

        queryWrapper.lambda().orderByAsc(Dept::getOrderNum);

        List<Dept> deptList = this.baseMapper.selectList(queryWrapper);
        List<DeptTree<Dept>> trees =  this.convertDept(deptList);
        return TreeUtil.buildDeptTree(trees);
    }

    @Override
    public List<Dept> findDept(Dept dept, QueryRequest request) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        }

        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", AdminConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDept(Dept dept) {
        Long parentId = dept.getParentId();
        if (parentId == null) {
            dept.setParentId(0L);
        }

        dept.setCreatedAt(new Date());
        this.save(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(Dept dept) {
        dept.setUpdatedAt(new Date());
        this.baseMapper.updateById(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(String[] deptIds) {
        Arrays.stream(deptIds).forEach(deptId -> this.baseMapper.deleteDepts(deptId));
    }

    private List<DeptTree<Dept>> convertDept(List<Dept> depts) {
        List<DeptTree<Dept>> trees = new ArrayList<>();

        depts.forEach(dept -> {
            DeptTree<Dept> tree = new DeptTree<>();
            tree.setId(String.valueOf(dept.getDeptId()));
            tree.setParentId(String.valueOf(dept.getParentId()));
            tree.setName(dept.getDeptName());
            tree.setData(dept);
            trees.add(tree);
        });
        return trees;
    }
}
