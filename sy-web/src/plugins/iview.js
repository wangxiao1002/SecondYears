import Vue from 'vue'
import iview from 'iview'
/* --------author:xiaoling-------
对弹框统一处理为默认三秒自动关闭
 */
iview.Message.config({
    top: 200,
    duration: 3
  })
Vue.use(iview)

import 'iview/dist/styles/iview.css'
