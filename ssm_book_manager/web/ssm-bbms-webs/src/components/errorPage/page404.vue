<template>
  <el-container>
    <el-dialog :visible.sync="isShow" center @close ="closeDialog">
      <el-card >
          这个星球找不到！<span style="color: red;font-size: 25px" >{{count}}</span> 秒后返回首页
      </el-card>
    </el-dialog>
  </el-container>
</template>

<script>
const  TIME_COUNT = 5;
export default {
  name: "page404",
  data(){
    return{
       timer:null,
       count:TIME_COUNT,
       isShow:true
    }
  },
  methods:{
     turnBackLogin(){
       if (!this.timer) {
         this.timer = setInterval(() => {
           if (this.count > 0 && this.count <= TIME_COUNT) {
             this.count--;
           }
           else {
                 clearInterval(this.timer);
                 this.timer = null;
                 this.$router.replace({name:"userList"});
           }
         },1000)
       }
     },
    closeDialog(){
      this.isShow = false;
      this.$router.replace({name:"userList"});
  },
  },
  created() {
    this.turnBackLogin();
  }

}
</script>

<style scoped>

</style>