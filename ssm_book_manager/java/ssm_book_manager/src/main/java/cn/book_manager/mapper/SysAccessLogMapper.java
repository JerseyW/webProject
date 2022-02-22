package cn.book_manager.mapper;

import cn.book_manager.common.DataSource;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-12 14:02
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Repository
@DataSource("dataSource3")
public interface SysAccessLogMapper {

    int saveSysLog(Map<String,Object> map );

    //这是一个用在返回值为 Map 的方法上的注解。它能够将存放对象的 List 转化为 key 值为对象的某一属性的 Map。属性有： value，填入的是对象的属性名，作为 Map 的 key 值
    @MapKey("logId")
    List<Map<String,Object>>querySysLogList(Map<String,Object> map);
}
