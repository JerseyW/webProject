<template>

  <div class="login-container">

    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" autocomplete="on" label-position="left">
      <div class="title-container">
        <h3 class="title">登 &nbsp; 录 &nbsp; 界 &nbsp; 面</h3>
      </div>

      <!--Form-Item中的prop属性设置需要校验的字段名-->
      <el-form-item prop="userName">
        <span class="svg-container">
          <i class="el-icon-user-solid"></i>
        </span>
        <el-input ref="userName" v-model="loginForm.userName" placeholder="用户名" name="userName" type="text" tabindex="1" autocomplete="on"/>
      </el-form-item>

      <el-tooltip v-model="capsTooltip" content="大写开启" placement="right" manual>
        <el-form-item prop="password">
          <span class="svg-container">
            <i class="el-icon-lock"></i>
          </span>
          <!--.native:自定义组件绑定原生的方法-->
          <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType" placeholder="密码" name="password"
              tabindex="2"
              autocomplete="on"
              @keyup.native="checkCapslock"
              @blur="capsTooltip = false"
              @keyup.enter.native="handleLogin"/>
          <span class="show-pwd" @click="showPwd">
            <i class="el-icon-view"></i>
          </span>
        </el-form-item>
      </el-tooltip>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click="handleLogin">登录</el-button>
    </el-form>

  </div>
</template>

<script>
import {commonAPI} from "../../api/commonAPI";

import Cryptos from "../../utils/cryptos";
import store from "../../store";

export default {
  name: "login",
  data() {
    return {
      loginForm: {
        userName: "",
        password: ""
      },
      capsTooltip: false,
      passwordType: "password",
      loading: false,
      loginRules: {
        userName: [
          {
            required: true,
            trigger: 'blur',
            message: '请填写用户名'
          }
        ],
        password: [
          {
            required: true,
            trigger: 'blur',
            message: '请填写密码'
          },
          {type: 'string', min: 6, message: '密码至少为6位', trigger: 'blur'}
        ]
      }
    }
  },methods:{
    checkCapslock ({ shiftKey, key } = {}) {
      if (key && key.length === 1) {
        this.capsTooltip = (shiftKey && (key >= 'a' && key <= 'z')) ||
            (!shiftKey && (key >= 'A' && key <= 'Z'));
      }
      if (key === 'CapsLock' && this.capsTooltip === true) {
        this.capsTooltip = false
      }
    },
    showPwd (e) {
          if (this.passwordType === 'password'){
            this.passwordType = '';
          }
          else this.passwordType = 'password';
          this.passwordType === ''
          ? e.target.setAttribute('style', 'color: #409EFF')
          : e.target.setAttribute('style', 'color: #c0c4cc')
    },
    handleLogin: function () {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
 /*         let md5 = crypto.createHash('md5')
          md5.update(this.loginForm.password) //  需要加密的密码
          this.loginForm.password = md5.digest('hex') //  password 加密完的密码*/
          const password =  Cryptos.encrypt(this.loginForm.password);
          const loginData = {...this.loginForm,password};
          // 为了看到登录转圈圈的加载效果，这里来一个延迟1秒发送请求
          // 封装的公共请求API
          commonAPI('queryUser', loginData,'post',true).then(res => {
                let data = res.data;
                if (data.info.code === '0' && data.data.status === 'OK') {
                  this.$store.dispatch('saveToken', {token:data.data.token,userName:this.loginForm.userName,uId:data.data.uId});
                  this.$store.dispatch("setRoles", data.data.roles);
                  setTimeout(() => {
                    this.$router.push({
                      name: 'userList'
                    })
                  }, 1000)
                } else {
                  this.$message({
                    message: '用户名或密码错误',
                    type: 'warning'
                  })
                  this.loading = false;
                }
              })
              .catch(error => {
                this.loading = false;
                this.$notify({
                  // 这里采用element ui的一个错误显示效果模板
                  title: '登录提示',
                  message: error.message,
                  position: 'bottom-right',
                  type: 'error'
                })
              })
        } else {
          console.log('error submit!!')
          return false;
        }
      })
    }
  }
}
</script>
<style lang="less">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

@bg: #283443;
@light_gray: #fff;
@cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: @cursor)) {
  .login-container .el-input input {
    color: @cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0;
      -webkit-appearance: none;
      border-radius: 0;
      padding: 12px 5px 12px 15px;
      color: @light_gray;
      height: 47px;
      caret-color: @cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0 1000px @bg inset !important;
        -webkit-text-fill-color: @cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>
<style scoped lang="less">
@bg: #2d3a4b;
@dark_gray: #889aa4;
@light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: @bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 5px 5px 6px 15px;
    color: @dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: @light_gray;
      margin: 0 auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 16px;
    top: 7px;
    font-size: 16px;
    color: @dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .code {
    position: absolute;
    right: 0;
    font-size: 16px;
    color: @dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
     }
   }
  }

</style>