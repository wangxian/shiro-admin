package io.webapp.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.webapp.common.entity.AdminConstant;
import io.webapp.common.entity.QueryRequest;
import io.webapp.common.util.AddressUtil;
import io.webapp.common.util.HttpContextUtil;
import io.webapp.common.util.IpUtil;
import io.webapp.common.util.SortUtil;
import io.webapp.monitor.entity.LoginLog;
import io.webapp.monitor.mapper.LoginLogMapper;
import io.webapp.monitor.service.ILoginLogService;
import io.webapp.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author ADMIN
 */
@Service("loginLogService")
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Override
    public IPage<LoginLog> findLoginLogs(LoginLog loginLog, QueryRequest request) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) &&
                StringUtils.equals(loginLog.getLoginTimeFrom(), loginLog.getLoginTimeTo())) {
            loginLog.setLoginTimeFrom(loginLog.getLoginTimeFrom() + " 00:00:00");
            loginLog.setLoginTimeTo(loginLog.getLoginTimeTo() + " 23:59:59");
        }

        if (StringUtils.isNotBlank(loginLog.getUsername())) {
            queryWrapper.lambda().eq(LoginLog::getUsername, loginLog.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) && StringUtils.isNotBlank(loginLog.getLoginTimeTo())) {
            queryWrapper.lambda()
                    .ge(LoginLog::getLoginTime, loginLog.getLoginTimeFrom())
                    .le(LoginLog::getLoginTime, loginLog.getLoginTimeTo());
        }

        Page<LoginLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "loginTime", AdminConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IpUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }

    @Override
    public void saveLoginLog(String username) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setSystemBrowserInfo();
        saveLoginLog(loginLog);
    }

    @Override
    public void deleteLoginLogs(String[] ids) {
        List<String> list = Arrays.asList(ids);
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public Long findTotalVisitCount() {
        return this.baseMapper.findTotalVisitCount();
    }

    @Override
    public Long findTodayVisitCount() {
        return this.baseMapper.findTodayVisitCount();
    }

    @Override
    public Long findTodayIp() {
        return this.baseMapper.findTodayIp();
    }

    @Override
    public List<Map<String, Object>> findLastSevenDaysVisitCount(User user) {
        return this.baseMapper.findLastSevenDaysVisitCount(user);
    }

    private List<Map<String, Object>> findLastSevenDaysVisitCount() {
        return findLastSevenDaysVisitCount(new User());
    }

    private List<Map<String, Object>> findLastSevenDaysVisitCount(String username) {
        User param = new User();
        param.setUsername(username);
        return findLastSevenDaysVisitCount(param);
    }

    @Override
    public Map<String, Object> retrieveIndexPageData(String username) {
        Map<String, Object> data = new HashMap<>(8);
        // 获取系统访问记录
        data.put("totalVisitCount", findTotalVisitCount());
        data.put("todayVisitCount", findTodayVisitCount());
        data.put("todayIp", findTodayIp());
        // 获取近期系统访问记录
        data.put("lastSevenVisitCount", findLastSevenDaysVisitCount());
        data.put("lastSevenUserVisitCount", findLastSevenDaysVisitCount(username));
        return data;
    }
}
