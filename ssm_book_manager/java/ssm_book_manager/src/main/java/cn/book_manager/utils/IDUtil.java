package cn.book_manager.utils;

import java.util.UUID;

/**
 * @author: Jersey
 * @create: 2022-02-12 14:21
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
public class IDUtil {

    public  static  String getUUID(){

        return UUID.randomUUID().toString().replace("-","");
    }
}
