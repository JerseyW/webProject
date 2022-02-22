package cn.book_manager.service;

import cn.book_manager.pojo.Book;

import java.util.List;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-11 19:11
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
public interface BookService {

    void addBook(Map<String, Object> map);

    void returnBook(Map<String, Object> bookMap);

    void editBook(Map<String, Object> bookMap);


    Boolean deleteBook(Map<String, Object> bookMap);

    Map<String, Object> queryBookList(Map<String, Object> bookMap);

    void addSubBook(Map<String, Object> subMap);

    Map<String, Object> querySubBookList(Map<String, Object> bookMap);

    void updateInventories(Map<String, Object> bookMap);

    int queryInventories(Integer bId);

    int queryBookName(String bookName);

}