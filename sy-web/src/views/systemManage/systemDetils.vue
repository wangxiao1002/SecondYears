<template>
    <div class="account">
      <div
      class="content"
      style="overflow:hidden;"
    >
      <div class="risk">
        <p><span class="titleTheme">接入系统信息</span></p>
        <div class="updateInfo">
          <Form
            ref="formInline"
            :model="formDate"
            inline
            :showMessage="false"
            class="form"
          >
          <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">系统名称：</span>
              <Input
                type="text"
                class="input"
                v-model="formDate.systemName"
              />
            </FormItem>
            
            <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">系统访问账号：</span>
              <Input
                type="text"
                class="input"
                v-model="formDate.systemNo"
              />
            </FormItem>
            <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">系统访问密码：</span>
              <Input
                type="text"
                class="input"
                v-model="formDate.systemPwd"
              />
               <a
              @click="getCode1"
            >随记30位密码</a>
            </FormItem>
            
            <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">是否是APP：</span>
              <Select
                :labelInValue="true"
                v-model="formDate.isApp"
                class="input"
              >
                <Option value="1">是</Option>
                <Option value="0">否</Option>
        
              </Select>
            </FormItem>
            <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">是否有手势密码：</span>
              <Select
                :labelInValue="true"
                v-model="formDate.hasSign"
                class="input"
              >
                <Option value="1">是</Option>
                <Option value="0">否</Option>
              </Select>
            </FormItem>
            <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">系统密钥：</span>
              <Input
                type="text"
                class="input"
                v-model="formDate.systemAesPwd"
              />
            <a
              @click="getCode2"
            >随机30位密钥</a>  
            </FormItem>
             <FormItem
              class="formLine"
              style="text-align:center"
            > 
            <a
              class="but mr10"
              v-if="isShowComit"
              @click="comit"
            >提交</a>
             <a
              class="but mr10"
              v-if="isShowEdit"
              @click="edit"
            >修改</a>
            <a
              class="but mr10"
              @click="revert"
            >返回</a>

            </FormItem>
          </Form>
        </div>
      </div>
      </div>
    </div>
</template>
<script>
export default {
  name: "systemDetils",
  components:{},
  data(){
      return{
        formDate:{
          systemId: "",
          systemName: "",
          systemNo:"",
          systemPwd:"",
          isApp:"2",
          systemAesPwd:"",
          hasSign:"2"
        },
        riskData:[],
        loading:false,
        url: "/logHom/api/getSystem",
        isShowComit: true,
        isShowEdit: false,

      }
  },
  
  methods:{
    comit(params){
      let that = this;
      that.$Modal.confirm({
        title: "信息",
        content: "确定提交吗？",
        onOk: () => {
          that.axios
            .post("/logHom/api/putSystem",this.formDate
            )
            .then(res => {
              if (res.data.status == "success") {
                that.$Message.success({
                  closable: true,
                  content: res.data.message,
                  onClose: () => {
                    this.revert();
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
    revert(){
        this.$router.push({
         path :"/systemManage"
      });
    },
    edit(params){
      let that = this;
      that.$Modal.confirm({
        title: "信息",
        content: "确定修改吗？",
        onOk: () => {
          that.axios
            .post("/logHom/api/putSystem",this.formDate
            )
            .then(res => {
              if (res.data.status == "success") {
                that.$Message.success({
                  closable: true,
                  content: res.data.message,
                  onClose: () => {
                    this.revert();
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
    initTable(){
      let that =this;
        this.axios
        .get(this.url, {params : {
           systemId :this.formDate.systemId
        }})
        .then(res => {
          that.loading = false;
          let data = res.data;
          if (data.status == "success") {
            this.formDate = data.result
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
      var systemId =this.$route.params.systemId;
      that.loading = true;
      if(systemId !="undefined" && systemId!=""){
        this.formDate.systemId =systemId;
        this.isShowComit= false;
        this.isShowEdit= true;
        this.initTable();
      }
      
    },
    getCode1(){
      let that =this;
        this.axios
        .get("/logHom/api/getRandomCode")
        .then(res => {
          that.loading = false;
          let data = res.data;
          if (data.status == "success") {
            this.formDate.systemPwd = data.result
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
     getCode2(){
      let that =this;
        this.axios
        .get("/logHom/api/getRandomCode")
        .then(res => {
          that.loading = false;
          let data = res.data;
          if (data.status == "success") {
            this.formDate.systemAesPwd = data.result
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

  },
  
  
  

  created() {
    this.$store.commit("focusId", { focusId: "L0601" });
    this.query();
  },
  mounted(formDate) {
    
    
  }
 

};
</script>
<style lang="scss" scoped>
.content {
  .risk {
    .updateInfo {
      width: 40%;
      margin: 0 auto;
    }
  }
}
</style>

