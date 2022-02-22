<template>
  <!-- .sync 是什么意思呢，官方给出的解释就是一种语法糖的意思:双向绑定值。
其实他的作用就是实现子组件修改父组件的属性的值 子组件触发了父组件的函数进行修改 .sync 修饰符的 v-bind 不能和表达式一起使用-->
  <el-dialog :visible.sync="isShow" :title="title" center @close ="closeDialog" @open = "openDialog">
    <el-card>
      <el-form ref="addData" :model="addData" :rules="rules" label-width="100px" label-position="left">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item prop="userName" label="账号:">
              <el-input v-model="addData.userName" placeholder="请输入账号" :disabled="isDisabled" @blur="userNameCheck"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="password" label="密码">
              <el-input type="password" v-model="addData.password"  placeholder="请输入密码"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item prop="uName" label="用户名:">
              <el-input v-model="addData.uName "  placeholder="请输入用户名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="age" label="年龄">
              <el-input v-model.number="addData.age"  type="number" min="1" placeholder="请输入年龄"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item prop="uid" label="用户Id:">
              <el-input v-model="addData.uid" placeholder="用户Id" :disabled="isDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="sex" label="性别">
              <el-select v-model="addData.sex" >
                <el-option v-for="item in sexSelect" :key="item.key"
                           :label="item.value"
                           :value="item.key">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <div style="text-align:right;margin:20px 0">
      <el-button size="mini" @click="closeDialog">取消</el-button>
      <el-button type="primary"   :loading="isLoading" size="mini" @click="addUser">确认</el-button>
    </div>
  </el-dialog>
</template>


<script>
import {commonAPI} from "../../api/commonAPI";
import Cryptos from "../../utils/cryptos";

export default {
  name: "addUser",
  props:{
    option:Object
  },
  data() {
    return {
        sexSelect: [
          {key: 0, value: '男'},
          {key: 1, value: '女'}
        ],
        tableData: '',
        disabled: this.$store.getters.getToken !== 'admin',
        isDisabled: '',
        flag: false,//账号是否存在
        isLoading: false,
        title: '',
        addData: {
          userName: '',
          password: '',
          uid: '',
          uName: '',
          age: '',
          sex: '',
          pageNum: 1,
          pageSize: 5
        },
       rules: {
        userName: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' },
          { required: true, message: '密码不能小于5位', trigger: 'change', min: 5 }],
        uName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
        uid: [{ required: true, message: '请输入UId', trigger: 'blur' }],
        sex: [{ required: true, message: '请输入性别', trigger: 'blur' }]
      }
    }
  },
  methods:{
    closeDialog(){
      this.option.isShow = false;
      this.flag = false;
      //dom 渲染完成之后执行；在 created 和 mounted 阶段，如果需要操作渲染后的试图，也要使用 nextTick 方法。
      this.$nextTick(()=>{
        this.$refs['addData'].resetFields();//重置表单
      })
    },
    openDialog(){
      this.title = this.option.title;
      this.isDisabled = this.option.isDisabled;
      this.addData =  this.option.tableData ;
    },
    insert(addData){
     return  commonAPI(`addUser`,addData,"put");

    },
    update(addData){
       return  commonAPI("updateUser",addData);
    },
    async addUser(){
      let mes ;
      let type;
      if (!this.flag){
         this.isLoading = true;
          const  password = Cryptos.encrypt(this.addData.password);
          const addData = {...this.addData,password};

         let result;
         let isValidate = false;
           await this.$refs['addData'].validate(valid => {
             if (valid) {
               isValidate = true;
             }
           })
           try {
             if (isValidate){
                if (this.option.isDisabled){
                  result =  await this.update(addData);
                  mes = '更新';
                }
                else {
                    mes = '添加';
                    result = await this.insert(addData);
                }
            }

        } catch (e) {
          mes = e;
          type = "error";
         }

        if (result){
          if ( result.data.info.code === '0' && result.data.data === 'success') {
            mes = mes + "成功";
            type = "success";
            this.option.isShow = false;
            this.flag = false;
            this.$emit("refreshTable");
          } else {
            mes = mes + "失败";
            type = "warn";
          }
          this.$message({
            message: mes,
            type: type
          })
        }
       }
       else {
        this.$message({
          message: "账号已存在",
          type: "warning"
        })
         mes = "";
         type = "";
       }
      this.isLoading = false;

    },
    userNameCheck(){
       if (this.addData.userName){
         commonAPI(`queryUserByName`,this.addData).then((res)=>{
            if (res.data.data.isPresent){
              this.$message.error("账号已存在!");
              this.flag = true;
            }
         })
       }
    }
  },
  computed:{
    isShow: {
      get: function () {

        return this.option.isShow
      },
      set: function (newValue) {
        this.option.isShow = newValue
      }
    }
  }
}
</script>

<style scoped>

</style>