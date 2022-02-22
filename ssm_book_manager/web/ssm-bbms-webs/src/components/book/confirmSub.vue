<template>
 <el-container>
   <el-dialog :visible.sync="isShow"  center @close ="closeDialog" @open = "openDialog">
     <el-form :inline="true" ref="sub" :data="sub" class="demo-form-inline" size="mini" label-width="80px">
       <el-form-item label="书名">
         <el-input disabled v-model="sub.bookName" > </el-input>
       </el-form-item>
       <el-form-item label="作者">
         <el-input disabled v-model="sub.author"></el-input>
       </el-form-item>
       <el-form-item label="借阅时间">
         <el-input disabled v-model="sub.subDateTime"></el-input>
       </el-form-item>
       <el-form-item label="归还时间" label-width="180">
           <el-date-picker v-model="sub.returnDateTime" type="datetime" placeholder="选择日期时间" default-time="12:00:00"></el-date-picker>
       </el-form-item>
       <el-form-item align="center" style="width: 100%">
         <el-button type="primary" @click="confirmSub">确认</el-button>
         <el-button type="primary" @click="cancelSub">取消</el-button>
       </el-form-item>
     </el-form>
   </el-dialog>
 </el-container>
</template>

<script>
export default {
  name: "confirmSub",
  props:['option'],
  data(){
    return{
      sub:{
        bookName:'',
        author:'',
        subDateTime:"",
        returnDateTime:''
      }
    }
  },
  methods:{
    confirmSub(){
         if(!this.sub.returnDateTime){
           this.$message.warning("不能为空");
            return
         }
         if (new Date(this.sub.returnDateTime).getTime() < new Date(this.sub.subDateTime).getTime()){
           this.$message.warning("不能早于借阅时间");
            return
         }
       this.option.isShow = false;
       this.$emit("refreshTable",this.sub.returnDateTime);

    },
    cancelSub(){
       this.closeDialog();
    },
    closeDialog(){
      this.option.isShow = false;
      this.$refs['sub'].resetFields();
    },
    openDialog(){
      this.sub = this.option;
    }
  },
  computed:{
    isShow:{
      set(value){
        this.option.isShow = value;
      },
      get(){
        return this.option.isShow;
      }
    }
  }
}
</script>

<style scoped>

</style>