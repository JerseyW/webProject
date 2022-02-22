
import  Vuex from 'vuex';
import Vue from "vue";
Vue.use(Vuex);

import user from "./user";
import roles from "./roles";
import Storage from "../utils/stroage";

export const storage =  new Storage();
export const  TOKEN_EXPIRED = 1000 * 60 * 60;
//vuex 模块化配置
export  default  new Vuex.Store({
    modules:{
        user,
        roles
    }
});