package cn.book_manager.mapper;

import cn.book_manager.common.DataSource;
import cn.book_manager.pojo.Book;
import cn.book_manager.pojo.BookSub;
import cn.book_manager.pojo.User;
import cn.book_manager.service.BookService;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-10 17:28
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Repository
@DataSource("dataSource1")
public interface UserMapper {

    String queryUser(Map<String, Object> userMap);
    String queryUserByName(String userName);

    List<User> queryUserList(Map<String, Object> userMap);

    void addUser(Map<String, Object> userMap);

    void updateUserByName(Map<String, Object> userMap);

    void updateUserById(Map<String, Object> userMap);

    void deleteUser(Map<String, Object> userMap);

    void  updateUserPassword(Map<String, Object> userMap);
    User  queryAlreadySubUser(Map<String, Object> userMap);
}