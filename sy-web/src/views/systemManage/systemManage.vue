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
            <span class="leftSpan">接入系统名称：</span>
            <input
              type="text"
              class="input"
              v-model="paramDto.systemName"
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
            >新增接入系统</a>
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
    

     <Modal
      v-model="showDialog"
      :mask-closable="false"
      class-name="detailModal"
      scrollable
      width="500"
    >
      <p slot="header">
        <span style="color:#fff;">接口配置</span>
      </p>
      <div>
        <div class="">
          <Tree
            :data="interfaceList"
            ref="tree"
            @on-check-change="interfaceChecked"
            show-checkbox
            multiple
            empty-text
          ></Tree>
        </div>
      </div>
      <div slot="footer" 
        style="text-align:center;">
        <Button
          type="primary"
          size="large"
          @click="ok()"
        >确定</Button>
        <Button
          type="primary"
          size="large"
          @click="cancel()"
        >取消</Button>
      </div>
    </Modal>

    <div class="demo-spin-container"
        ref="Loadding"
      style="display:none">
        <Spin fix></Spin>
    </div>
  </div>
  
  
  
</template>

<script>
export default {
  name :"systemManage",
  components:{},
  data(){
    return {
      paramDto:{
        systemName:"",
        currentPage: 1,
        pageSize: 10
      },
      interfaceList: [],
      currentSelectRow:"",
      cuurentInterFaceIds: [],
      riskData: [],
      riskCols:[
           {
          title: "序号",
          type: "index",
          width: 50,
          align: "center"
        },
        {
          title: "接入系统名称",
          key: "systemName",
          minWidth: 70,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "接入系统编码",
          key: "systemCode",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "接入系统访问账号",
          key: "systemNo",
          minWidth: 100,
          align: "center"
        },
        {
          title: "是否是APP",
          key: "isAppMsg",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "接入状态",
          key: "systemFlagMsg",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
        {
          title: "是否拥有手势密码",
          key: "hasSignMsg",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
         {
          title: "接入创建时间",
          key: "createTime",
          minWidth: 100,
          ellipsis: true,
          tooltip: true,
          align: "center"
        },
          {
          title: "操作",
          key: "opreate",
          minWidth: 200,
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
                      this.view(params.row.systemId);
                    }
                  }
                },
                "查看详情"
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
                      this.alloInterf(params);
                    }
                  }
                },
                "配置接口"
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
                      this.notifySystemFlag(params);
                    }
                  }
                },
                params.row.systemFlag==1?"置为无效":"置为有效"
              )
            ]);
          }      
        },
      ],
      showDialog: false,
      loading:false,
      total: 0,
      url: "/logHom/api/getSystems/"

    }
  },
   created() {
    this.$store.commit("focusId", { focusId: "L06" });
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
    //初始化
    initTable(){
      let that = this;
        this.axios
        .get(this.url, {params : {
           systemName :this.paramDto.systemName,
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
         name :"systemDetils",
         params:{
           systemId: ""
         },
      });
    },
    //查看
    view(param){
      this.$router.push({
         name :"systemDetils",
         params:{
           systemId: param
         },
      });
    },
    notifySystemFlag(params){
      let that = this;
      that.$Modal.confirm({
        title: "信息",
        content: "确定提交吗？",
        onOk: () => {
          that.axios
            .get("/logHom/api/patchFlag",{
              params:{
                systemId: params.row.systemId,
                systemFlag: params.row.systemFlag
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
    },
    //配置接口信息
    alloInterf(params){
      let that = this;
      this.axios
        .get("/logHom/api/getSystemInteinfo", {
          params : {
             systemId: params.row.systemId,
             date: new Date().getTime()
        }})
        .then(res => {
          that.showDialog = true;
          that.loading = false;
          let data = res.data;
          if (data.status == "success") {
            that.currentSelectRow = params.row;
             that.interfaceList = data.result;
             that.interfaceList.map(
               function(items){
                 that.$set(items,"title", items.interFaceRemrk+"，   地址：【"+items.interFaceUrl+"】");
                 items.isChecked == "1"?that.$set(items, "checked", true):that.$set(items, "checked", false);
               }
             )} else {
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
    //选中事件
    interfaceChecked(values){
      let that = this,
      interFaceIds = [];
      values.map(item => {
        interFaceIds.push(item.interFaceId);
      });
      that.cuurentInterFaceIds = interFaceIds;
    },
    //提交配置信息
    ok(){
      let that = this;
      this.showDialog = false;
      that.$refs.Loadding.style="display:inline"; 
      var interFaceIds = that.cuurentInterFaceIds.join(",");
      that.axios
        .post("/logHom/api/alloInteFace?"+"interFaceIds="+interFaceIds+"&systemId="+that.currentSelectRow.systemId)
        .then(res => {
          let data = res.data;
          that.$refs.Loadding.style="display:none";
          if (data.status == "success") {
            that.showDialog = false;
            that.$Message.success({
              title: "信息",
              content: res.data.message,
              onClose: () => {
                that.initTable();
              }
            });
          } else {
            that.$Message.error({
              title: "信息",
              content: data.message
            });
          }
        })
        .catch(error => {
          that.$refs.Loadding.style="display:none";
          that.$Message.error({
            title: "信息",
            content: error
          });
        });
        
    },
    //取消
    cancel(){
      this.showDialog = false;
    }

  }
 

};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
</style>


