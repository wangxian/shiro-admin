package com.company.project.others.controller;

import com.company.project.common.entity.FebsConstant;
import com.company.project.common.utils.FebsUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MrBird
 */
@Controller("othersView")
@RequestMapping(FebsConstant.VIEW_PREFIX + "others")
public class ViewController {

    @GetMapping("demo/form")
    @RequiresPermissions("demo:form:view")
    public String demoForm() {
        return FebsUtil.view("others/demo/form");
    }

    @GetMapping("demo/form/group")
    @RequiresPermissions("demo:formgroup:view")
    public String demoFormGroup() {
        return FebsUtil.view("others/demo/formGroup");
    }

    @GetMapping("demo/tools")
    @RequiresPermissions("demo:tools:view")
    public String demoTools() {
        return FebsUtil.view("others/demo/tools");
    }

    @GetMapping("demo/icon")
    @RequiresPermissions("demo:icons:view")
    public String demoIcon() {
        return FebsUtil.view("others/demo/icon");
    }

    @GetMapping("demo/others")
    @RequiresPermissions("others:demo:others")
    public String demoOthers() {
        return FebsUtil.view("others/demo/others");
    }

    @GetMapping("apex/line")
    @RequiresPermissions("apex:line:view")
    public String apexLine() {
        return FebsUtil.view("others/apex/line");
    }

    @GetMapping("apex/area")
    @RequiresPermissions("apex:area:view")
    public String apexArea() {
        return FebsUtil.view("others/apex/area");
    }

    @GetMapping("apex/column")
    @RequiresPermissions("apex:column:view")
    public String apexColumn() {
        return FebsUtil.view("others/apex/column");
    }

    @GetMapping("apex/radar")
    @RequiresPermissions("apex:radar:view")
    public String apexRadar() {
        return FebsUtil.view("others/apex/radar");
    }

    @GetMapping("apex/bar")
    @RequiresPermissions("apex:bar:view")
    public String apexBar() {
        return FebsUtil.view("others/apex/bar");
    }

    @GetMapping("apex/mix")
    @RequiresPermissions("apex:mix:view")
    public String apexMix() {
        return FebsUtil.view("others/apex/mix");
    }

    @GetMapping("map")
    @RequiresPermissions("map:view")
    public String map() {
        return FebsUtil.view("others/map/gaodeMap");
    }

    @GetMapping("eximport")
    @RequiresPermissions("others:eximport:view")
    public String eximport() {
        return FebsUtil.view("others/eximport/eximport");
    }

    @GetMapping("eximport/result")
    public String eximportResult() {
        return FebsUtil.view("others/eximport/eximportResult");
    }
}
