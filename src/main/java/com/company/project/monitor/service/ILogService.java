package com.company.project.monitor.service;


import com.company.project.common.entity.QueryRequest;
import com.company.project.monitor.entity.Log;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @author MrBird
 */
public interface ILogService extends IService<Log> {

    /**
     * 查询操作日志分页
     *
     * @param log     日志
     * @param request QueryRequest
     * @return IPage<Log>
     */
    IPage<Log> findLogs(Log log, QueryRequest request);

    /**
     * 删除操作日志
     *
     * @param logIds 日志 ID集合
     */
    void deleteLogs(String[] logIds);

    /**
     * 异步保存操作日志
     *
     * @param point 切点
     * @param log   日志
     * @throws JsonProcessingException 异常
     */
    @Async("febsAsyncThreadPool")
    void saveLog(ProceedingJoinPoint point, Log log) throws JsonProcessingException;
}
