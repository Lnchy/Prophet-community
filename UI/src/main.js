import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/iview.js'
//全局顶部导航
import Top from "./components/TopNav";
//问题组件
import Issue from "./components/Issue";
//markdonw组件
import MarkDown from "./components/MarkD";
//markdown编写组件
import Meditor from "./components/Meditor";
//答案组件
import Reply from "./components/Reply";


Vue.use(Top);
Vue.use(Issue);
Vue.use(MarkDown);
Vue.use(Meditor);
Vue.use(Reply);

Vue.config.productionTip = false
//跨域
axios.defaults.withCredentials = true;
//设置服务器地址
Vue.prototype.localhost = 'http://localhost:8090';


new Vue({
	router,
	render: h => h(App)
}).$mount('#app')
