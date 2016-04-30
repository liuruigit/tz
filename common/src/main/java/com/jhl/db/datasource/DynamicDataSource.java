package com.jhl.db.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created with IntelliJ IDEA.
 * User: vic
 * Date: 13-5-18
 * Time: 下午4:23
 * 根据@ChooseDataSource获取数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String key = DataSourceContextHolder.getDataSourceKey();
        return DataSourceContextHolder.getDataSourceKey();
    }
}
