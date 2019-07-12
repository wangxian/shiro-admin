package com.company.project.others.controller;

import com.company.project.common.entity.AdminConstant;
import com.company.project.common.utils.AdminUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ADMIN
 */
@Controller("othersView")
@RequestMapping(AdminConstant.VIEW_PREFIX + "others")
public class ViewController {

    @GetMapping("demo/form")
    @RequiresPermissions("demo:form:view")
    public String demoForm() {
        return AdminUtil.view("others/demo/form");
    }

    @GetMapping("demo/form/group")
    @RequiresPermissions("demo:formgroup:view")
    public String demoFormGroup() {
        return AdminUtil.view("others/demo/formGroup");
    }

    @GetMapping("demo/tools")
    @RequiresPermissions("demo:tools:view")
    public String demoTools() {
        return AdminUtil.view("others/demo/tools");
    }

    @GetMapping("demo/icon")
    @RequiresPermissions("demo:icons:view")
    public String demoIcon() {
        return AdminUtil.view("others/demo/icon");
    }

    @GetMapping("demo/others")
    @RequiresPermissions("others:demo:others")
    public String demoOthers() {
        return AdminUtil.view("others/demo/others");
    }

    @GetMapping("apex/line")
    @RequiresPermissions("apex:line:view")
    public String apexLine() {
        return AdminUtil.view("others/apex/line");
    }

    @GetMapping("apex/area")
    @RequiresPermissions("apex:area:view")
    public String apexArea() {
        return AdminUtil.view("others/apex/area");
    }

    @GetMapping("apex/column")
    @RequiresPermissions("apex:column:view")
    public String apexColumn() {
        return AdminUtil.view("others/apex/column");
    }

    @GetMapping("apex/radar")
    @RequiresPermissions("apex:radar:view")
    public String apexRadar() {
        return AdminUtil.view("others/apex/radar");
    }

    @GetMapping("apex/bar")
    @RequiresPermissions("apex:bar:view")
    public String apexBar() {
        return AdminUtil.view("others/apex/bar");
    }

    @GetMapping("apex/mix")
    @RequiresPermissions("apex:mix:view")
    public String apexMix() {
        return AdminUtil.view("others/apex/mix");
    }

    @GetMapping("map")
    @RequiresPermissions("map:view")
    public String map() {
        return AdminUtil.view("others/map/gaodeMap");
    }

    @GetMapping("eximport")
    @RequiresPermissions("others:eximport:view")
    public String eximport() {
        return AdminUtil.view("others/eximport/eximport");
    }

    @GetMapping("eximport/result")
    public String eximportResult() {
        return AdminUtil.view("others/eximport/eximportResult");
    }
}
