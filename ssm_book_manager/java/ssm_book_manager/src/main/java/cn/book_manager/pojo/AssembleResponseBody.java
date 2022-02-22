package cn.book_manager.pojo;

import cn.book_manager.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jersey
 * @create: 2022-02-10 19:26
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description: 封装响应的数据结构
 */
@Data
public class AssembleResponseBody<T> implements Serializable {

    private  int status = 200;
    private  T data;
    private  InfoMsg info;
    private  String date = DateUtil.getCurrentDateStr("yyyy-MM-dd HH:mm:ss");
}
