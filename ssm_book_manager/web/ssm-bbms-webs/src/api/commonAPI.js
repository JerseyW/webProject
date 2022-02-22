/**
 @author: Jersey
 @create: 2022-02-12 19:14
 @version: V1.0
 @slogan: 业精于勤,荒于嬉;行成于思,毁于随。
 @description:
 */
import request from '../utils/request'
import store from "../store";


export   function  commonAPI (api, data,method="post",isLogin = false) {
    if (!data){
        method = 'get';
    }
    let param = {
        url: '/' +process.env.VUE_APP_BASE_API+"/" + api,
        method: method
    }
    if (data  || method === "post" ){
        param.data = data;
    }
    if (method === 'put' ||method === 'delete' ){
        param.data = {...data,"_method": method === 'put' ? 'put':'delete'};
    }
    if (!isLogin){
        //发送请求重置token
        store.dispatch("saveToken", store.getters.getTokeInfo);
    }

    return request(param);
}