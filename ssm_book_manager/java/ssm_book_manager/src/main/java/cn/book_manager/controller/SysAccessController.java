package cn.book_manager.controller;

import cn.book_manager.common.AssembleResponseMsg;
import cn.book_manager.pojo.AssembleResponseBody;
import cn.book_manager.service.SysAccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-12 14:19
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@RestController
public class SysAccessController {
    @Autowired
    private SysAccessLogService sysAccessLogService;

    @RequestMapping(value = "/querySysLogList")
    public AssembleResponseBody<?> querySysLogList(@RequestBody Map<String,Object> map){

        return new AssembleResponseMsg().success(sysAccessLogService.querySysLogList(map));
    }
}
