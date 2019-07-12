package com.company.project.generator.controller;

import com.company.project.common.annotation.Log;
import com.company.project.common.controller.BaseController;
import com.company.project.common.entity.AdminResponse;
import com.company.project.common.exception.AdminException;
import com.company.project.generator.entity.GeneratorConfig;
import com.company.project.generator.servie.IGeneratorConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ADMIN
 */
@Slf4j
@RestController
@RequestMapping("generatorConfig")
public class GeneratorConfigController extends BaseController {

    @Autowired
    private IGeneratorConfigService generatorConfigService;

    @GetMapping
    @RequiresPermissions("generator:configure:view")
    public AdminResponse getGeneratorConfig() {
        return new AdminResponse().success().data(generatorConfigService.findGeneratorConfig());
    }

    @Log("修改GeneratorConfig")
    @PostMapping("update")
    @RequiresPermissions("generator:configure:update")
    public AdminResponse updateGeneratorConfig(@Valid GeneratorConfig generatorConfig) throws AdminException {
        try {
            if (StringUtils.isBlank(generatorConfig.getId()))
                throw new AdminException("配置id不能为空");
            this.generatorConfigService.updateGeneratorConfig(generatorConfig);
            return new AdminResponse().success();
        } catch (Exception e) {
            String message = "修改GeneratorConfig失败";
            log.error(message, e);
            throw new AdminException(message);
        }
    }
}
