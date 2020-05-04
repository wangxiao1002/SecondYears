#配置中心
## 配置获取地址规则
/{application}/{profile}/[{label}]

/{application}-{profile}.yml

/{application}-{profile}.properties

/{label}/{application}-{profile}.properties

/{label}/{application}-{profile}.yml
## 遇到的问题
1. 在configServer 里面配置 servlet.context-path 后 其他服务获取路径依旧是没有添加context-path</br>
需要在eureka instance(服务端) 配置以下配置</br>
```
   metadata-map:
      configPath: ${server.servlet.context-path}
```
## 注册ip问题
开启使用ip 注册服务需要配置 ip 首选项
```
eureka:
  instance:
    prefer-ip-address: true
```
但在阿里云上面会首先以内网ip注册进去，所以可以在配置文件中指定，或者在docker 启动时候指定，具体配置如下
```
eureka:
  instance:
    prefer-ip-address: true
    ip-address: 127.0.0.1
```
容器build 时候
```
java -jar --eureka.instance.ip-address=${ip}
``` 
容器启动时候
```
docker run -e ip='127.0.0.1'
```

