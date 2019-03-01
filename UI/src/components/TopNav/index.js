import TopNav from './TopNav.vue'
// 这里是重点
const top = {
    install: function (Vue) {
        Vue.component('Top', TopNav)
    }
}

// 导出组件
export default top