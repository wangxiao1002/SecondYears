#配置中心
## 配置获取地址规则
/{application}/{profile}/[{label}]

/{application}-{profile}.yml

/{application}-{profile}.properties

/{label}/{application}-{profile}.properties

/{label}/{application}-{profile}.yml
## 遇到的问题
在configServer 里面配置 servlet.context-path 后 其他服务获取路径依旧是没有添加context-path</br>
需要在eureka instance(服务端) 配置以下配置
```
   metadata-map:
      configPath: ${server.servlet.context-path}
```
