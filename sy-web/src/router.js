import Vue from 'vue'
import Router from 'vue-router'
import Login from'@/views/Login.vue'
import Home from '@/views/Home.vue'
import MyHome from '@/views/MyHome.vue'

Vue.use(Router)
let commonRouter = [{
        path: '',
        name: 'MyHome',
        component: MyHome
    }, { //账户信息查询
        path: '/accountManage',
        name: 'accountManage',
        component: () =>
            import( /*webpackChunkName: "index" */ '@/views/accountManage/accountManage.vue')
    }, { //有效账户列表
        path: '/activeAccount',
        name: 'activeAccount',
        component: () =>
            import( /*webpackChunkName: "index" */ '@/views/activeAccount/activeAccount.vue')
    }, { //修改客户的五要素
        path: '/updateInfo',
        name: 'updateInfo',
        component: () =>
            import( /*webpackChunkName: "index" */ '@/views/updateInfo/updateInfo.vue')
    }, { //账户注销
        path: '/drawDown',
        name: 'drawDown',
        component: () =>
            import( /*webpackChunkName: "index" */ '@/views/drawDown/drawDown.vue')
    }, { //账户冻结，解冻
        path: '/freezeOrDefrost',
        name: 'freezeOrDefrost',
        component: () =>
            import( /*webpackChunkName: "index" */ '@/views/freezeOrDefrost/freezeOrDefrost.vue')
    }, {//系统管理 
        path: '/systemManage',
        name: 'systemManage',
        component: () =>
            import(/*webpackChunkName: "index" */ '@/views/systemManage/systemManage.vue')
    },{
        //系统信息
        path: '/systemDetils',
        name: 'systemDetils',
        component: () =>
            import(/*webpackChunkName: "index" */ '@/views/systemManage/systemDetils.vue')
    },{
        //接口管理
        path: '/interfaceManage',
        name: 'interfaceManage',
        component: () =>
            import(/*webpackChunkName: "index" */ '@/views/interfaceManage/interfaceManage.vue')
    },{ 
        //接口信息
        path: '/interfaceDetils',
        name: 'interfaceDetils',
        component: () =>
            import(/*webpackChunkName: "index" */ '@/views/interfaceManage/interfaceDetils.vue')
    }
];
export default new Router({
    mode: 'history',
    routes: [{
            path: '/',
            name: 'Login',
            component: Login
        }, {
            path: '/home',
            name: 'Home',
            component: Home,
            // beforeEnter: (to, from, next) => {
            //     let basicInfo = JSON.parse(localStorage.getItem("basicInfo"));
            //     if (basicInfo) {
            //         next();
            //     } else {
            //         next("/");
            //     }
            // },
            children: commonRouter
        }
    ]
})