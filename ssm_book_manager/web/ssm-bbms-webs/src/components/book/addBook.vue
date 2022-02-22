<template>
  <el-dialog :visible.sync="isShow" :title="title" center @close ="closeDialog" @open = "openDialog">
   <el-card>
     <el-form ref="addBook" :model="addBookInfo" :rules="rules" label-width="100px" label-position="left">
       <el-row :gutter="30">
         <el-col :span="12">
           <el-form-item prop="bookName" label="书名:">
             <el-input v-model.trim="addBookInfo.bookName" :disabled="isDisabled" placeholder="请入书名"  @blur="bookNameCheck"></el-input>
           </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item prop="author" label="作者:">
             <el-input type="text" v-model.trim="addBookInfo.author" @blur="watchChangeValue($event,'author')"  placeholder="请输入作者"></el-input>
           </el-form-item>
         </el-col>
       </el-row>
       <el-row :gutter="30">
         <el-col :span="12">
           <el-form-item prop="inventory" label="库存">
             <el-input v-model.number="addBookInfo.inventory" @blur="watchChangeValue($event,'inventory')" placeholder="库存" ></el-input>
           </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item prop="price" label="单价">
             <el-input v-model="addBookInfo.price" @blur="watchChangeValue($event,'price')" placeholder="单价"></el-input>
           </el-form-item>
         </el-col>
       </el-row>
     </el-form>
   </el-card>
    <div style="text-align:right;margin:20px 0">
      <el-button size="mini" @click="closeDialog">取消</el-button>
      <el-button type="primary"   :loading="isLoading" size="mini" @click="addBook">确认</el-button>
    </div>
 </el-dialog>
</template>

<script>
import {commonAPI} from "../../api/commonAPI";


export default {
  name: "addBook",
  props:{
     option:Object
  },
  data(){
    const isNumber =(role,value,callback)=>{
       if (!Number(value)) {
         callback(new Error('只能为数字'))
       }
       else callback();
    }
    return {
        title:"",
        isLoading:false,
        isPresent :false,
        isDisabled:false,
        watchChange:{
          isChange:false
        },
        addBookInfo:{
          bookName:'',
          author:'',
          inventory:'',
          price:'',
        },
        rules: {
          bookName: [{ required: true, message: '请输入书名', trigger: 'blur' }],
          author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
          inventory: [{ required: true, message: '请输入库存', trigger: 'blur' }, {trigger: 'blur', validator: isNumber}],
          price: [{ required: true, message: '请输入单价', trigger: 'blur' },{trigger: 'blur', validator: isNumber}]
       }
     }
  },methods:{
    openDialog(){
      this.title = this.option.title;
      this.addBookInfo =  this.option.bookData;
      this.isDisabled = this.option.isDisabled
      this.watchChange.isChange = false;
    },
    closeDialog(){
      this.option.isShow = false;
      this.isPresent = false;
      //dom 渲染完成之后执行；在 created 和 mounted 阶段，如果需要操作渲染后的试图，也要使用 nextTick 方法。
      this.$nextTick(()=>{
        this.$refs['addBook'].resetFields();//重置表单
      })
    },
    bookNameCheck(){
        if (this.addBookInfo.bookName){
          commonAPI(`queryBookName`,this.addBookInfo).then((res)=>{
            if (res.data.data.isPresent){
              this.$message.error("图书已存在!");
              this.isPresent = true;
            }
          })
        }

    },
    addBook(){
      if (!this.isPresent ) {
        this.$refs.addBook.validate(valid => {
          if (valid) {
            if (!this.isDisabled) { // 新增图书
              this.isLoading = true;
              commonAPI('addBook', this.addBookInfo,'put')
                  .then(res => {
                    this.option.isShow = false;
                    if (res.data.data === 'success') {
                      this.$emit("refreshTable");
                      this.$message({
                        showClose: true,
                        message: '添加成功',
                        type: 'success'
                      })
                    } else {
                      this.$message.error('添加失败，请重试');
                    }
                    this.isLoading = false;
                  })
            } else {

              if(this.watchChange.isChange){
                commonAPI('editBook', this.addBookInfo).then(res => {
                  if (res.data.data === 'success') {
                    this.option.isShow = false;
                    this.$emit("refreshTable");
                    this.$message({
                      showClose: true,
                      message: '修改成功',
                      type: 'success'
                    })
                  } else {
                    this.$message.error('修改失败，请重试');
                  }
                  this.isLoading = false;
                })
              }  else   this.option.isShow = false;
              this.watchChange.isChange = false;
            }
          }
        })
      }
    },watchChangeValue(target,e){
           this.$watch(`addBookInfo.${e}`,{
               handler(newValue,oldValue){
                 if (newValue !== oldValue ){
                   this.watchChange.isChange = true;
                 }

               },immediate:true
           })
    }
  },computed:{
     isShow:{
        get(){
          return this.option.isShow
        },
        set(value){
          this.option.isShow = value;
        }
     }
  }


}
</script>

<style scoped>

</style>