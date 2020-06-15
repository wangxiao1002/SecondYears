import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        focusId: ""//聚焦菜单
    },
    mutations: {
        focusId(state, payload) {
            state.focusId = payload.focusId;
        }
    },
    actions: {

    }
})