"use strict";

import Vue from 'vue';
import iView from 'iview'
import axios from "axios";
import router from '../router'

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
  function (response) {
    // Do something with response dataq
    /* 
    --------author:xiaoling-------
    请求统一处理，即session失效跳转到登录页
     */
    if (response.data.status == 'logout') {
      iView.Message.error({
        title: "错误",
        content: response.data.message,
        onClose() {
          localStorage.clear();
          router.replace({
            name: "Login"
          });
        }
      });
    }
    return response;
  },
  function (error) {
    // Do something with response error
    return Promise.reject(error);
  }
);
const Plugin = {}

Plugin.install = function (Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;