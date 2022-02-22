package cn.book_manager.service.impl;

import cn.book_manager.mapper.BookMapper;
import cn.book_manager.pojo.Book;
import cn.book_manager.pojo.BookSub;
import cn.book_manager.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
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
@Service
public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public void addBook(Map<String, Object> map) {
        bookMapper.addBook(map);
    }

    @Transactional(transactionManager = "dataSourceTransactionManager",propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ,readOnly = false,rollbackFor = { Exception.class })
    @Override
    public void returnBook(Map<String, Object> subMap) {

        try {
            bookMapper.returnBook(subMap);
            int allBookInventories =  queryInventories((Integer) subMap.get("bId"));
            subMap.put("inventory",++allBookInventories);
            updateInventories(subMap);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
            //throw new RuntimeException(e);//手动抛出异常需要抛出RuntimeException异常事务才会回滚;也可以手动回滚
            e.printStackTrace();
        }
    }

    @Override
    public void editBook(Map<String, Object> bookMap) {
        bookMapper.editBook(bookMap);
    }

    @Override
    public Boolean deleteBook(Map<String, Object> bookMap) {
        return bookMapper.deleteBook(bookMap);
    }

    @Override
    public Map<String, Object> queryBookList(Map<String, Object> bookMap) {


        int pageNum = (int) bookMap.get("pageNum");
        int pageSize = (int) bookMap.get("pageSize");

        PageHelper.startPage(pageNum, pageSize);

        List<Book> books = bookMapper.queryBookList(bookMap);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("rows",books);

        return resultMap;
    }
    @Transactional(transactionManager = "dataSourceTransactionManager",propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ, rollbackFor = { Exception.class })
    @Override
    public void addSubBook(Map<String, Object> subMap)   {

        try {
            bookMapper.addSubBook(subMap);
            int allBookInventories = queryInventories((Integer) subMap.get("bId"));
            subMap.put("inventory",--allBookInventories);
             updateInventories(subMap);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
           //throw new RuntimeException(e);//手动抛出异常需要抛出RuntimeException异常事务才会回滚;也可以手动回滚
             e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> querySubBookList(Map<String, Object> bookMap) {

        boolean needPage = bookMap.get("pageNum")!=null && bookMap.get("pageSize")!=null;

        if (needPage){
            int pageNum = (int) bookMap.get("pageNum");
            int pageSize = (int) bookMap.get("pageSize");
            PageHelper.startPage(pageNum, pageSize);
        }

        List<BookSub> bookSubs = bookMapper.querySubBookList(bookMap);
        Map<String, Object> resultMap = new HashMap<>();

        if (needPage){
            PageInfo<BookSub> pageInfo = new PageInfo<>(bookSubs);
            resultMap.put("total",pageInfo.getTotal());
        }

        resultMap.put("row",bookSubs);
        return resultMap;
    }

    @Override
    public void updateInventories(Map<String, Object> bookMap) {
        bookMapper.updateInventories(bookMap);
    }

    @Override
    public int queryInventories(Integer bId) {
        return bookMapper.queryInventories(bId);
    }

    @Override
    public int queryBookName(String bookName) {
        return bookMapper.queryBookName(bookName);
    }


}
