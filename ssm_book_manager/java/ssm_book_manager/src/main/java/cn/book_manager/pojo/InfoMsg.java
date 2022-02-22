package cn.book_manager.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Jersey
 * @create: 2022-02-10 19:23
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Data
public class InfoMsg  implements Serializable {

    private  String code = "0";

    private  String msg = "成功";
}
