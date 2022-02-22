package cn.book_manager.controller;

import cn.book_manager.common.AssembleResponseMsg;
import cn.book_manager.pojo.AssembleResponseBody;
import cn.book_manager.service.BookService;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-11 19:13
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@RestController
public class BookController {

    private BookService bookService;

    @Resource
    public void setBook(BookService bookService) {
        this.bookService = bookService;
    }
    @PutMapping("/addBook")
    public AssembleResponseBody<?> addBook(@RequestBody Map<String,Object> bookMap){

        try {
            bookService.addBook(bookMap);
        } catch (Exception e) {

            return  new AssembleResponseMsg().failure(200,"error","添加图书失败");
        }

        return new AssembleResponseMsg().success("success");
    }

    @PostMapping("/editBook")
    public  AssembleResponseBody<?> editBook(@RequestBody Map<String,Object> bookMap){
        try {
            bookService.editBook(bookMap);
        } catch (Exception e) {

            return  new AssembleResponseMsg().failure(200,"error","编辑图书失败");
        }
        return new AssembleResponseMsg().success("success");
    }

    @DeleteMapping("/deleteBook")
    public  AssembleResponseBody<?> deleteBook(@RequestBody Map<String,Object> bookMap){
        try {
            bookService.deleteBook(bookMap);
        } catch (Exception e) {

            return  new AssembleResponseMsg().failure(200,"error","删除图书失败");
        }
        return new AssembleResponseMsg().success("success");
    }

   @RequestMapping("/queryBookList")
   public  AssembleResponseBody<?> queryBookList(@RequestBody Map<String,Object> bookMap){


       return new AssembleResponseMsg().success(bookService.queryBookList(bookMap));
   }

    @PostMapping(value = "queryBookName" )
    public AssembleResponseBody<?> queryBookName(@RequestBody String data){
        Map<?,?> newData = (Map<?, ?>) JSON.parse(data);
        String bookName = (String) newData.get("bookName");
        int i = bookService.queryBookName(bookName);

        Map<String,Object> all = new HashMap<>();


        all.put("isPresent", i == 1);

        all.put("status","OK");

        return  new AssembleResponseMsg ().success(all);

    }
    @PostMapping("/addSubBook")
    public AssembleResponseBody<?> addSubBook(@RequestBody Map<String,Object> bookMap){
        int subNum = (int)bookMap.getOrDefault("subNum", 0);
        bookMap.computeIfAbsent("subNum",(v)->subNum +1);

        try {
            if (bookMap.get("bId")!=null){
                bookService.addSubBook(bookMap);
            }

        } catch (Exception e) {

            return  new AssembleResponseMsg().failure(200,"error","借阅图书失败");
        }

        return new AssembleResponseMsg().success("success");
    }

    @PostMapping("/returnSubBook")
    public AssembleResponseBody<?> returnSubBook(@RequestBody Map<String,Object> bookMap){

        try {
            bookService.returnBook(bookMap);
        } catch (Exception e) {

            return  new AssembleResponseMsg().failure(200,"error","归还图书失败");
        }

        return new AssembleResponseMsg().success("success");
    }

    @PostMapping("/querySub")
    public AssembleResponseBody<?> querySub(@RequestBody Map<String,Object> bookMap){

        return new AssembleResponseMsg().success(bookService.querySubBookList(bookMap));
    }
    @PostMapping("/updateInventories")
    public AssembleResponseBody<?> updateInventories(@RequestBody Map<String,Object> bookMap){
        bookService.updateInventories(bookMap);
        return new AssembleResponseMsg().success("update success");
    }
}
