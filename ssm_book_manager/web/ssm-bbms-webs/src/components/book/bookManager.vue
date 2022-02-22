<template>
<el-container style="flex-direction: column">
  <el-card style="margin-top:20px">
    <el-form  :inline="true" class="demo-form-inline" :model="bookInfo" size="mini" label-width="80px">
      <el-form-item label="书名">
          <el-input v-model="bookInfo.bookName"  placeholder="请输入书名"></el-input>
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="bookInfo.author" placeholder="请输入作者"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"  v-preventDbClick="{'timer':2000,'isLoading':isLoading}"  @click="queryBooks"><i class="el-icon-search"></i>查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" v-preventDbClick="{'timer':2000,'isLoading':isLoading}" @click="resetting"><i class="el-icon-refresh"></i>重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <el-card style="margin-top: 10px">
    <el-button type="primary" size="mini"  style="float:left" @click="addBook()"><i class="el-icon-circle-plus-outline"></i>&nbsp;新增</el-button><br><br>
    <el-table :loading="isLoading" :data="bookData" border stripe size="mini">
      <el-table-column align="center" prop="bid" label="图书编号"></el-table-column>
      <el-table-column align="center" prop="bookName" label="书名"></el-table-column>
      <el-table-column align="center" prop="author" label="作者"></el-table-column>
      <el-table-column align="center" prop="inventory" label="库存"></el-table-column>
      <el-table-column align="center" prop="price" label="价格"></el-table-column>
      <el-table-column align="center" label="操作"  >
        <template  slot-scope="scope">
          <el-button size="mini"  @click="addBook(scope)">编辑</el-button>
          <el-button size="mini"  @click="deleteBook(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <el-footer>
    <div style="padding: 15px 0;text-align: right;">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="bookInfo.pageNum"
                     :page-sizes="pageSizes" :page-size="bookInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageTotal">
      </el-pagination>
    </div>
  </el-footer>
  <add-book :option="editBook" @refreshTable = "queryBooks"></add-book>
</el-container>
</template>

<script>
import {commonAPI} from "../../api/commonAPI";
import AddBook from "./addBook";

export default {
  name: "bookManager",
  components: {AddBook},
  data(){
    return{
       isLoading:false,
       bookInfo:{
          bookName:'',
          author:'',
          pageNum:1,
          pageSize: 5
       },
       pageSizes: [5, 10, 15, 20],
       pageTotal: 0,
       bookData:[],
       editBook:{
           isShow:false,
           bookData:{},
           isDisabled:false
       }
    }
  },methods:{
    queryBooks(){
        this.isLoading = true;
        commonAPI('queryBookList',this.bookInfo).then((res)=>{
          this.bookData = res.data.data.rows||[];
          this.pageTotal = res.data.data.total;
          this.isLoading = false;
        }).catch(e=>{
          this.$message.error(e);
          this.isLoading = false;
        })
    },
    handleSizeChange(val){
      this.bookInfo.pageSize = val;
      this.bookInfo.pageNum = 1;
      this.queryBooks();
    },
    handleCurrentChange(val){
      this.bookInfo.pageNum = val;
      this.queryBooks();
    },
    resetting(){
      this.bookInfo.bookName = ''
      this.bookInfo.author = '';
      this.queryBooks();
    },
    addBook(scope){
      this.editBook.isShow = true;
       if (scope){
         this.editBook.bookData = scope.row;
         this.editBook.isDisabled = true;
         this.editBook.title = "编辑";
       }else {
         this.editBook.isDisabled = false;
         this.editBook.title = "新增";
         this.editBook.bookData = {};
       }
    },
    deleteBook(val){
      this.$confirm("你确定删除此条目？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(()=>{
        commonAPI('deleteBook', {bId:val.row.bid },"delete")
            .then(res => {
              if (res.data.data === 'success') {
                this.$message({
                  message: '删除成功',
                  type: 'success'
                })
                this.queryBooks();
              } else {
                this.$message({
                  message: '删除失败',
                  type: 'warning'
                })
              }
            })
      }).catch(() => {});

    }
  },mounted() {
    this.queryBooks();
  }
}
</script>

<style scoped>

</style>