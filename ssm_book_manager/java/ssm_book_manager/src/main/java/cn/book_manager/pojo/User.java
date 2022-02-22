package cn.book_manager.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: Jersey
 * @create: 2022-02-10 18:33
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@JsonIgnoreProperties(value = { "handler" })//json 序列化时忽略 bean 中的一些不需要转化的属性因为使用Mybatis采用懒加载候，会创建一个关联实体类的代理对象，并且会在该对象中创建一个handler属性，而在json序列化的时候由没有对应的getter方法
public class User {


    private  Integer id;
    private  String uid;
    private  String userName;
    private  String password;
    private  String uName;
    private  Integer age;
    private  Integer sex;
    private  List<BookSub> userSubBook;
}
