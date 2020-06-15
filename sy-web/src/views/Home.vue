<template>
  <div class="home">
    <Header :userInfo="userInfo"></Header>
    <div class="menu">
      <div class="menu-left">
        <Menu :menus="menus" :focusId="focusId"></Menu>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
import Header from "@/components/Header.vue";
import Menu from "@/components/Menu.vue";

export default {
  name: "Home",
  components: {
    Menu,
    Header
  },
  data() {
    return {
      menus: {}, //菜单
      userInfo: {} //用户
    };
  },
  computed: {
    focusId() {
      return this.$store.state.focusId;
    }
  },
  created() {
    //菜单，用户
    this.initInfo();
  },
  methods: {
    initInfo() {
      //菜单，用户
      let basicInfo = JSON.parse(localStorage.getItem("basicInfo"));
      if (!basicInfo) {
        return false;
      }
      this.menus = basicInfo.menu;
      this.userInfo = basicInfo.userInfo;
    }
  },
  watch: {
    focusId() {
      this.initInfo();
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.instruct {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}
</style>