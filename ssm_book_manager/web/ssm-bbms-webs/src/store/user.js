/**
 @author: Jersey
 @create: 2022-02-12 21:13
 @version: V1.0
 @slogan: 业精于勤,荒于嬉;行成于思,毁于随。
 @description: 使用vuex 管理状态
 */
import {storage, TOKEN_EXPIRED} from "./index";
export  default {
    namespace:true,
    actions:{
        saveToken(context,data){
            context.commit("saveToken",data);
        },
        removeToken(context){
            context.commit("removeToken");
        }
    },
    mutations:{

        saveToken (state, data) {
            state.tokenInfo = data;
            storage.setItem("token", data,TOKEN_EXPIRED);
        },
        removeToken(state){
            storage.removeItem("token");
            state.tokenInfo = {};
        }
    },
    state:{
        tokenInfo:{
            token: '',
            userName: '',
            uId:''
        }
    },
    getters:{
        getToken (state) {
            const tokenObj = storage.getItem("token");
             return state.tokenInfo.token ||  tokenObj &&tokenObj.token;
        },
        getUserName(state){
            const tokenObj = storage.getItem("token");
            return state.tokenInfo.userName || tokenObj &&tokenObj.userName;
        },
        getUid (state) {
            const tokenObj = storage.getItem("token");
            return state.tokenInfo.uId ||  tokenObj &&tokenObj.uId;
        },
        getTokeInfo(state){

            return  Object.values( state.tokenInfo).every(item=>item !== '')? state.tokenInfo :storage.getItem("token");
        }
    }
}