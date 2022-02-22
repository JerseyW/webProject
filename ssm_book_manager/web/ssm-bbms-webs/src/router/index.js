/**
 @author: Jersey
 @create: 2022-02-12 19:16
 @version: V1.0
 @slogan: 业精于勤,荒于嬉;行成于思,毁于随。
 @description:
 */

import VueRouter from 'vue-router'
import Vue from "vue";
import store from "../store";
import {Message} from "element-ui";

Vue.use(VueRouter);

//解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.replace;
//push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
    if (onResolve || onReject)
        return originalPush.call(this, location, onResolve, onReject);
    return originalPush.call(this, location).catch(err => err);
};
//replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
    if (onResolve || onReject)
        return originalReplace.call(this, location, onResolve, onReject);
    return originalReplace.call(this, location).catch(err => err);
};

let accessedRoutes = [];
//基本路由
export  const  baseRouters= [

    {
        path:'/',
        redirect:'/login',
        meta:{
            menu:false
        }
    },
    {
        path: "/login",
        name:'login',
        //懒加载
        component:()=> import('@/components/login/login.vue'),

        meta: {
            menu:false
        }
    },{
        path: "/user",
        name: "user",
        component:()=> import("@/components/layout/layout.vue"),
        meta: {
            title: '用户管理',
            icon: 'el-icon-user',
            menu: true,
            funcNode: '1'
        },
        children: [
            {
                path: '/user/userList',
                name: 'userList',
                component: () => import('@/components/user/userList'),
                meta: {
                    title: '用户列表',
                    icon: 'el-icon-notebook-2',
                    menu: true,
                    funcNode: '1-1'
                }
            }
        ]
    },
    {
        path: "*/404",
        name: '404',
        component:()=>import('@/components/errorPage/page404.vue')
    }

]
//动态路由根据权限加载
export const asyncRoutes=[
    {
        path:'/book',
        name:'book',
        component:()=>import("@/components/layout/layout.vue"),
        meta:{
            title:"图书管理",
            icon:"el-icon-reading",
            menu:true,
            funcNode:"2",
            role:['admin','editor']
        },
        children:[
            {
                path:"/book/bookManager",
                name:"bookManager",
                component:()=>import('@/components/book/bookManager.vue'),
                meta: {
                    title: '图书管理',
                    icon: 'el-icon-notebook-2',
                    menu: true,
                    funcNode: '2-1',
                    role: ['admin']
                }
            },
            {
               path: "/book/subBook",
               name: "subBook",
               component:()=> import('@/components/book/subBook.vue'),
               meta:{
                   title: "图书借阅",
                   menu: true,
                   icon: 'el-icon-notebook-1',
                   funcNode: '2-2',
                   role: ['editor']
               }
            },
            {
                path: "/book/returnBook",
                name: "returnBook",
                component:()=> import('@/components/book/returnBook.vue'),
                meta:{
                    title: "图书归还",
                    menu: true,
                    icon: 'el-icon-collection',
                    funcNode: '2-3',
                    role: ['editor']
                }
            },
            {
                path: '/book/subUserManage',
                name: 'subUserManage',
                component: () => import('@/components/book/subUserManager'),
                meta: {
                    title: '借阅用户',
                    icon: 'el-icon-document-copy',
                    menu: true,
                    funcNode: '2-4',
                    role: ['admin']
                }
            }
        ]
    }, {
        path: '/sys',
        name: 'sysLayout',
        component: () => import('@/components/layout/layout.vue'),
        meta: {
            title: '系统管理',
            icon: 'el-icon-setting',
            menu: true,
            funcNode: '3',
            role: ['admin']
        },
        children: [
            {
                path: '/sys/sysLogList',
                name: 'sysLogList',
                component: () => import('@/components/sys/sysLogList'),
                meta: {
                    title: '系统访问日志',
                    icon: 'el-icon-notebook-1',
                    menu: true,
                    funcNode: '3-1',
                    role: ['admin']
                }
            }
        ]
    }
]

const  router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    // 切换路由后滚动条置顶
    scrollBehavior: () => ({ x:0,y: 0 }),
    routes:baseRouters
})

router.afterEach((to)=>{
    document.title = to.meta.title || '图书管理系统';
})
router.beforeEach(async (to, from, next) => {

    if (localStorage.getItem('token')) {
        if (accessedRoutes.length){
            next();
            return
        }
        const roles = store.getters.getRole;
        if (roles && roles.length){
            try {
                if (!accessedRoutes.length){
                    //生成动态路由
                    accessedRoutes = await store.dispatch("setRouters", roles);
                    for(let i =0,length = accessedRoutes.length;i<length;i++){
                        router.addRoute(accessedRoutes[i]);//此方法要求参数为数组
                    }
                   //添加成功的路由不会马上在router.option 查到，可以使用router.getRoutes()查看
                    // router.addRoutes(accessedRoutes); //旧版本
                    next({...to,replace:true});
                }
            } catch (error) {
                await store.dispatch('removeToken');
                await store.dispatch("removeRoles");
                //替换路由，初始化为基本路由
                /*  router.matcher = new VueRouter({
                      routes:router
                  }).matcher*/
                Message.error(error || 'Has Error')
                next({path: '/login'});
            }
        }
        else {
            next();
        }
    }
    else {
        if (to.path === '/login'){
            accessedRoutes = [];
            next();
        }
        else  next({path: '/login'});
    }

    //动态添加进去的路由，暂时无法访问到，option对象里没有，需要判断to 的path是否在router.getRoutes()里才能判断404
    if (!to.matched.length && !router.getRoutes().some((e)=>e.path === to.path)){
        next('404');
    }
})
export  default   router