package cn.book_manager.controller;

import cn.book_manager.common.AssembleResponseMsg;
import cn.book_manager.pojo.AssembleResponseBody;
import cn.book_manager.pojo.BookSub;
import cn.book_manager.pojo.User;
import cn.book_manager.service.UserService;
import cn.book_manager.utils.CryptoUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author: Jersey
 * @create: 2022-02-10 18:46
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@RestController
public class UserController {

    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/queryUserList")
    public AssembleResponseBody<?> queryUserList(@RequestBody Map<String,Object> paramMap){

        Map<String,Object> resultMap = userService.queryUserList(paramMap);
        System.out.println("resultMap"+resultMap);
        return new AssembleResponseMsg ().success(resultMap);
    }



     @RequestMapping(value = "queryUser" )
    public AssembleResponseBody<?> queryUser(@RequestBody Map<String,Object> map,  HttpServletRequest request){


        String password = (String) map.get("password") ;

         map.computeIfPresent("password",(k, v)->CryptoUtil.desEncrypt(password.trim()));
         String username = request.getServletContext().getInitParameter("username");
         String i = userService.queryUser(map);

        Map<String,Object> all = new HashMap<>();

        if (i != null){
            Optional<Object> find = map.values().stream().filter((k) -> k.equals(username)).findFirst();

            all.put("token",find.isPresent()?"admin":"editor");
            List<Object> list = new ArrayList<>();
            list.add(find.isPresent()?"admin":"editor");
            all.put("roles",list);
            all.put("status","OK");
            all.put("uId",i);

            return  new AssembleResponseMsg ().success(all);
        }

        return  new AssembleResponseMsg().failure(200,"error","用户名或者密码错误");
    }
    @PostMapping(value = "queryUserByName" )
    public AssembleResponseBody<?> queryUserByName(@RequestBody Map<String,Object> map){
        String i = userService.queryUserByName((String) map.get("userName"));

        Map<String,Object> all = new HashMap<>();


        all.put("isPresent", Objects.equals(i, "1"));
        all.put("uId", i);

        all.put("status","OK");

        return  new AssembleResponseMsg ().success(all);

    }
    @PutMapping("/addUser")
    public  AssembleResponseBody<?> addUser(@RequestBody  Map<String,Object> userMap){

        userMap.compute("password",(k,v)->CryptoUtil.desEncrypt((String) userMap.get("password")));

        userService.addUser(userMap);
        return  new AssembleResponseMsg ().success("success");
    }

    @RequestMapping("/updateUserById")
    public  AssembleResponseBody<?> updateUserById(@RequestBody Map<String, Object> userMap) {
        userMap.compute("password",(k,v)->CryptoUtil.desEncrypt((String) userMap.get("password")));
        userService.updateUserById(userMap);
        return  new AssembleResponseMsg ().success("success");
    }
    @RequestMapping("/updateUser")
    public  AssembleResponseBody<?> updateUserByName(@RequestBody Map<String, Object> userMap) {

        userMap.compute("password",(k,v)->CryptoUtil.desEncrypt((String) userMap.get("password")));
        userService.updateUserByName(userMap);
        return  new AssembleResponseMsg ().success("success");
    }

    @DeleteMapping("/deleteUser")
    public  AssembleResponseBody<?> deleteUser(@RequestBody Map<String, Object> userMap) {
        userService.deleteUser(userMap);
        return  new AssembleResponseMsg ().success("success");
    }


    @RequestMapping("/updatePassword")
    public AssembleResponseBody<?> updateUserPwd(@RequestBody Map<String, Object> userMap){
        userMap.compute("password",(k,v)->CryptoUtil.desEncrypt((String) userMap.get("password")));
        userService.updateUserPassword(userMap);
        return  new AssembleResponseMsg ().success("success");
    }

    @PostMapping("/queryAlreadySubUser")
    public AssembleResponseBody<?> queryAlreadySubUser(@RequestBody Map<String, Object> userMap){

         User userSInfo= userService.queryAlreadySubUser(userMap);
         Map<String,Object> resultMap = new HashMap<>();

         resultMap.put("userSub", userSInfo);
         resultMap.put("status","OK");

        return new AssembleResponseMsg ().success(resultMap);
    }

}
