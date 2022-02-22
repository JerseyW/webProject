package cn.book_manager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Jersey
 * @create: 2022-02-10 19:29
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
public class DateUtil {

    public  static  String getCurrentDateStr(String str){

        SimpleDateFormat format = new SimpleDateFormat(str);

        return format.format(new Date());
    }
}
