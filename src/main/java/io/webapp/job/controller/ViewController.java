package io.webapp.job.controller;

import io.webapp.common.entity.AdminConstant;
import io.webapp.common.utils.AdminUtil;
import io.webapp.job.entity.Job;
import io.webapp.job.service.IJobService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;

/**
 * @author ADMIN
 */
@Controller("jobView")
@RequestMapping(AdminConstant.VIEW_PREFIX + "job")
@RequiredArgsConstructor
public class ViewController {

    private final IJobService jobService;

    @GetMapping("job")
    @RequiresPermissions("job:view")
    public String online() {
        return AdminUtil.view("job/job");
    }

    @GetMapping("job/add")
    @RequiresPermissions("job:add")
    public String jobAdd() {
        return AdminUtil.view("job/jobAdd");
    }

    @GetMapping("job/update/{jobId}")
    @RequiresPermissions("job:update")
    public String jobUpdate(@NotBlank(message = "{required}") @PathVariable Long jobId, Model model) {
        Job job = jobService.findJob(jobId);
        model.addAttribute("job", job);
        return AdminUtil.view("job/jobUpdate");
    }

    @GetMapping("log")
    @RequiresPermissions("job:log:view")
    public String log() {
        return AdminUtil.view("job/jobLog");
    }

}
