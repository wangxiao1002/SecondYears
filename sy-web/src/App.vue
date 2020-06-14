<template>
  <div id="app">
    <!-- 路由出口 -->
    <!-- 路由匹配到的组件将渲染在这里 -->
    <transition name="fade">
      <router-view />
    </transition>
  </div>
</template>

<script>

export default {
  name: "app",
  components: {

  },
  data() {
    return {
     
    };
  },
  computed: {
    
  },
  created() {
    
  },
  methods: {
    
  },
  watch: {
   
  }
};
</script>

<style lang="scss">
#app {
  font-family: Arial, "Microsoft Yahei";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  //text-align: center;
  color: #2c3e50;
}
</style>
