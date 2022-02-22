<template>
<el-container direction="vertical">
   <el-header class="padding">
     <el-card>
       <el-form :inline="true" :model="queryReturnBook" size="mini" label-width="80px" class="demo-form-inline">
         <el-form-item label="书名">
           <el-input v-model="queryReturnBook.bookName" placeholder="书名" ></el-input>
         </el-form-item>
         <el-form-item>
           <el-button type="primary" @click="queryBook"><i class="el-icon-search"></i>查询</el-button>
         </el-form-item>
         <el-form-item>
           <el-button type="danger" @click="resetting"><i class="el-icon-refresh"></i>重置</el-button>
         </el-form-item>
       </el-form>
     </el-card>
   </el-header>
   <el-main class="padding">
     <el-card   style="margin-top:40px">
       <el-table  :data="returnBookList" stripe border size="mini">
         <el-table-column align="center" label="书名" prop="bookName"></el-table-column>
         <el-table-column align="center" label="作者" prop="author"></el-table-column>
         <el-table-column align="center" label="价格" prop="price"></el-table-column>
         <el-table-column align="center" label="借阅时间"  sortable prop="subDateTime"></el-table-column>
         <el-table-column align="center" label="待归还时间" sortable prop="returnDateTime"></el-table-column>
         <el-table-column align="center" label="状态" :sortable="true" :sort-method="sortSubBook">
           <template slot-scope="scope">
             <span  v-bind:class="`urge-${scope.row.returnType}`">{{scope.row.returnType|typeFormater}}</span>
           </template>
         </el-table-column>
         <el-table-column align="center" label="操作">
           <template slot-scope="scope">
             <el-button size="mini" :disabled="scope.row.returnType===3 || scope.loadIng" @click="returnBook(scope)">归还</el-button>
           </template>
         </el-table-column>
       </el-table>
     </el-card>
  </el-main>
  <el-footer>
    <el-pagination background style="float:right" @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryReturnBook.pageNum" :page-sizes="[5, 10, 20, 40]" :page-size="queryReturnBook.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="pageTotal">
    </el-pagination>
  </el-footer>
</el-container>
</template>

<script>
import {commonAPI} from "../../api/commonAPI";
import {formatDate} from "../../utils/dateUtil";

const  bookStatusMap = new Map();
bookStatusMap.set("urge-0","未归还");
bookStatusMap.set("urge-1","未被催还");
bookStatusMap.set("urge-2","已被催还");
bookStatusMap.set("urge-3","已归还");
export default {
  name: "returnBook",
  data(){
    return{
      queryReturnBook:{
        returnType:0,
        bookName:"",
        uId: parseInt(this.$store.getters.getUid),
        pageNum: 1,
        pageSize: 10,
      },
      pageTotal:0,
      returnBookList:[]
    }
  },methods:{
    returnBook(scope){

      scope.loadIng = true;
      const params = {
        bookType:0,//重新借阅
        returnType:3,//设置归还
        bId:scope.row.bid,
        uId: parseInt(this.$store.getters.getUid),
        returnDateTime:formatDate(new Date(),true)//归还时间
      }

      commonAPI('returnSubBook',params).then((res)=>{
        if (res.data.info.code === '0'){
          this.returnBookList = res.data.data.row;
          this.pageTotal = res.data.data.total;
          this.$message({
            message: '归还成功',
            type: 'success'
          })
        }else {
          this.$message({
            message: '归还失败，请联系管理员',
            type: 'warning'
          })
        }
        this.queryBook();
        scope.loadIng = false;
      }).catch((e)=>{
        this.isLoading = false;
        this.$message.error(e);
      })
    },
    queryBook(){
      this.isLoading =true;
      commonAPI('querySub',this.queryReturnBook).then((res)=>{
        if (res.data.info.code === '0'){
          this.isLoading = false;
          this.returnBookList = res.data.data.row;
          this.pageTotal = res.data.data.total;
        }

      }).catch((e)=>{
        this.isLoading = false;
        this.$message.error(e);
      })
    },
    resetting(){
     this.queryReturnBook.bookName = '';
     this.queryBook();
    },
    handleSizeChange(val){
      this.queryReturnBook.pageSize = val;
      this.queryReturnBook.pageNum = 1;
      this.queryBook();

    },
    handleCurrentChange(val){
      this.queryReturnBook.pageNum = val;
      this.queryBook();
    },
    sortSubBook (a, b) {
      return a.bookType - b.bookType
    }

  },
  filters:{
    typeFormater(value){
      return bookStatusMap.get("urge-"+value);
    }
  },mounted() {
    this.queryBook();
  }
}
</script>

<style scoped>
  .padding{
    padding: 20px 0;
  }

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