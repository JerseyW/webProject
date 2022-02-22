/**
 @author: Jersey
 @create: 2022-02-12 21:17
 @version: V1.0
 @slogan: 业精于勤,荒于嬉;行成于思,毁于随。
 @description:
 */
import  {asyncRoutes,baseRouters} from "../router";
import {storage} from "./index";

function  hasPermission(roles,route){
    if (route.meta && route.meta.role)
      return roles.some(role => route.meta.role.indexOf(role) >= 0)
    return  true
}
//根据权限过滤路由
function  filterAsyncRoutes(asyncRoutes, roles){
    let res = [];
    asyncRoutes.map(item=>{
        const tmp = { ...item };
        if (hasPermission(roles,tmp)){
            if (item.children){
                tmp.children = filterAsyncRoutes(item.children,roles);
            }
            res.push(tmp);
        }
    })
    return res;
}
export  default {
    namespace:true,
    actions:{
        setRoles (context, roles) {
            context.commit('setRoles', roles);
        },
        setRouters({commit},roles){

            return new Promise(resolve => {
                const accessedRoutes =  filterAsyncRoutes(asyncRoutes,roles);
                commit('setRouters',accessedRoutes);
                resolve(accessedRoutes);
            });
        },
        removeRoles(context){
            context.commit('removeRoles', "roles");
        }
    },
    mutations:{
        setRoles(state,roles){
            storage.setItem("roles", roles);
            state.roles = roles;
        },
        setRouters(state, routers){
          state.addRouters = routers;
          state.routers = baseRouters.concat(routers);
        },
        removeRoles(state, roles){
            storage.removeItem(roles);
            state.roles= [];
        }
    },
    state:{
        routers: baseRouters,
        addRouters: [],
        roles:[]
    },getters:{
        getRole (state) {

            return state.roles && state.roles.length? state.roles : storage.getItem("roles")
        },
        getRoutes (state) {
            return state.routers || baseRouters
        }
    }
}