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
      <div class="risk" v-if="isShow">
        <p><span class="titleTheme">详细信息</span></p>
        <div class="updateInfo">
          <Form
            ref="formInline"
            :model="formValidate"
            :rules="ruleValidate"
            inline
            :showMessage="false"
            class="form"
          >
            <FormItem
              prop="accountId"
              class="formLine"
            >
              <span class="leftSpan">客户编号：</span>
              <Input
                type="text"
                class="input"
                v-model="formValidate.agentId"
                disabled
              />
            </FormItem>
            <FormItem
              prop="accountName"
              class="formLine"
            >
              <span class="leftSpan">姓名：</span>
              <Input
                type="text"
                class="input"
                v-model="formValidate.agentName"
              />
            </FormItem>
            <FormItem
              prop="cardId"
              class="formLine"
            >
              <span class="leftSpan">证件类型：</span>
              <Select
                :labelInValue="true"
                v-model="formValidate.agentCharacterType"
                class="input"
                @on-change="cardChange"
              >
                <Option value="1">身份证</Option>
                <Option value="3">护照</Option>
                <Option value="4">军官证</Option>
                <Option value="5">港澳台同胞证</Option>
                <Option value="6">出生证明</Option>
                <Option value="7">户口薄</Option>
                <Option value="11">户口本</Option>
                <Option value="18">台胞证</Option>
                <Option value="19">港澳通行证</Option>
                <Option value="30">外国人永久居留证</Option>
                <Option value="56">港澳台居民居住证</Option>
              </Select>
            </FormItem>
            <FormItem
              prop="cardNo"
              class="formLine"
            >
              <span class="leftSpan">证件号码：</span>
              <Input
                type="text"
                class="input"
                v-model="formValidate.agentCharacterNum"
                @on-blur="valid"
              />
            </FormItem>
            <FormItem
              prop="userDate"
              class="formLine"
            >
              <span class="leftSpan">生日：</span>
              <DatePicker
                type="date"
                @on-change="dateChange"
                v-model="formValidate.agentBirthday"
                class="input"
              >
              </DatePicker>
            </FormItem>
            <FormItem
              prop="sex"
              class="formLine"
            >
              <span class="leftSpan">性别：</span>
              <Select
                :labelInValue="true"
                v-model="formValidate.agentGender"
                class="input"
              >
                <Option value="1">男</Option>
                <Option value="2">女</Option>
                
              </Select>
            </FormItem>
            <FormItem
              class="formLine"
              style="text-align:center"
            >
              <Button
                type="primary"
                @click="save('formInline')"
              >修改</Button>
            </FormItem>
            <FormItem
              class="formLine"
              style="text-align:center,display:none"
            >
            </FormItem>
             <FormItem
              class="formLine"
              style="text-align:center,display:none"
            >
            </FormItem>
             <FormItem
              class="formLine"
              style="text-align:center,display:none"
            >
            </FormItem>
          </Form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "updateInfo",
  components: {},
  data() {
    return {
      cardValid: true,
      RmsIndexDto: {
        accountName: ""
      },
      formValidate: {
        agentId: "会员编码不能修改",
        agentName: "",
        agentCharacterType: "",
        agentCharacterNum: "",
        agentBirthday: "",
        agentGender: ""
      },
      isShow:false,
      ruleValidate: {
        agentName: [{ required: true, pattern: /.+/, trigger: "blur" }],
        agentCharacterType: [{ required: true, pattern: /.+/, trigger: "blur" }],
        agentCharacterNum: [{ required: true, pattern: /.+/, trigger: "blur" }],
        agentBirthday: [{ required: true, pattern: /.+/, trigger: "blur" }]
      },
      url : "/logHom/api/getAgentInfo"
    };
  },
  created() {
    this.$store.commit("focusId", { focusId: "L03" });
  },
  mounted() {},
  methods: {
    //查询
    query() {
      let that = this;
      this.axios
        .get(this.url, {
          params :{
          accountName :this.RmsIndexDto.accountName
        }
        })
        .then(res => {
          let data = res.data;
          if (data.status == "success") {
            this.isShow = true;
            this.formValidate = data.result;
          } else {
            that.$Message.error({
              title: "信息",
              content: data.message
            });
          }
        })
        .catch(error => {
          that.$Message.error({
            title: "信息",
            content: error
          });
        });
    },
    //重置
    reset() {
      this.RmsIndexDto = {
        accountName: ""
      };
      this.formValidate = {
        agentId: "账户不能为空",
        agentName: "",
        agentCharacterType: "",
        agentCharacterNum: "",
        agentBirthday: "",
        agentGender: ""
      };
    },
    dateChange(value) {
      this.formValidate.agentBirthday = value;
    },
    validCard() {
      //根据证件类型校验证件号码
      // 1：身份证3：护照4：军官证5：港澳台胞证10：出生证
      let that=this,cardType = {
        "1": function() {
          // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
          return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        },
        "3": function() {
          // 护照
          // 规则： 14/15开头 + 7位数字, G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
          // 样本： 141234567, G12345678, P1234567
          return /^([a-zA-z]|[0-9]){5,17}$/;
        },
        "4": function() {
          // 军官证
          // 规则： 军/兵/士/文/职/广/（其他中文） + "字第" + 4到8位字母或数字 + "号"
          // 样本： 军字第2001988号, 士字第P011816X号
          return /^[\u4E00-\u9FA5](字第)([0-9a-zA-Z]{4,8})(号?)$/;
        },
        "5": function() {
          // 港澳居民来往内地通行证
          // 规则： H/M + 10位或6位数字
          // 样本： H1234567890
          // 台湾居民来往大陆通行证
          // 规则： 新版8位或18位数字， 旧版10位数字 + 英文字母
          // 样本： 12345678 或 1234567890B
          return /^([A-Z]\d{6,10}(\(\w{1}\))?)|(\d{8}|^[a-zA-Z0-9]{10}|^\d{18})$/;
        },
        "10": function() {
          //出生证明：开头的字母是印刷的批次，后面九位阿拉伯数字
          return /^[A-Z]{1}\D{9}$/;
        }
      };
      let reg = cardType[this.formValidate.agentCharacterType]();
      if (reg.test(this.formValidate.agentCharacterNum)) {
        that.cardValid = true;
      } else {
        that.cardValid = false;
      }
    },
    cardChange() {
      //检验证件号码是否正确
      this.validCard();
    },
    valid() {
      //校验证件号码是否正确
      this.validCard();
    },
    save(name) {
      let that = this;
      this.$refs[name].validate(valid => {
        if (valid && that.cardValid) {
           that.$Modal.confirm({
             title: "信息",
           content: "确定修改吗？",
           onOk: () =>{
             that.axios
            .post("/logHom/api/updateAgentInfo", this.formValidate)
            .then(res => {
              if (res.data.status == "success") {
                that.$Message.success({
                  title: "信息",
                  content: res.data.message
                });
                that.query();
              } else {
                that.$Message.error({
                  title: "信息",
                  content: res.data.message
                });
              }
            })
            .catch(error => {
              that.$Message.error({
                title: "信息",
                content: error
              });
            });
           },
          onCancel: () => {},
           });
           
        } else {
          that.$Message.error({
            title: "信息",
            content: "请补充信息或证件类型与证件号码不一致"
          });
        }
      });
    }
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
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