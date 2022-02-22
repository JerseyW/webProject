package cn.book_manager.service.impl;

import cn.book_manager.mapper.UserMapper;
import cn.book_manager.pojo.Book;
import cn.book_manager.pojo.BookSub;
import cn.book_manager.pojo.User;
import cn.book_manager.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-10 18:41
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String queryUser(Map<String, Object> userMap) {
        return userMapper.queryUser(userMap);
    }
    @Override
    public String queryUserByName(String userName) {
        return userMapper.queryUserByName(userName);
    }

    @Override
    public void addUser(Map<String,Object> userMap) {
        userMapper.addUser(userMap);
    }

    @Override
    public void updateUserByName(Map<String, Object> userMap) {
        userMapper.updateUserByName(userMap);
    }

    @Override
    public void updateUserById(Map<String, Object> userMap) {
        userMapper.updateUserById(userMap);
    }

    @Override
    public void deleteUser(Map<String, Object> userMap) {
        userMapper.deleteUser(userMap);
    }

    //查询列表分页
    @Override
    public Map<String, Object> queryUserList(Map<String, Object> userMap) {

        int pageNum= (int) userMap.get("pageNum");
        int pageSize= (int) userMap.get("pageSize");

        PageHelper.startPage(pageNum, pageSize);
        List<User> usersList = userMapper.queryUserList(userMap);

        PageInfo<User> pageInfo = new PageInfo<>(usersList);
        long total = pageInfo.getTotal();


        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("total",total);
        resultMap.put("rows",usersList);

        return  resultMap;

    }

    @Override
    public void updateUserPassword(Map<String, Object> userMap) {
        userMapper.updateUserPassword(userMap);
    }

    @Override
    public User queryAlreadySubUser(Map<String, Object> userMap) {
        return userMapper.queryAlreadySubUser(userMap);
    }
}
