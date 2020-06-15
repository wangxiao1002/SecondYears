<template>
  <div class="account">
     <div
      class="content"
      style="overflow:hidden;"
    >
    <div class="risk">
         <div class="risk">
        <p><span class="titleTheme">数据查询</span></p>
        <div class="formLine">
          <div class="formInput">
            <span class="leftSpan">接口说明：</span>
            <input
              type="text"
              class="input"
              v-model="paramDto.interFaceRemrk"
            />
          </div>
           <div class="formInput txtCenter">
            <a
              class="but mr10"
              @click="query"
            >查询</a>
            <a
              class="but mr10"
              @click="add"
            >新增接口信息</a>
          </div> 
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
  name :"interfaceManage",
  components:{},
  data(){
    return {
      paramDto:{
        interFaceRemrk:"",
        currentPage: 1,
        pageSize: 10
      },
      riskData: [],
      riskCols:[
           {
          title: "序号",
          type: "index",
          width: 50,
          align: "center"
        },
        {
          title: "接口访问地址",
          key: "interFaceUrl",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "接口说明",
          key: "interFaceRemrk",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "接口状态",
          key: "interFaceFlagMsg",
          minWidth: 100,
          align: "center"
        },
        {
          title: "接口创建时间",
          key: "interFaceCreateTimeStr",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
          {
          title: "操作",
          key: "opreate",
          minWidth: 150,
          ellipsis: true,
          tooltip: true,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                  marginRight: "10px"
                },
                  on: {
                    click: () => {
                      this.edit(params);
                    }
                  }
                },
                "编辑"
              ),
               h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                  marginRight: "10px"
                },
                  on: {
                    click: () => {
                      this.notifyInterfaceFlag(params);
                    }
                  }
                },
                params.row.interFaceFlag==1?"置为无效":"置为有效"
              )
            ]);
          }      
        },
      ],
      loading:false,
      total: 0,
      url: "/logHom/api/getInterfaces"

    }
  },
   created() {
     this.$store.commit("focusId", { focusId: "L07" });
  },
  mounted() {
  },
  methods:{
      changePage(index) {
      this.loading = true;
      this.paramDto.currentPage = index;
      this.initTable();
    },
    changPageSize(pageSize) {
      this.loading = true;
      this.paramDto.pageSize = pageSize;
      this.paramDto.currentPage = 1;
      this.initTable();
    },
    initTable(){
      let that = this;
        this.axios
        .get(this.url, {params : {
           interFaceRemrk :this.paramDto.interFaceRemrk,
           currentPage: this.paramDto.currentPage,
           pageSize: this.paramDto.pageSize
        }})
        .then(res => {
          that.loading = false;
          let data = res.data;
          if (data.status == "success") {
            this.riskData = data.result.list;
            this.total = data.result.total; 
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
    query(){
      let that = this;
      that.loading = true;
      //tabs页，页数
      this.paramDto.currentPage = 1;
      this.initTable();
    },
    //新增接入系统
    add(param){
      this.$router.push({
         path :"/interfaceDetils"
      });
    },
    //编辑
    edit(){
       let that = this;
        that.$Modal.confirm({
        title: "信息",
        content: "暂不支持修改,请置为无效",
        onOk: () => {},
        onCancel: () => {}
      });   
    },
    notifyInterfaceFlag(params){
        let that = this;
        that.$Modal.confirm({
        title: "信息",
        content: "确定提交吗？",
        onOk: () => {
          that.axios
            .get("/logHom/api/patchInterFlag",{
              params:{
                interFaceId: params.row.interFaceId,
                interFaceFlag: params.row.interFaceFlag
              }
            })
            .then(res => {
              if (res.data.status == "success") {
                that.$Message.success({
                  closable: true,
                  content: res.data.message,
                  onClose: () => {
                    this.query();
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


