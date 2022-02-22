package cn.book_manager.mapper;

import cn.book_manager.common.DataSource;
import cn.book_manager.pojo.Book;
import cn.book_manager.pojo.BookSub;
import org.springframework.stereotype.Repository;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-11 19:06
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Repository
@DataSource("dataSource2")
public interface BookMapper {

    void addBook(Map<String, Object> bookMap);

    void editBook(Map<String, Object> bookMap);

    Boolean deleteBook(Map<String, Object> bookMap);

    List<Book> queryBookList(Map<String, Object> bookMap);

    void addSubBook(Map<String, Object> subMap);

    void returnBook(Map<String, Object> bookMap);

    List<BookSub> querySubBookList(Map<String, Object> bookMap);

    void updateInventories(Map<String, Object> bookMap);

    int queryInventories(Integer bId);

    int queryBookName(String bookName);

    List<BookSub> querySubBookId(Integer uId);

}