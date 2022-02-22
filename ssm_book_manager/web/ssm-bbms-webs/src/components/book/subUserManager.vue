<template>
  <el-container style="flex-direction: column">
    <el-card style="margin-top:20px">
      <el-form  :inline="true" class="demo-form-inline" :model="subInfo" size="mini" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="subInfo.userName"  placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"  v-preventDbClick="{'timer':2000,'isLoading':isLoading}"  @click="querySub"><i class="el-icon-search"></i>查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" v-preventDbClick="{'timer':2000,'isLoading':isLoading}" @click="resetting"><i class="el-icon-refresh"></i>重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <el-table :loading="isLoading" :data="subBookData" border stripe size="mini" empty-text="暂无数据">
        <el-table-column align="center" prop="bookName" label="书名"></el-table-column>
        <el-table-column align="center" prop="userName" label="用户名"></el-table-column>
        <el-table-column align="center" prop="subDateTime" sortable label="借阅时间"></el-table-column>
        <el-table-column align="center" prop="subDateTime" sortable label="待归还时间"></el-table-column>
        <el-table-column align="center"   label="状态">
          <template slot-scope="scope">
            <span  v-bind:class="`urge-${scope.row.returnType}`">
                  {{scope.row.returnType|typeFormater}}
            </span>

          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" >
          <template  slot-scope="scope">
            <el-button size="mini" :disabled="scope.row.returnType ===2 ||scope.row.returnType ===3 ||scope.isLoading" @click="urgeBook(scope)">催还</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </el-container>
</template>

<script>
import {commonAPI} from "../../api/commonAPI";
const  bookStatusMap = new Map();
bookStatusMap.set("urge-0","未归还");
bookStatusMap.set("urge-1","未催还");
bookStatusMap.set("urge-2","已催还");
bookStatusMap.set("urge-3","已归还");
export default {
  name: "subBook",
  data(){

    return {
      isLoading:false,
      subInfo:{
        userName:'',
        uId:'',
        bId:'',
        returnType:1
      },
      subBookData:[],
      tableDataUser:[],
      tableDataBook:[]
    }
  },
  //局部过滤器
  filters:{
    typeFormater(value){
      return bookStatusMap.get("urge-"+value);
    }
  },
  methods:{
    querySub(){
      if (this.subInfo.userName){
         this.queryUser();
      }
      else {
       this.querySubBooks();
      }
    },

    resetting(){
      this.subInfo.userName = ''
      this.querySub();
    },
    querySubBooks(){
      commonAPI('querySub',{  }).then((res)=>{
        this.subBookData = res.data.data.row||[];

      }).catch(error=>{
        console.log(error)
      })
    },
    queryUser(){
      commonAPI(`queryUserByName`,this.subInfo).then((res)=>{
        if (res.data.data.isPresent){
          this.subInfo.uId = res.data.data.uId;
          this.querySubBooks();
        }
        else this.$message.warning("用户不存在")
      })
    },

    urgeBook(scope){
      scope.isLoading = true;
      commonAPI('returnSubBook', { uId: scope.row.uid, bId: scope.row.bid, returnType: 2 }).then(res => {
        scope.isLoading = false;
        this.querySub();
        if (res.data.data === 'success') {
          this.$message({
            message: '催还成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '催还失败，请再次催还',
            type: 'warning'
          })
        }
      })
    },
  }
  ,mounted() {
    this.querySub();

  }
}
</script>

<style scoped lang="less">

  .urge-2{
    color: orange;
  }
  .urge-0{
    color: red;
  }
  .urge-3{
    color: gray;
  }
  .urge-1{
    color: #B3C0D1;
  }

</style>