import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/iview.js'

Vue.config.productionTip = false
//跨域
axios.defaults.withCredentials = true;
//设置服务器地址
Vue.prototype.localhost = 'http://localhost:8090';

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
