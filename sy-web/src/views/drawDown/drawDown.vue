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
            <span class="leftSpan">账户：</span>
            <input
              type="text"
              class="input"
              v-model="RmsIndexDto.accountName"
            />
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
  name: "drawDown",
  components: {},
  data() {
    return {
      RmsIndexDto: {
        accountName: "",
        currentPage: 1,
        pageSize: 10
      },
      loading: false,
      total: 0,
      url: "/logHom/api/getAccountInfo", 
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
          title: "账户编号",
          key: "accountId",
          minWidth: 200,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "账户",
          key: "accountName",
          minWidth: 100,
          align: "center"
        },
        
        {
          title: "操作",
          key: "",
          align: "center",
          width: 80,
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  on: {
                    click: () => {
                      this.edit(params);
                    }
                  }
                },
                "注销"
              )
            ]);
          }
        }
      ],
      riskData: []
    };
  },
  created() {
    this.$store.commit("focusId", { focusId: "L04" });
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
        .get(this.url,{params :{
          accountName :this.RmsIndexDto.accountName
        }})
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
        currentPage: 1,
        pageSize: 10
      };
    },
    //注销
    edit(params) {
      let that = this;
      that.$Modal.confirm({
        title: "信息",
        content: "确定注销吗？",
        onOk: () => {
          that.axios
            .post("/logHom/api/cancelAgent" ,{
                  agentId : params.row.agentId,
                  accountId :params.row.accountId,
                  type :params.row.type
            } )
            .then(res => {
              if (res.data.status == "success") {
                that.$Message.success({
                  closable: true,
                  content: res.data.message,
                  onClose() {
                    that.initTable();
                  }
                });
              } else {
                that.$Message.error({
                  closable: true,
                  content: res.data.message,
                  onClose() {}
                });
              }
            })
            .catch(error => {
              that.$Message.error({
                closable: true,
                content: error,
                onClose() {}
              });
            });
        },
        onCancel: () => {}
      });
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
</style>