package io.webapp.generator.helper;

import com.google.common.io.Files;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.webapp.common.annotation.Helper;
import io.webapp.common.utils.AddressUtil;
import io.webapp.common.utils.AdminUtil;
import io.webapp.generator.entity.Column;
import io.webapp.generator.entity.FieldType;
import io.webapp.generator.entity.GeneratorConfig;
import io.webapp.generator.entity.GeneratorConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author ADMIN
 */
@Slf4j
@Helper
public class GeneratorHelper {

    public void generateEntityFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.JAVA_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getEntityPackage(), suffix, false);
        String templateName = GeneratorConstant.ENTITY_TEMPLATE;

        File entityFile = new File(path);

        columns.forEach(c -> {
            c.setField(AdminUtil.underscoreToCamel(StringUtils.lowerCase(c.getName())));
            if (StringUtils.containsAny(c.getType(), FieldType.DATE, FieldType.DATETIME, FieldType.TIMESTAMP)) {
                configure.setHasDate(true);
            }
            if (StringUtils.containsAny(c.getType(), FieldType.DECIMAL, FieldType.NUMERIC)) {
                configure.setHasBigDecimal(true);
            }
        });

        configure.setColumns(columns);
        this.generateFileByTemplate(templateName, entityFile, configure);
    }

    public void generateMapperFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.MAPPER_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getMapperPackage(), suffix, false);
        String templateName = GeneratorConstant.MAPPER_TEMPLATE;

        File mapperFile = new File(path);

        generateFileByTemplate(templateName, mapperFile, configure);
    }

    public void generateServiceFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.SERVICE_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getServicePackage(), suffix, true);
        String templateName = GeneratorConstant.SERVICE_TEMPLATE;

        File serviceFile = new File(path);

        generateFileByTemplate(templateName, serviceFile, configure);
    }

    public void generateServiceImplFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.SERVICEIMPL_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getServiceImplPackage(), suffix, false);
        String templateName = GeneratorConstant.SERVICEIMPL_TEMPLATE;

        File serviceImplFile = new File(path);

        generateFileByTemplate(templateName, serviceImplFile, configure);
    }

    public void generateControllerFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.CONTROLLER_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getControllerPackage(), suffix, false);
        String templateName = GeneratorConstant.CONTROLLER_TEMPLATE;

        File controllerFile = new File(path);

        generateFileByTemplate(templateName, controllerFile, configure);
    }

    public void generateMapperXmlFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.MAPPERXML_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getMapperXmlPackage(), suffix, false);
        String templateName = GeneratorConstant.MAPPERXML_TEMPLATE;

        File mapperXmlFile = new File(path);

        columns.forEach(c -> c.setField(AdminUtil.underscoreToCamel(StringUtils.lowerCase(c.getName()))));
        configure.setColumns(columns);

        generateFileByTemplate(templateName, mapperXmlFile, configure);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void generateFileByTemplate(String templateName, File file, Object data) throws Exception {
        Template template = getTemplate(templateName);
        Files.createParentDirs(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        try (Writer out = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), 10240)) {
            template.process(data, out);
        } catch (Exception e) {
            String message = "代码生成异常";
            log.error(message, e);
            throw new Exception(message);
        }
    }

    private static String getFilePath(GeneratorConfig configure, String packagePath, String suffix, boolean serviceInterface) {
        String filePath = GeneratorConstant.TEMP_PATH + configure.getJavaPath() +
                packageConvertPath(configure.getBasePackage() + "." + packagePath);
        if (serviceInterface) {
            filePath += "I";
        }

        filePath += configure.getClassName() + suffix;
        return filePath;
    }

    private static String packageConvertPath(String packageName) {
        return String.format("%s%s%s", File.separator,
                             packageName.contains(".") ? packageName.replaceAll("\\.", File.separator) : packageName,
                             File.separator);
    }

    private Template getTemplate(String templateName) throws Exception {
        final String templatePathPrefix = File.separator + "generator" + File.separator + "templates" + File.separator;
        Configuration configuration = new freemarker.template.Configuration(Configuration.VERSION_2_3_23);
        String templatePath = GeneratorHelper.class.getResource(templatePathPrefix).getPath();
        File file = new File(templatePath);

        if (!file.exists()) {
            templatePath = System.getProperties().getProperty("java.io.tmpdir");
            file         = new File(templatePath + File.separator + templateName);
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(AddressUtil.class.getClassLoader().getResourceAsStream("classpath:generator" + File.separator + "templates" + File.separator + templateName)), file);
        }

        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        return configuration.getTemplate(templateName);

    }
}
