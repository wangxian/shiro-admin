package io.webapp.generator.controller;

import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.controller.BaseController;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.exception.AdminException;
import io.webapp.generator.entity.GeneratorConfig;
import io.webapp.generator.servie.IGeneratorConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequiredArgsConstructor
public class GeneratorConfigController extends BaseController {

    private final IGeneratorConfigService generatorConfigService;

    @GetMapping
    @RequiresPermissions("generator:configure:view")
    public AdminResponse getGeneratorConfig() {
        return new AdminResponse().success().data(generatorConfigService.findGeneratorConfig());
    }

    @PostMapping("update")
    @RequiresPermissions("generator:configure:update")
    @ControllerEndpoint(operation = "修改GeneratorConfig", exceptionMessage = "修改GeneratorConfig失败")
    public AdminResponse updateGeneratorConfig(@Valid GeneratorConfig generatorConfig) {
        if (StringUtils.isBlank(generatorConfig.getId())) {
            throw new AdminException("配置id不能为空");
        }

        this.generatorConfigService.updateGeneratorConfig(generatorConfig);
        return new AdminResponse().success();
    }
}
