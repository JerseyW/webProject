package cn.book_manager.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: Jersey
 * @create: 2022-02-12 9:55
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description: 借书
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookSub {

     private  Integer sId;
     private  Integer uId;
     private  Integer bId;
     private  String  userName;
     private  String  bookName;
     private  String  author;
     private  BigDecimal price;
     private  String  subDateTime;
     private  String  returnDateTime;
     private  Integer subNum;//借阅次数
     private  Integer bookType; //0 未订阅，1 已订阅
     private  Integer returnType;//0 未归还 1 未催还 2 已崔还,3已归还

}
