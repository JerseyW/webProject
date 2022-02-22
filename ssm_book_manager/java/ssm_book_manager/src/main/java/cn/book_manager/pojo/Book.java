package cn.book_manager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Jersey
 * @create: 2022-02-11 19:11
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book {

   private  Integer bid;
   private  String bookName;
   private  String author;
   private  BigDecimal price;
   private  Integer inventory;
   private Date subDateTime;
   private  Integer bookType; //0 未订阅，1 已订阅
}
