package io.webapp.generator.controller;

import io.webapp.common.entity.AdminConstant;
import io.webapp.common.utils.AdminUtil;
import io.webapp.generator.entity.GeneratorConfig;
import io.webapp.generator.servie.IGeneratorConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ADMIN
 */
@Controller("generatorViews")
@RequestMapping(AdminConstant.VIEW_PREFIX + "generator")
public class ViewController {

    @Autowired
    private IGeneratorConfigService generatorConfigService;

    @GetMapping("generator")
    @RequiresPermissions("generator:view")
    public String generator() {
        return AdminUtil.view("generator/generator");
    }

    @GetMapping("configure")
    @RequiresPermissions("generator:configure:view")
    public String generatorConfigure(Model model) {
        GeneratorConfig generatorConfig = generatorConfigService.findGeneratorConfig();
        model.addAttribute("config", generatorConfig);

        return AdminUtil.view("generator/configure");
    }

    @GetMapping("swagger")
    @RequiresPermissions("generator:swagger")
    public String swagger() {
        return AdminUtil.view("generator/swagger");
    }
}
