import Vue from 'vue';
import App from './App.vue';

import ElementUi from 'element-ui';
import  Print from  'vue-print-nb';
import 'element-ui/lib/theme-chalk/index.css'
//引入路由器
import router from './router';
import store from "./store/index";



//关闭Vue的生产提示
Vue.config.productionTip = false


Vue.use(ElementUi,{size:'small'});
Vue.use(Print);

new Vue({

  render: h => h(App),
  store,
  router,
}).$mount('#app')
