<template>
    <div class="account">
      <div
      class="content"
      style="overflow:hidden;"
    >
      <div class="risk">
        <p><span class="titleTheme">接口信息</span></p>
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
              <span class="leftSpan">接口访问地址：</span>
              <Input
                type="text"
                class="input"
                v-model="formDate.interFaceUrl"
              />
            </FormItem>
            <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">接口说明：</span>
              <Input
                type="text"
                class="input"
                v-model="formDate.interFaceRemrk"
              />
            </FormItem>
            <FormItem
              prop="agentId"
              class="formLine"
            >
              <span class="leftSpan">接口状态是否有效：</span>
              <Select
                :labelInValue="true"
                v-model="formDate.interFaceFlag"
                class="input"
              >
                <Option value="1">有效</Option>
                <Option value="0">无效</Option>
              </Select>
            </FormItem>
            <FormItem
              class="formLine"
              style="text-align:center"
            > 
            <a
              class="but mr10"
              @click="comit"
            >提交</a>
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
  created() {},
  mounted() {},
  data(){
      return{
        formDate:{
          systemId: "",
          interFaceUrl: "",
          interFaceRemrk:"",
          interFaceFlag:""
        },
        riskData:[],
        loading:false,
       
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
            .post("/logHom/api/adOrUpInterface",this.formDate
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
         path :"/interfaceManage"
      });
    }





  },

 
 

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

