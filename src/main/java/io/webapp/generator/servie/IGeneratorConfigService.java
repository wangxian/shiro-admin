package io.webapp.generator.servie;

import io.webapp.generator.entity.GeneratorConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ADMIN
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
