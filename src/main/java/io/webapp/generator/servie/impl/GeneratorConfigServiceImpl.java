package io.webapp.generator.servie.impl;

import io.webapp.generator.entity.GeneratorConfig;
import io.webapp.generator.mapper.GeneratorConfigMapper;
import io.webapp.generator.servie.IGeneratorConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ADMIN
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GeneratorConfigServiceImpl extends ServiceImpl<GeneratorConfigMapper, GeneratorConfig> implements IGeneratorConfigService {

    @Override
    public GeneratorConfig findGeneratorConfig() {
        List<GeneratorConfig> generatorConfigs = this.baseMapper.selectList(null);
        return CollectionUtils.isNotEmpty(generatorConfigs) ? generatorConfigs.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGeneratorConfig(GeneratorConfig generatorConfig) {
        this.saveOrUpdate(generatorConfig);
    }
}
