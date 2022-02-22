<template>
  <el-container style="height: 100%;" class="abc" :class="{active:isActive}">
    <!-- 头部 -->
    <el-header style="text-align: right; font-size: 12px">
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link" >
          你好，{{uName}}<i class="el-icon-setting el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown" >
          <el-dropdown-item command="update">修改密码</el-dropdown-item>
          <el-dropdown-item command="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-container>
      <!-- 左侧栏 -->
      <el-aside width="200px" >
        <el-menu :default-active="activeItem"    collapse-transition    active-text-color="#ffd04b"  :default-openeds="opends">
          <!--如果菜单(menu)是true 循环侧栏路由列表  -->
          <template v-for="item in showCurrentMenu"     >
            <!-- 这里必须设置index,相当唯一标识这个菜单标签，否则菜单列表点击后随意展开 -->
            <el-submenu :index="item.meta.funcNode+''" :key="item.path"   v-if="item.children.length" >
              <template slot="title"><i :class="item.meta.icon"></i>{{item.meta.title}}</template>
              <!-- 如果菜单有孩子菜单，则循环孩子菜单 -->
              <template  v-if="item.children.length">
                <div v-for="(itemC) in  item.children"   :key="itemC.path " >
                  <el-menu-item :index="(itemC.meta.funcNode+'')"  @click="clickMenu(itemC)"   >
                    <i :class="itemC.meta.icon"></i>{{itemC.meta.title}}
                  </el-menu-item>
                </div>
              </template>
            </el-submenu>
          </template>
        </el-menu>
      </el-aside>
      <!-- 内容渲染 -->
      <el-main>
        <router-view />
      </el-main>
    </el-container>

    <!-- dialog -->
    <updatePwd :option="option"/>
  </el-container>

</template>

<script>

import updatePwd from "../user/updatePwd";
import preventDbClick from "../../directive/preventDbClick";
export default {
  name: "layout",
  components:{
    updatePwd
  },
  data(){
    return {
      option:{
        isShow: false
      },
      opends: [],
      menuData:this.$store.getters.getRoutes  ,
      uName:"",
      isActive:true,
      activeItem:''
    }
  },
  computed:{

    showCurrentMenu () {
     let data =  this.menuData.filter(item => item.meta && item.meta.menu);
      data.map((item)=>{
        if (item.children && item.children.length){
          item.children.map((ele)=>{
            if (ele.meta.funcNode){
              this.opends.push(ele.meta.funcNode + '')
            }
          })
        }
      })
      return data;
    }

  },methods:{
    handleCommand: function (type) {
      if (type === 'logout') {
        this.$store.dispatch("removeToken");
        this.$store.dispatch("removeRoles");
        this.$router.replace('/login');
      }
      else {
          this.option.isShow = true;
      }
    },
    clickMenu(item){
      if (this.activeItem ===  item.meta.funcNode) return
      this.$router.push(
          {
            name:item.name
          }
      );
      this.activeItem = item.meta.funcNode;
    }
  },mounted() {

    this.activeItem =  this.$route.meta.funcNode||this.opends[0];
  }
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
.user-avatar {
  cursor: pointer;
  width: 40px;
  height: 40px;
  border-radius: 10px;
}
.el-aside,
.el-menu,
.el-submenu,
.el-menu-item {
  background-color: rgb(238, 241, 246);
}

body {
  margin: 0;
}
</style>