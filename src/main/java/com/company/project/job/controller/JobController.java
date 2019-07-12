package com.company.project.job.controller;

import com.company.project.common.annotation.Log;
import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.entity.QueryRequest;
import com.company.project.common.exception.AdminException;
import com.company.project.job.entity.Job;
import com.company.project.job.service.IJobService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author ADMIN
 */
@Slf4j
@Validated
@RestController
@RequestMapping("job")
public class JobController extends BaseController {

    @Autowired
    private IJobService jobService;

    @GetMapping
    @RequiresPermissions("job:view")
    public AdminResponse jobList(QueryRequest request, Job job) {
        Map<String, Object> dataTable = getDataTable(this.jobService.findJobs(request, job));
        return new AdminResponse().success().data(dataTable);
    }

    @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @Log("新增定时任务")
    @PostMapping
    @RequiresPermissions("job:add")
    public AdminResponse addJob(@Valid Job job) throws AdminException {
        try {
            this.jobService.createJob(job);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "新增定时任务失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("删除定时任务")
    @GetMapping("delete/{jobIds}")
    @RequiresPermissions("job:delete")
    public AdminResponse deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws AdminException {
        try {
            String[] ids = jobIds.split(StringPool.COMMA);
            this.jobService.deleteJobs(ids);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "删除定时任务失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("修改定时任务")
    @PostMapping("update")
    public AdminResponse updateJob(@Valid Job job) throws AdminException {
        try {
            this.jobService.updateJob(job);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改定时任务失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("执行定时任务")
    @RequiresPermissions("job:run")
    @GetMapping("run/{jobIds}")
    public AdminResponse runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws AdminException {
        try {
            this.jobService.run(jobIds);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "执行定时任务失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("暂停定时任务")
    @GetMapping("pause/{jobIds}")
    @RequiresPermissions("job:pause")
    public AdminResponse pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws AdminException {
        try {
            this.jobService.pause(jobIds);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "暂停定时任务失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @Log("恢复定时任务")
    @GetMapping("resume/{jobIds}")
    @RequiresPermissions("job:resume")
    public AdminResponse resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws AdminException {
        try {
            this.jobService.resume(jobIds);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "恢复定时任务失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("job:export")
    public void export(QueryRequest request, Job job, HttpServletResponse response) throws AdminException {
        try {
            List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
            ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }
}
