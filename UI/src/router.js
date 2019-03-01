import Vue from 'vue'
import Router from 'vue-router'

import notFont from './views/notfont.vue' //404页面
import Index from './views/Index.vue' //主页
import about from './views/About.vue' //关于我
import Login from './views/Login.vue' //登录
import Reg from './views/Reg.vue'         //注册
import People from './views/People.vue' //个人主页
import Admin from './views/admin/Admin.vue' //个人主页
import Topic from './views/Topic.vue' //话题
import issue from './views/issueInfo.vue' //问题详情

Vue.use(Router)

const router = new Router({
    mode: 'history',    //hash
	routes: [
		{ path: '/', name: '首页', meta: { title: "先知社区" }, component: Index, },
		{ path: '/login', name: 'login', meta: { title: "登录先知" }, component: Login },
		{ path: '/about', name: 'about', meta: { title: "关于我" }, component: about },
		{ path: '/reg', name: 'reg', meta: { title: "注册先知" }, component: Reg },
		{ path: '/people/:id/activities', name: 'people', meta: { title: "个人中心" }, component: People },
		{ path: '/admin/', name: 'admin', meta: { title: "后台" }, component: Admin },
		{ path: '/topic', name: 'topic', meta: { title: "话题广场" }, component: Topic },
		{
			path: '/question/:id',
			name: 'issue',
			meta: { title: "问题详情" },
			component: issue,
			children: [
				{
					path: '/question/:id-:pageCode',
					name: 'issuePage',
					component: issue
				}
			]
        },

        { path: '*', meta: { title: "找不到页面" }, component: notFont }
    ]
})

router.beforeEach((to, from, next) => {//beforeEach是router的钩子函数，在进入路由前执行
    if (to.meta.title) {//判断是否有标题
        document.title = to.meta.title
    }
    next()//执行进入路由，如果不写就不会进入目标页
})

export default router