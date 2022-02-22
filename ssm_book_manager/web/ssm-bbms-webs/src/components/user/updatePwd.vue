<template>
  <el-dialog :visible.sync="isShow" title="修改密码" center  @close ="closeDialog">
    <el-form ref="updateData" :model="updateData" label-width="130px" :rules="rules">
      <el-form-item label="密码：" prop="password">
        <el-input v-model="updateData.password" type="password" placeholder="请输入密码" style="width:77%"/>
        <!-- <span class="show-pwd" @click="showPwd">
          <i class="el-icon-view" />
        </span> -->
      </el-form-item>
      <el-form-item label="请再次输入密码:" prop="repassWord">
        <el-input v-model="updateData.repassWord" type="password" placeholder="请输入密码" style="width:77%"/>
      </el-form-item>
      <el-form-item  >
        <el-button type="primary"  :loading="isLoading" @click="updatePwd">修改密码</el-button>
        <el-button @click="resetForm('updateData')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import Cryptos from "../../utils/cryptos";
import {commonAPI} from "../../api/commonAPI";

export default {
  name: "updatePwd",
  props:{
    option: {
      required:true,
      type:Object
    },

  },
  data(){
    const validatePwd = (rule, value, callback) => {
      if (value !== this.updateData.password) {
        callback(new Error('两次密码不一致'))
      } else {
        callback();
      }
    }
    return {
      updateData:{
        password:'',
        repassWord:""
      },
      isLoading:false,
      rules: {

        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, message: '不得少于六个字符', trigger: 'blur'}
        ],
        repassWord: [{required: true, validator: validatePwd}]
      }

    }
  },
  computed:{
    isShow:{
      get () {
        return this.option.isShow;
      },
      set (newValue) {
        this.option.isShow = newValue;
      }
    }
  },
  methods:{
    closeDialog(){
      this.option.isShow = false;
      this.$refs["updateData"].resetFields();
      this.updateData = {
        repassWord: '',
        password: ''
      }
    },
    resetForm(formName){
      this.$refs[formName].resetFields();
    },
    updatePwd(){
      this.$refs.updateData.validate((valid)=>{
         if (valid){
           this.isLoading = true;
           const password =  Cryptos.encrypt(this.updateData.password);
            commonAPI('updatePassword',{userName:this.$store.getters.getUserName,password}).then((res)=>{
                if (res.data.info.code === '0' && res.data.data === 'success'){
                    this.$message.success("修改成功");
                    this.isShow = false;
                }
                else this.$message.warning("修改失败");
                this.isLoading = false;

            })
         }
      })
    }
  }
}
</script>

<style scoped>

</style>