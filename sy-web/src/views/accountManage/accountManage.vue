<template>
  <div class="account">
    <div
      class="content"
      style="overflow:hidden;"
    >
      <div class="risk">
        <p><span class="titleTheme">数据查询</span></p>
        <div class="formLine">
          <div class="formInput">
            <span class="leftSpan">客户编号：</span>
            <input
              type="text"
              class="input"
              v-model="RmsIndexDto.agentId"
            />
          </div>
          <div class="formInput">
            <span class="leftSpan">账户编号：</span>
            <input
              type="text"
              class="input"
              v-model="RmsIndexDto.accountId"
            />
          </div>
          <div class="formInput">
            <span class="leftSpan">账号：</span>
            <input
              type="text"
              class="input"
              v-model="RmsIndexDto.accountName"
            />
          </div>
        </div>
        <div class="formLine">
          <div class="formInput">
            <span class="leftSpan">是否冻结：</span>
            <select
              class="input"
              v-model="RmsIndexDto.accountStatus"
            >
              <option value="">全部</option>
              <option value="0">否</option>
              <option value="2">是</option>
            </select>
          </div>
          <div class="formInput">
            <span class="leftSpan">是否注销：</span>
            <select
              class="input"
              v-model="RmsIndexDto.aggentIsDel"
            >
              <option value="">全部</option>
              <option value="0">否</option>
              <option value="1">是</option>
            </select>
          </div>
        <div class="formInput txtCenter">
          <a
            class="but mr10"
            @click="query"
          >查询</a>
          <a
            class="but mr10"
            @click="reset"
          >重置</a>
        </div>
        </div>
      </div>
      <div class="risk">
        <p><span class="titleTheme">数据列表</span></p>
        
            <Table
              :data="riskData"
              :columns="riskCols"
              stripe
              border
              :loading="loading"
            ></Table>
            <div style="margin: 10px;overflow: hidden">
              <div style="float: right;">
                <Page
                  :total="total"
                  :current="1"
                  @on-change="changePage"
                  @on-page-size-change="changPageSize"
                  :page-size-opts="[10, 20, 30, 40]"
                  :show-sizer="true"
                  :show-total="true"
                ></Page>
              </div>
            </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "GroupIndex",
  components: {
   
  },
  data() {
    return {
      RmsIndexDto: {
        agentId: "",
        accountId: "",
        accountName: "",
        accountStatus: "",
        aggentIsDel: "",
        currentPage: 1,
        pageSize: 10
      },
      loading:false,
      total: 0,
      url: "/logHom/api/getInfos/", 
      riskCols: [
        {
          title: "序号",
          type: "index",
          width: 50,
          align: "center"
        },

        {
          title: "客户编号",
          key: "agentId",
          minWidth: 70,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "客户姓名",
          key: "agentName",
          minWidth: 200,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "证件类型",
          key: "agentCharacterTypeMsg",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "证件号码",
          key: "agentCharacterNum",
          minWidth: 85,
          align: "center"
        },
        {
          title: "性别",
          key: "agentGenderMsg",
          minWidth: 85,
          align: "center"
        },
        {
          title: "注册时间",
          key: "agentCreatTime",
          minWidth: 100,
          align: "center"
        },
        {
          title: "最后一次更新时间",
          key: "agentUpdateTime",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "一级机构",
          key: "agentInsType",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "注册平台",
          key: "agentPlatform",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "注册场景",
          key: "agentRegistDes",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
           {
          title: "账户状态",
          key: "accountStatusMsg",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
           {
          title: "账户有效性",
          key: "accountIsDelMsg",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        }
      ],
      riskData: []
    };
  },
  created() {
    this.$store.commit("focusId", { focusId: "L01" });
  },
  mounted() {
  },
  methods: {
    
    changePage(index) {
      this.loading = true;
      //index显示的页码
      this.RmsIndexDto.currentPage = index;
      this.initTable();
    },
    changPageSize(pageSize) {
      this.loading = true;
      this.RmsIndexDto.pageSize = pageSize;
      this.RmsIndexDto.currentPage = 1;
      this.initTable();
    },
    initTable() {
      let that = this;
      this.axios
        .post(this.url, this.RmsIndexDto)
        .then(res => {
          that.loading = false;
          let data = res.data;
          if (data.status == "success") {
              this.riskData = data.result.list;
              this.total = data.result.total; //总条数
          } else {
            that.$Message.error({
              title: "信息",
              content: data.message
            });
          }
        })
        .catch(error => {
          //console.log(error);
          that.loading = false;
          that.$Message.error({
            title: "信息",
            content: error
          });
        });
    },
    //查询
    query() {
      let that = this;
      that.loading = true;
      //tabs页，页数
      this.RmsIndexDto.currentPage = 1;
      this.initTable();
    },
    //重置
    reset() {
      this.RmsIndexDto = {
        agentId: "",
        accountId: "",
        accountName: "",
        accountStatus: "",
        aggentIsDel: "",
        currentPage: 1,
        pageSize: 10
      };
    },
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
</style>