package io.webapp.generator.mapper;


import io.webapp.generator.entity.Column;
import io.webapp.generator.entity.Table;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ADMIN
 */
public interface GeneratorMapper {

    List<String> getDatabases(@Param("databaseType") String databaseType);

    <T> IPage<Table> getTables(Page<T> page, @Param("tableName") String tableName, @Param("databaseType") String databaseType, @Param("schemaName") String schemaName);

    List<Column> getColumns(@Param("databaseType") String databaseType, @Param("schemaName") String schemaName, @Param("tableName") String tableName);
}
