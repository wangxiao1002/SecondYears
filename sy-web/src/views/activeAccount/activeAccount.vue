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
              v-model="RmsIndexDto.accountIsDel"
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
  name: "activeAccount",
  components: {
   
  },
  data() {
    return {
      RmsIndexDto: {
        agentId: "",
        accountId: "",
        accountName: "",
        accountStatus: "",
        accountIsDel: "",
        currentPage : 1,
        pageSize : 10
      },
      loading:false,
      total: 0,
      url: "/logHom/api/getNodelAccounts",  
      riskCols: [
        {
          title: "序号",
          type: "index",
          width: 50,
          align: "center"
        },
        {
          title: "账户编号",
          key: "accountId",
          minWidth: 70,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "账号",
          key: "accountName",
          minWidth: 200,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "注册时间",
          key: "accountCreatTime",
          minWidth: 100,
          align: "center"
        },
        {
          title: "最后一次更新时间",
          key: "accountUpdateTime",
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
          title: "类型",
          key: "accountType",
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
    this.$store.commit("focusId", { focusId: "L02" });
  },
  mounted() {
    
  },
  methods: {
    
    changePage(index) {
      this.loading = true;
      // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server
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
        agent_Id: "",
        account_Id: "",
        account_name: "",
        account_status: "",
        is_del: "",
        currentPage: 1,
        pageSize:10
      };
    },
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
</style>