package io.webapp.generator.servie;

import io.webapp.common.entity.QueryRequest;
import io.webapp.generator.entity.Column;
import io.webapp.generator.entity.Table;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author ADMIN
 */
public interface IGeneratorService {

    /**
     * 获取数据库列表
     *
     * @param databaseType databaseType
     * @return 数据库列表
     */
    List<String> getDatabases(String databaseType);

    /**
     * 获取数据表
     *
     * @param tableName    tableName
     * @param request      request
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @return 数据表分页数据
     */
    IPage<Table> getTables(String tableName, QueryRequest request, String databaseType, String schemaName);

    /**
     * 获取数据表列属性
     *
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @param tableName    tableName
     * @return 数据表列属性
     */
    List<Column> getColumns(String databaseType, String schemaName, String tableName);
}
