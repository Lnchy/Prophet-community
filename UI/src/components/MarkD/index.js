import MarkD from './MarkD.vue'

const markdown = {
    install: function (Vue) {
        Vue.component('MarkDown', MarkD)
    }
}

export default markdown;