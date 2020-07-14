# Nginx 配置总结
## nginx 环境安装（个人推荐Docker安装）
* 切换root (非root权限用户需要将命令添加)
* 安装依赖环境
 ```
 yum -y install wget gcc-c++ ncurses ncurses-devel cmake make perl bison openssl openssl-devel gcc* libxml2 libxml2-devel curl-devel libjpeg* libpng* freetype* autoconf automake zlib* fiex* libxml* libmcrypt* libtool-ltdl-devel* libaio libaio-devel  bzr libtool

wget https://www.openssl.org/source/openssl-1.0.2s.tar.gz
tar -zxvf openssl-1.0.2s.tar.gz
cd /usr/local/src/openssl-1.0.2s
./config --prefix=/usr/local/openssl-1.0.2s
make
make install

wget https://ftp.pcre.org/pub/pcre/pcre-8.43.tar.gz
tar -zxvf pcre-8.43.tar.gz
cd /usr/local/src/pcre-8.43
./configure --prefix=/usr/local/pcre-8.43
make
make install


wget https://sourceforge.net/projects/libpng/files/zlib/1.2.11/zlib-1.2.11.tar.gz
tar -zxvf zlib-1.2.11.tar.gz
cd /usr/local/src/zlib-1.2.11
./configure --prefix=/usr/local/zlib-1.2.11
make
make install

```
* nginx 安装
```
-- 路径地址均是安装解压路径
wget http://nginx.org/download/nginx-1.17.2.tar.gz
tar -zxvf nginx-1.17.2.tar.gz
cd /usr/local/src/nginx-1.17.2
./configure --prefix=/usr/local/nginx-1.17.2 --with-openssl=/usr/local/src/openssl-1.0.2s --with-pcre=/usr/local/src/pcre-8.43 --with-zlib=/usr/local/src/zlib-1.2.11 --with-http_ssl_module
make
make install

```
## nginx 实现负载
* 负载服务
```
http {
       ……
    upstream server_login {
       server 127.0.0.1:2001 weight=1;  #轮询服务器和访问权重
       server 127.0.0.1:2005 weight=2;
    }
 
    server {
        listen  80;
        # 拦截所有
        location / {
            proxy_pass http://server_login;
        }
        # 拦截指定地址
        location ^~/ordering{
        	# 根据需求重写路径
            rewrite ^/ordering/(.*)$ /ordering/$1 break;
            proxy_set_header   HOST   $host:38080;
            proxy_pass http://server_login;
            proxy_set_header   X-Real-IP $remote_addr;
            proxy_set_header Accept-Encoding "";
            proxy_intercept_errors on;
    	}
    }
}

```
* 失败剔除
```
# 在fail_timeout时间内失败了max_fails次请求后，则认为该上游服务器不可用，然后将该服务地址踢除掉。fail_timeout时间后会再次将该服务器加入存活列表，进行重试。
upstream server_login {
   server 127.0.0.1:8081 weight=1 max_fails=2 fail_timeout=60s;
   server 127.0.0.1:8082 weight=2 max_fails=2 fail_timeout=60s;
}
```
## Nginx 限流
* http 模块使用
```
#limit_req_zone定义在http块中，$binary_remote_addr表示保存客户端IP地址的二进制形式。
#Zone定义IP状态及URL访问频率的共享内存区域。zone=keyword标识区域的名字，以及冒号
后面跟区域大小。16000个IP地址的状态信息约1MB，所以示例中区域可以存储160000个IP地址。
#Rate定义最大请求速率。示例中速率不能超过每秒10个请求。

limit_req_zone $binary_remote_addr zone=mylimit:10m rate=10r/s;
```
* 负载服务模块
```
#burst排队大小，nodelay不限制单个请求间的时间。
location / {
        limit_req zone=mylimit burst=20 nodelay;
        proxy_pass http://server_login;
}

```
* 限流白名单
```
# 数字说明 24表示子网掩码:255.255.255.0
# 16表示子网掩码:255.255.0.0
# 8表示子网掩码:255.0.0.0
geo $limit {
	default              1;
	127.0.0.1/24  0;
}
 
map $limit $limit_key {
	1 $binary_remote_addr;
	0 "";
}
 
limit_req_zone $limit_key zone=mylimit:10m rate=1r/s;
 
location / {
        limit_req zone=mylimit burst=1 nodelay;
        proxy_pass http://server_login;
}

```
## nginx 缓存配置
* 静态资源缓存
```
location ~*  .(jpg|jpeg|png|gif|ico|css|js)$ {
   expires 2d;
}

```
* 代理缓存
```
//缓存路径，inactive表示缓存的时间，到期之后将会把缓存清理
proxy_cache_path /data/cache/nginx/ levels=1:2 keys_zone=cache:512m inactive = 1d max_size=8g;
 
location / {
    location ~ \.(htm|html)?$ {
        proxy_cache cache;
        proxy_cache_key    $uri$is_args$args;     //以此变量值做HASH，作为KEY
        //HTTP响应首部可以看到X-Cache字段，内容可以有HIT,MISS,EXPIRES等等
        add_header X-Cache $upstream_cache_status;
        proxy_cache_valid 200 10m;
        proxy_cache_valid any 1m;
        proxy_pass  http://server_login;
        proxy_redirect     off;
    }
    location ~ .*\.(gif|jpg|jpeg|bmp|png|ico|txt|js|css)$ {
        root /data/webapps/index;
        expires      3d;
        add_header Static Nginx-Proxy;
    }
}

```
* 在本地磁盘创建一个文件目录，根据设置，将请求的资源以K-V形式缓存在此目录当中，KEY需要自己定义（这里用的是url的hash值），同时可以根据需要指定某内容的缓存时长，比如状态码为200缓存10分钟，状态码为301，302的缓存5分钟，其他所有内容缓存1分钟等等。
可以通过purger的功能清理缓存。
## nginx 黑名单
* 一般配置
```
location / {
    deny  192.168.1.1;
    deny 192.168.1.0/24;
    allow 10.1.1.0/16;
    allow 2001:0db8::/32;
    deny  all;
}

```
* 高阶配置(lua 脚本+redis 或者数据库（该配置来源网络 ）)
```
1. 安装openResty
yum install yum-utils
yum-config-manager --add-repo https://openresty.org/package/centos/openresty.repo
yum install openresty
yum install openresty-resty
查看
yum --disablerepo="*" --enablerepo="openresty" list available
运行
service openresty start

2. 配置(/usr/local/openresty/nginx/conf/nginx.conf)
lua_shared_dict ip_blacklist 1m;
 
server {
    listen  80;
 
    location / {
        access_by_lua_file lua/ip_blacklist.lua;
        proxy_pass http://real_server;
    }
}

3. 编写脚本
local redis_host    = "192.168.1.132"
local redis_port    = 6379
local redis_pwd     = 123456
local redis_db = 2
 
-- connection timeout for redis in ms.
local redis_connection_timeout = 100
 
-- a set key for blacklist entries
local redis_key     = "ip_blacklist"
 
-- cache lookups for this many seconds
local cache_ttl     = 60
 
-- end configuration
 
local ip                = ngx.var.remote_addr
local ip_blacklist      = ngx.shared.ip_blacklist
local last_update_time  = ip_blacklist:get("last_update_time");
 
-- update ip_blacklist from Redis every cache_ttl seconds:
if last_update_time == nil or last_update_time < ( ngx.now() - cache_ttl ) then
 
  local redis = require "resty.redis";
  local red = redis:new();
 
  red:set_timeout(redis_connect_timeout);
 
  local ok, err = red:connect(redis_host, redis_port);
  if not ok then
    ngx.log(ngx.ERR, "Redis connection error while connect: " .. err);
  else
    local ok, err = red:auth(redis_pwd)
    if not ok then
      ngx.log(ngx.ERR, "Redis password error while auth: " .. err);
    else
        local new_ip_blacklist, err = red:smembers(redis_key);
        if err then
            ngx.log(ngx.ERR, "Redis read error while retrieving ip_blacklist: " .. err);
        else
        ngx.log(ngx.ERR, "Get data success:" .. new_ip_blacklist)
          -- replace the locally stored ip_blacklist with the updated values:
            ip_blacklist:flush_all();
          for index, banned_ip in ipairs(new_ip_blacklist) do
            ip_blacklist:set(banned_ip, true);
          end
          -- update time
          ip_blacklist:set("last_update_time", ngx.now());
      end
    end
  end
end
 
if ip_blacklist:get(ip) then
  ngx.log(ngx.ERR, "Banned IP detected and refused access: " .. ip);
  return ngx.exit(ngx.HTTP_FORBIDDEN);
end

```
## IP灰度发布
```
如果是内部IP，则反向代理到hilinux_02(预发布环境)；如果不是则反向代理到hilinux_01(生产环境)。

upstream host1 {
    server 192.168.1.100:8080 max_fails=1 fail_timeout=60;
}

upstream host2 {
    server 192.168.1.200:8080 max_fails=1 fail_timeout=60;
}

upstream default {
    server 192.168.1.100:8080 max_fails=1 fail_timeout=60;
}

server {
  listen 80;


  set $group default;

  if ($remote_addr ~ "192.168.119.1") {
      set $group host1;
  }
  if ($remote_addr ~ "192.168.119.2") {
      set $group host2;
  }

location / {                       
    proxy_pass http://$group;
    proxy_set_header   Host             $host;
    proxy_set_header   X-Real-IP        $remote_addr;
    proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
    index  index.html index.htm;
  }
}
```
