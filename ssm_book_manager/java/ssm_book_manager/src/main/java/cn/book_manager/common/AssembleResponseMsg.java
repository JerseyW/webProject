package cn.book_manager.common;

import cn.book_manager.pojo.AssembleResponseBody;
import cn.book_manager.pojo.InfoMsg;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Jersey
 * @create: 2022-02-10 19:19
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description: 公共类。处理response 结果
 */
public class AssembleResponseMsg {


    public <T> AssembleResponseBody<T>  success(T data){

        AssembleResponseBody<T> responseBody = new AssembleResponseBody<>();
        responseBody.setData(data);
        responseBody.setInfo(new InfoMsg());

        return responseBody;
    }

    public <T>AssembleResponseBody<T> failure(int status,String errorCode,String msg){

        AssembleResponseBody<T> responseBody = new AssembleResponseBody<>();
        responseBody.setStatus(status);
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(errorCode);
        infoMsg.setMsg(msg);
        responseBody.setInfo(infoMsg);

        return  responseBody;
    }
}
