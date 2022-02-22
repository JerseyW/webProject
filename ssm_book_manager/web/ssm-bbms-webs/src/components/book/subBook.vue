<template>
  <el-container>
    <el-card style="margin-top:20px" >
      <el-form :inline="true" ref="subInfo" :model="subInfo" class="demo-form-inline" size="mini" label-width="80px">
        <el-form-item label="书名">
          <el-input v-model="subInfo.bookName" placeholder="书名" ></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="subInfo.author" placeholder="作者" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"   v-preventDbClick="{'timer':2000,'isLoading':isLoading}" v-on:click="queryBook"><i class="el-icon-search"></i>查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" v-preventDbClick="{'timer':2000,'isLoading':isLoading}" @click="resetting"><i class="el-icon-refresh"></i>重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="elMain" style="margin-top:10px">
      <el-button type="primary" size="mini" :disabled="isLoading" style="float:left" @click="printBookInfo()"><i class="el-icon-circle-plus-outline"></i>&nbsp;打印</el-button><br><br>
      <el-table :data="bookList" stripe border size="mini" >
        <el-table-column align="center" label="编号" prop="bid"></el-table-column>
        <el-table-column align="center" label="书名" prop="bookName"></el-table-column>
        <el-table-column align="center" label="作者" prop="author"></el-table-column>
        <el-table-column align="center" label="库存" sortable prop="inventory"></el-table-column>
        <el-table-column align="center" label="单价" sortable prop="price"></el-table-column>
        <el-table-column align="center" label="操作"  >
          <template  slot-scope="scope">
              <el-button size="mini" v-if="!scope.row.isCurrentSub &&  !scope.row.isCurrentSub" :disabled="isLoading || !scope.row.inventory"  @click="subBook(scope)">借阅</el-button>
              <el-button size="mini" v-if="scope.row.bookType ===1 && scope.row.isCurrentSub" disabled>待归还</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-footer>
      <div style="padding: 15px 0;text-align: right;">
        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="subInfo.pageNum"
                       :page-sizes="pageSizes" :page-size="subInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageTotal">
        </el-pagination>
      </div>
    </el-footer>
    <confirm-sub :option="subInfo"  @refreshTable = "sendSubBook"></confirm-sub>
    <print-subBook :option="printInfo"></print-subBook>
  </el-container>
</template>
<script>
import {commonAPI} from "../../api/commonAPI";
import {formatDate} from "../../utils/dateUtil";
import printSubBook from "./printSubBook";
const  confirmSub = ()=>import("@/components/book/confirmSub")

export default {
  name: "subBook",
  components:{
    confirmSub,
    printSubBook
  },
  data(){
    return {
      isLoading:false,
      printInfo:{
        isPrint:false
      },
      subInfo:{
        bId:"",
        uId:'',
        isShow:false,
        bookName:'',
        author:'',
        pageNum: 1,
        pageSize: 10,
        returnDateTime:"",
        bookType: 1, //0 未订阅，1 已订阅
        returnType: 0//0 未归还 1 未催还 2 已崔还,3已归还
      },
      bookList:[],
      pageTotal:0,
      pageSizes: [5, 10, 15, 20],
    }
  },methods:{
    queryBook: async function () {
      try {
        const subInfo = await this.queryAlreadySubUser();
        const bookInfo = await this.queryBookList();
        if (subInfo.data.info.code === '0' && bookInfo.data.info.code === '0') {
          const userSub = subInfo.data.data.userSub;
          this.bookList = bookInfo.data.data.rows;
          this.pageTotal = bookInfo.data.data.total;
           if (userSub){
             userSub.userSubBook.map(item=>{
               this.bookList.map(su=>{
                 if (item.bid === su.bid)  su.isCurrentSub = true;
               })
             })
           }
        }
      } catch (e) {
        this.$message.error(e);
      }
    },
    resetting(){
      this.subInfo.bookName = ''
      this.subInfo.author = ''
      this.queryBook();
    },
    printBookInfo(){
      this.printInfo.isPrint = true;
    },
    subBook(scope){
      console.log(scope)
      if (scope.bookType && scope.row.subDateTime){
        this.$message({
          message: '已借阅此书，上次借阅时间：' + formatDate(new Date(scope.row.subDateTime),true),
          type: 'warning'
        })
        return
      }
      this.subInfo.isShow = true;
      this.subInfo.bookName = scope.row.bookName;
      this.subInfo.author = scope.row.author;
      this.subInfo.subDateTime = formatDate(new Date(),true);
      this.subInfo.uId = parseInt(this.$store.getters.getUid);
      this.subInfo.bId = scope.row.bid;
    },
    sendSubBook(returnDateTime){
      this.isLoading = true;
      this.subInfo.returnDateTime = formatDate(new Date(returnDateTime),true);
      commonAPI('addSubBook',this.subInfo).then((res)=>{
        if (res.data.info.code === '0' && res.data.data === 'success'){
          this.subInfo.bookName = '';
          this.subInfo.author = '';
          this.queryBook();
          this.$message({
            message: '借阅成功，请记得归还',
            type: 'success'
          })
        }
        else {
          this.$message({
            message: '借阅失败，请联系管理员',
            type: 'warning'
          })
        }
        this.isLoading = false;

      }).catch((e)=>{
        this.isLoading = false;
        this.$message.error(e);
      })
    },
    queryAlreadySubUser(){
      return commonAPI(`queryAlreadySubUser`,{id:parseInt(this.$store.getters.getUid),bookType:1});
    },
    queryBookList(){
       return  commonAPI('queryBookList',this.subInfo);
    },
    handleSizeChange(val){
      this.subInfo.pageNum = 1;
      this.subInfo.pageSize = val;
      this.queryBook();
    },
    handleCurrentChange(val){
      this.subInfo.pageNum = val;
      this.queryBook();
    }
  },mounted() {
    this.queryBook();
  },
  beforeDestroy(){
     this.$off();
  }
}
</script>

<style scoped>

</style>