package cn.book_manager.service;

import java.util.List;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-12 14:09
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
public interface SysAccessLogService  {

    int saveSysLog(Map<String,Object> map );
    Map<String,Object> querySysLogList(Map<String,Object> map);
}
