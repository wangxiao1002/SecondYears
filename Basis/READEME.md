# 基础类
**主要解决以下问题:**
1. 共用实体类不用重复创建
2. 共用方法不能项目之间共享
3. 提供基础功能，自定义缓存、日志收集等
4. 分布式id 生成，
## 自定义本地缓存模块
1. CacheEntity 缓存实体类
2. CacheManager 缓存管理器，用来存放缓存,启动清理线程
3. CacheUtil 提供缓存工具类
## 日志收集
使用阻塞队列来收集日志，线程池来发送日志 遗留问题(此包中不应该用多线程触发，只应该提供示例 )</br>
具体实现如下：
```
    //  继承 LogSendService 实现自己发送方法，再利用线程池触发，但只是示例
    static {
        final ExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.execute(new LogThread(new LogSendService()));
        executorService.execute(new LogThread(new LogSendService()));
        executorService.execute(new LogThread(new LogSendService()));
    }
```
## 分布式ID生成
* snowflake 算法
* 使用机器号和进程号做 workeid、dataCennterId 保证不同机器上不重复
**重要类有:**
1. LogEntity 日志实体类
2. LogUtil 日志工具，提供添加日志
3. LogThread 日志线程
4. LogManager 日志管理日 包含阻塞队列操作
5. LogSendService 日志发送service 具体业务需要自己实现，并通过LogThread构造参数传递进入
## 遗留问题 
1. 使用okhttp 异步发送时候不能使用微服务名
## 电子印章工具类
* 突然想起之前的代码有电子印章功能 所以百度一下 来源地址：https://github.com/localhost02/SealUtil
* 生成电子印章
```java
  /**
         * 印章配置文件
         */
        SealConfiguration configuration = new SealConfiguration();

        /**
         * 主文字
         */
        SealFont mainFont = new SealFont();
        mainFont.setBold(true);
        mainFont.setFontFamily("楷体");
        mainFont.setMarginSize(10);
        /**************************************************/
//        mainFont.setFontText("欢乐无敌制图网淘宝店专用章");
//        mainFont.setFontSize(35);
//        mainFont.setFontSpace(35.0);
        /**************************************************/
        //mainFont.setFontText("ZHITUWANG CO.LTDECIDDO SH  NANNINGSHI");
        //mainFont.setFontSize(20);
        //mainFont.setFontSpace(15.0);
        /**************************************************/
        mainFont.setFontText("北京思维魔法教育科技有限责任公司");
        mainFont.setFontSize(26);
        mainFont.setFontSpace(25.5);

        /**
         * 副文字
         */
        SealFont viceFont = new SealFont();
        viceFont.setBold(true);
        viceFont.setFontFamily("宋体");
        viceFont.setMarginSize(5);
        /**************************************************/
        //viceFont.setFontText("123456789012345");
        //viceFont.setFontSize(13);
        //viceFont.setFontSpace(12.0);
        /**************************************************/
        viceFont.setFontText("财务专用章");
        viceFont.setFontSize(25);
        viceFont.setFontSpace(25.5);

        /**
         * 中心文字
         */
        SealFont centerFont = new SealFont();
        centerFont.setBold(true);
        centerFont.setFontFamily("宋体");
        /**************************************************/
        centerFont.setFontText("★");
        centerFont.setFontSize(100);
        /**************************************************/
//        centerFont.setFontText("财务专用章\n★");
//        centerFont.setFontSize(20);
        /**************************************************/
        //centerFont.setFontText("123456789012345");
        //centerFont.setFontSize(20);
        /**************************************************/
//        centerFont.setFontText("发货专用");
//        centerFont.setFontSize(25);
        centerFont.setFontSpace(20.0);





        /**
         * 添加主文字
         */
        configuration.setMainFont(mainFont);
        /**
         * 添加副文字
         */
        configuration.setViceFont(viceFont);
        /**
         * 添加中心文字
         */
        configuration.setCenterFont(centerFont);
        /**
         * 添加抬头文字
         */
      //  configuration.setTitleFont(titleFont);

        /**
         * 图片大小
         */
        configuration.setImageSize(300);
        /**
         * 背景颜色
         */
        configuration.setBackgroudColor(Color.RED);
        /**
         * 边线粗细、半径
         */
        configuration.setBorderCircle(new SealCircle(3, 140, 140));
//        configuration.setBorderCircle(new SealCircle(3, 140, 100));
        /**
         * 内边线粗细、半径
         */
 //       configuration.setBorderInnerCircle(new SealCircle(1, 135, 135));
//        configuration.setBorderInnerCircle(new SealCircle(1, 135, 95));
        /**
         * 内环线粗细、半径
         */
    //    configuration.setInnerCircle(new SealCircle(2, 105, 105));
//        configuration.setInnerCircle(new SealCircle(2, 85, 45));

        //1.生成公章
        try {
            SealUtil.buildAndStoreSeal(configuration, "C:\\Users\\TR\\Desktop\\微信图片_20200811222331.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2.生成私章
//        SealFont font = new SealFont();
//        font.setFontSize(120).setBold(true).setFontText("诸葛孔明");
//        SealUtil.buildAndStorePersonSeal(300, 16, font, "印", "C:\\Users\\TR\\Desktop\\微信图片_20200811222332.jpg");
```
* 接下来就是 使用画图将目标绘画到文件中
```java
    public static void overlapImage(String desPath,String srcPath) throws IOException {
        BufferedImage a = ImageIO.read(new File(srcPath));
        BufferedImage b = ImageIO.read(new File(desPath));
        Graphics2D gd = a.createGraphics();
        int x = 100;
        int y = 100;
        gd.drawImage(b, 360, 500, 250, 250, null);
        gd.dispose();
        File file = new File("E:\\"+UUID.randomUUID()+".png");
        if (!file.exists()) {
            file.mkdirs();
        }
        ImageIO.write(a, "jpg", file);
    }
```


