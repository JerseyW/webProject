package cn.book_manager.aspect;

import cn.book_manager.controller.SysAccessController;
import cn.book_manager.service.SysAccessLogService;
import cn.book_manager.utils.IDUtil;
import cn.book_manager.utils.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-10 19:47
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description: 系统访问日志切面类
 */
@Component
public class SysAccessLogAspect {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysAccessLogService sysAccessLogService;

    //访问时间
    private Date  accessDate;

    private final Map<String,Object> logMap = new HashMap<>();

    private  Class<?> clazz;

    //JoinPoint对象封装了SpringAop中切面方法的信息,
    public void doBefore(JoinPoint jp){
        //获取被代理的对象
        clazz = jp.getTarget().getClass();
        System.out.println("被代理的类："+clazz);
        if (clazz != SysAccessController.class){
            logMap.put("logId", IDUtil.getUUID());

            logMap.put("accessInterface", request.getRequestURL().toString());
            logMap.put("accessIp", request.getRemoteAddr());
            logMap.put("requestType", request.getMethod());
            logMap.put("accessDate",   accessDate = new Date());
            logMap.put("interfaceParams", Arrays.toString(jp.getArgs()));
            logMap.put("accessSource", StringUtil.getBrowserSystemInfo(request));

        }
    }

    public void doAfter( ){

        if (clazz != SysAccessController.class){

            long executeTime = new Date().getTime() - accessDate.getTime();
            logMap.put("executeTime", executeTime);
             sysAccessLogService.saveSysLog(logMap);

        }
    }
}
