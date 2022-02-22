package cn.book_manager.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author: Jersey
 * @create: 2022-02-22 16:59
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description: 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DbSourceHolder.getDbType();
    }
}
