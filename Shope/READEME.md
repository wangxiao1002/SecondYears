# Shope 商城
## 负责功能
1. 购买礼物，对接微信支付(done)和支付宝
2. 抽奖
## 抽奖功能开发
1. 抽奖奖品可配置
2. 抽奖概率可配置
3. 抽奖数字落在该奖品的权重之间（第一个是0~weight/sumWeight,第二个是前一个权重比例到第二个权重比例） 当然数据需要按照权重排序
## 接口限流
1. 结合redis 单位时间段内使用lua脚本
2. 使用命令桶 桶排序算法
## 接口幂等
1. 使用redis 实现分布式（setnx）
2. 本地使用cache(gugua 提供或者自定义缓存)
## spu和sku 
```mysql

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_shope_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_shope_sku`;
CREATE TABLE `t_shope_sku`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `spu_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spuId',
  `images` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `sale_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠价格',
  `indexes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特有规格属性在spu属性模板中的对应下标组合',
  `own_spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'sku的特有规格参数键值对',
  `enable` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有效，0无效，1有效',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_shope_spu
-- ----------------------------
DROP TABLE IF EXISTS `t_shope_spu`;
CREATE TABLE `t_shope_spu`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `spu_name_zh` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称中文',
  `spu_name_en` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称英文',
  `spu_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `category_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类ID',
  `state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0下架，1正常',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_shope_categoory
-- ----------------------------
DROP TABLE IF EXISTS `t_shope_categoory`;
CREATE TABLE `t_shope_categoory`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `parent_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父目类id',
  `sort` int(255) NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_shope_order
-- ----------------------------
DROP TABLE IF EXISTS `t_shope_order`;
CREATE TABLE `t_shope_order`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `pay_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际支付',
  `state` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `desc_no` int(11) NULL DEFAULT NULL COMMENT '预留号码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_shope_order_info
-- ----------------------------
DROP TABLE IF EXISTS `t_shope_order_info`;
CREATE TABLE `t_shope_order_info`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单id',
  `spu_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spuID',
  `sku_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'skuID',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `par_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际支付',
  `count` int(11) NULL DEFAULT NULL COMMENT '数量',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_shope_spec
-- ----------------------------
DROP TABLE IF EXISTS `t_shope_spec`;
CREATE TABLE `t_shope_spec`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `spec_group_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格组',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `enable` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否默认选中',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_shope_spec_group
-- ----------------------------
DROP TABLE IF EXISTS `t_shope_spec_group`;
CREATE TABLE `t_shope_spec_group`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `spu_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格组名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规格组' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


```
spu 是一个商品，举例小米10破，sku是具体细化规格商品，举例小米10破 64G 黑金色，假设规格有内存（64G，128G）、颜色(黑色，白色)就会有笛卡尔积的数量sku,(4)</br>
商品规格按照规格组来划分，比如有内存、颜色

## eventListener
1. 之前事件监听，需要定义事件ApplicationEventTest实体类继承ApplicationEvent,和监听器ApplicationListenerTest 实现 ApplicationListener<ApplicationEventTest> </br>
中的onApplicationEvent方法， 事件的发布和监听器发布都是使用application与applicationContext,例如
```java
    // 发布监听器
    application.addListeners(new ApplicationListenerTest());
    Set<ApplicationListener<?>> listeners = application.getListeners();
     ConfigurableApplicationContext context =  application.run(args);
    //发布事件
     context.publishEvent(new ApplicationEventTest(new Object()));

```
2.现在使用注解
```java
@Component
public class MessageListener {

    @EventListener
    public void processAccountCreatedEvent1(OrderEvent event) {
        // TODO
        System.out.println(event.getOrderId());
    }

   //写多个方法来监听不通事件和统一事件的不通处理（例如新建账单时候发送邮件和提醒）,使用@Order 和Ordered 接口来控制顺序

   // 事件 发布注入private ApplicationEventPublisher publisher; 调用方法
  @Autowired
  private ApplicationEventPublisher publisher;
  publisher.publishEvent(new OrderEvent(account))

```
