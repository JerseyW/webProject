<template>
  <el-container>
    <el-card style="margin-top:20px" >
      <el-form :inline="true" :model="userInfo" class="demo-form-inline" size="mini" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="userInfo.userName" placeholder="用户名" ></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="userInfo.sex" >
            <el-option label="全部" value=""></el-option>
            <el-option label="男" value="0"></el-option>
            <el-option label="女" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"   v-preventDbClick="{'timer':2000,'isLoading':isLoading}" @click="onSubmit"><i class="el-icon-search"></i>查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" v-preventDbClick="{'timer':2000,'isLoading':isLoading}" @click="resetting"><i class="el-icon-refresh"></i>重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="elMain" style="margin-top:10px">
      <el-button type="primary" size="mini" :disabled="disabled" style="float:left" @click="addList()"><i class="el-icon-circle-plus-outline"></i>&nbsp;新增</el-button><br><br>
      <el-table :loading="loading" :data="tableData" stripe border size="mini">
        <el-table-column align="center" label="编号" prop="uid"></el-table-column>
        <el-table-column align="center" label="用户名" prop="userName"></el-table-column>
        <el-table-column align="center" label="姓名" prop="uname" ></el-table-column>
        <el-table-column align="center" label="年龄" prop="age"></el-table-column>
        <el-table-column align="center" label="性别">
          <template  slot-scope="scope">
            <span v-if="scope.row.sex===0">男</span>
            <span v-if="scope.row.sex===1">女</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作"  >
          <template  slot-scope="scope">
            <el-button size="mini" :disabled="isSelf(scope)" @click="addList(scope)">编辑</el-button>
            <el-button size="mini" :disabled="disabled" @click="delUser(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-footer>
      <div style="padding: 15px 0;text-align: right;">
        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="userInfo.pageNum"
                       :page-sizes="pageSizes" :page-size="userInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageTotal">
        </el-pagination>
      </div>
    </el-footer>
    <add-user :option="addIn" @refreshTable = "queryUserList"></add-user>
  </el-container>
</template>

<script>
import {commonAPI} from "../../api/commonAPI";
import addUser from "./addUser";

export default {
  name: "userList",
  components:{
   addUser
 },
  data(){
    return {
        disabled: this.$store.getters.getRole.indexOf("admin") ===-1,
        isLoading:false,
        userInfo:{
          userName:"",
          sex:'',
          pageNum: 1,
          pageSize: 5
        },

        tableData: [],
        loading: false,
        addIn: {
          isShow: false,
          title: '',
          flag: '',
          tableData: '',
          isDisabled: ''
      },
      pageSizes: [5, 10, 15, 20],
      pageTotal: 0
    }
  }
  ,methods:{
    onSubmit(){
      this.queryUserList();
    },
    resetting(){

      this.userInfo.userName = ''
      this.userInfo.sex = '';
      this.queryUserList();
    },
    addList(scope){
      this.addIn.isShow = true;
      if (scope) {
        this.addIn.title = '修改用户';
        this.addIn.tableData = scope.row;
        this.addIn.tableData.uName = scope.row.uname;
        this.addIn.isDisabled = true;

      } else {
        this.addIn.title = '新增用户';
        this.addIn.tableData = {};
        this.addIn.isDisabled = false;
      }
    },
    delUser(val){
      this.$confirm("你确定删除此条目？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(()=>{
        commonAPI('deleteUser', {id:val.row.id },"delete")
            .then(res => {
              if (res.data.data === 'success') {
                this.$message({
                  message: '删除成功',
                  type: 'success'
                })
                this.queryUserList();
              } else {
                this.$message({
                  message: '删除失败',
                  type: 'warning'
                })
              }
            })
      }).catch(() => {});

    },
    isSelf(val){

      return this.userInfo.userName !== val.row.userName && this.$store.getters.getRole&&this.$store.getters.getRole.indexOf("editor") >=0
    },
    handleSizeChange(val){
      this.userInfo.pageSize = val;
      this.userInfo.pageNum = 1;
      this.queryUserList();
    },
    handleCurrentChange(val){
      this.userInfo.pageNum = val;
      this.queryUserList();
    },
    queryUserList(){
      this.isLoading = true;
      commonAPI("queryUserList",this.userInfo).then(res=>{

         this.tableData = res.data.data.rows || [];
         this.pageTotal = res.data.data.total;
         this.isLoading = false;
      })
    }
  } ,
  mounted( ) {
     this.queryUserList();
  }
}
</script>

<style scoped>

</style>