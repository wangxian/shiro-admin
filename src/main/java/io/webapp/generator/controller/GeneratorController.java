package io.webapp.generator.controller;

import io.webapp.common.annotation.ControllerEndpoint;
import io.webapp.common.controller.BaseController;
import io.webapp.common.entity.AdminResponse;
import io.webapp.common.entity.QueryRequest;
import io.webapp.common.exception.AdminException;
import io.webapp.common.utils.AdminUtil;
import io.webapp.common.utils.FileUtil;
import io.webapp.generator.entity.Column;
import io.webapp.generator.entity.GeneratorConfig;
import io.webapp.generator.entity.GeneratorConstant;
import io.webapp.generator.helper.GeneratorHelper;
import io.webapp.generator.servie.IGeneratorConfigService;
import io.webapp.generator.servie.IGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author ADMIN
 */
@Slf4j
@RestController
@RequestMapping("generator")
@RequiredArgsConstructor
public class GeneratorController extends BaseController {

    private static final String SUFFIX = "_code.zip";

    private final IGeneratorService generatorService;
    private final IGeneratorConfigService generatorConfigService;
    private final GeneratorHelper generatorHelper;

    @GetMapping("tables/info")
    @RequiresPermissions("generator:view")
    public AdminResponse tablesInfo(String tableName, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(generatorService.getTables(tableName, request, GeneratorConstant.DATABASE_TYPE, GeneratorConstant.DATABASE_NAME));
        return new AdminResponse().success().data(dataTable);
    }

    @GetMapping
    @RequiresPermissions("generator:generate")
    @ControllerEndpoint(exceptionMessage = "代码生成失败")
    public void generate(@NotBlank(message = "{required}") String name, String remark, HttpServletResponse response) throws Exception {
        GeneratorConfig generatorConfig = generatorConfigService.findGeneratorConfig();
        if (generatorConfig == null) {
            throw new AdminException("代码生成配置为空");
        }

        String className = name;
        if (GeneratorConfig.TRIM_YES.equals(generatorConfig.getIsTrim())) {
            className = RegExUtils.replaceFirst(name, generatorConfig.getTrimValue(), StringUtils.EMPTY);
        }

        generatorConfig.setTableName(name);
        generatorConfig.setClassName(AdminUtil.underscoreToCamel(className));
        generatorConfig.setTableComment(remark);

        // 生成代码到临时目录
        List<Column> columns = generatorService.getColumns(GeneratorConstant.DATABASE_TYPE, GeneratorConstant.DATABASE_NAME, name);
        generatorHelper.generateEntityFile(columns, generatorConfig);
        generatorHelper.generateMapperFile(columns, generatorConfig);
        generatorHelper.generateMapperXmlFile(columns, generatorConfig);
        generatorHelper.generateServiceFile(columns, generatorConfig);
        generatorHelper.generateServiceImplFile(columns, generatorConfig);
        generatorHelper.generateControllerFile(columns, generatorConfig);

        // 打包
        String zipFile = System.currentTimeMillis() + SUFFIX;
        FileUtil.compress(GeneratorConstant.TEMP_PATH + "src", zipFile);

        // 下载
        FileUtil.download(zipFile, name + SUFFIX, true, response);

        // 删除临时目录
        FileUtil.delete(GeneratorConstant.TEMP_PATH);
    }
}
