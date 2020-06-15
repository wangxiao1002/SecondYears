<template>
  <ul :class="className?className:'list-first'">
    <li
      v-for="(menu,index) in menus"
      :key="index"
    >
      <div
        class="img"
        v-if="!menu.open&&menu.iconLS"
      >
        <img :src="menu.iconLS" />
      </div>
      <div
        class="img"
        v-if="menu.open&&menu.iconLH"
      >
        <img :src="menu.iconLH" />
      </div>
      <div
        class="imgIconP"
        v-if="menu.icon"
      >
        <Icon
          :type="menu.open?'md-remove':menu.icon"
          size="16"
          color="#fff"
        />
      </div>
      <div
        class="imgIcon"
        v-if="menu.pid&&!menu.isThree"
      >
        <span
          class="imgSpan"
          :style="{'background-color': menu.id==focusId?'#f4781f':'#fff'}"
        ></span>
      </div>
      <router-link
        :to="menu.url"
        v-if="menu.url?true:false"
        :id="menu.id"
        :activeClass="menu.id==focusId?'isActive':''"
        :class="menu.id==focusId?'isActive':''"
      >{{menu.name}}</router-link>
      <a
        v-if="menu.url?false:true"
        :id="menu.id"
        @click="toggle(menu)"
      >{{menu.name}}</a>
      <Menu
        :menus="menu.nodes"
        :focusId="focusId"
        v-if="menu.open"
        :className="(menu.isThree?'list-third':'list-second')"
      ></Menu>
    </li>
  </ul>
</template>
<script>
/*-----------
--------author:xiaoling-------
菜单menus的数据结构：
    "id": 菜单的id,
    "pid": 父菜单的id,
    "name": 菜单显示名称,
    "url":跳转的路由,根据URL是否存在，展示是否带路由菜单,
    "open":是否展示子菜单，true展开，false隐藏,
    "nodes":子菜单，对象数组[{},{},...,{}],
    "isThree":是否有三级菜单，一级菜单ul的class=list-first,二级菜单是list-second,三级菜单是list-third，用于样式调整,
    "focusId":聚焦的菜单id集"L060101,
通过控制open对子菜单做展开和收起操作,以及控制图标的展示内容。
------------*/
export default {
  name: "Menu",
  props: ["menus", "className", "focusId"],
  data() {
    return {};
  },
  created() {
    this.initMenu();
  },
  watch: {
    focusId() {
      this.initMenu();
    }
  },
  mounted() {},
  methods: {
    //左侧菜单数据处理
    initMenu() {
      let that = this;
      if (this.focusId) {
        //获取菜单的父节点集合
        let focus = [];
        if (this.focusId.length == 3) {
          focus.push(this.focusId);
        }
        for (let i = 0; i < (this.focusId.length - 3) / 2; i++) {
          focus.push(this.focusId.slice(0, this.focusId.length - 2 * (i + 1)));
        }
        that.menus.map(function(item) {
          for (let i = 0; i < focus.length; i++) {
            if (focus[i] == item.id) {
              that.$set(item, "open", true);
            }
          }
        });
      }
    },
    //收起/展开
    toggle(menu) {
      menu.open = !menu.open;
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
@mixin ul_li {
  position: relative;
  padding: 13px 0;
  text-align: left;
  padding-left: 20px;
  list-style: none;
  a {
    font-size: 14px;
    color: #f6f6f6;
    line-height: 14px;
    display: block;
    position: relative;
  }
  a.isActive {
    color: #f4781f;
  }
  .img {
    width: 18px;
    float: left;
    margin-right: 15px;
    margin-top: -3px;
    img {
      width: 20px;
    }
  }
  .imgIcon {
    width: 4px;
    margin-right: 10px;
    float: left;
    margin-top: 5px;
    height: 4px;
    .imgSpan {
      width: 4px;
      height: 4px;
      color: #fff;
      background-color: #fff;
      display: block;
    }
  }
  .imgIconP {
    float: left;
    margin-top: -3px;
    margin-left: -5px;
    margin-right: 3px;
  }
}

@each $list in first, second, third {
  .list-#{$list} {
    li {
      @include ul_li @if $list==first {
        border-bottom: 1px solid #4e515b;
      } @else {
        border-bottom: none;
      }
    }
  }
}
</style>
