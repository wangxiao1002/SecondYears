import 'babel-polyfill'
import Vue from 'vue'
import './plugins/iview'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import Es6Promise from 'es6-promise'
import Element from 'element-ui'
import draggable from 'vuedraggable'
import "./assets/common.scss"

require('es6-promise').polyfill()
Es6Promise.polyfill()
Vue.config.productionTip = false
Vue.use(Element)
Vue.use(draggable)
//路由切换，返回顶端
router.afterEach((to, from, next) => {
      window.scrollTo(0,0)
})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
