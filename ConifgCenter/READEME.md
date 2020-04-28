#配置中心
*为了统一管理配置文件和动态刷新*
## 配置文件获取规则
【其中application是配置文件名，cs是配置文件后面的环境名，master是分支名（可省略）】
路径相应规则如下：
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
