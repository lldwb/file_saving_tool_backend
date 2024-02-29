# 项目介绍

多设备同步网盘，基于 SpringBoot + Mybatis + Netty + Socket + MinIO + Redis + Elasticsearch + RabbitMQ + MySQL实现

# 负责工作(2版)

1、为了提高开发效率，使用了SpringBoot+MyBatis完成开发
2、使用Minio实现文件储存功能，并选取MySql储存文件结构，同时使用Elasticsearch实现文件的搜索功能
3、为了实现文件的恢复和存储空间的充分利用，使用Minio存储名称为文件特征码的文件，有相同文件时不进行上传，如果需要恢复文件数据库的文件路径指向原来的Minio路径
4、选用Netty进行客户端和服务器的通讯，封装了统一的消息处理器和操作接口，从而规范了通讯流程，解决复用性低的问题
5、使用Redis实现验证码登录功能，并选用RabbitMQ异步处理发送验证码，解决了响应慢的问题
6、选用MyBatis进行数据访问层开发，完成Java和SQL语句的解耦
7、为了统一接口的返回，自定义统一的VO对象，并且封装了全局异常处理器，从而规范了异常返回、屏蔽了项目冗余的报错细节
8、采用 Nginx 完成前端项目部署、采用Web容器完成后端项目部署，并且使用宝塔面板对项目进行运维监控。

# 负责工作(1版)

1、为了提高开发效率，使用了SpringBoot完成开发，同时SpringBoot中包含了Web容器(待修改，springboot提高开发效率高)
2、使用Minio实现文件储存功能，并选取MySql储存文件结构，同时使用Elasticsearch实现文件的搜索功能
3、为了实现文件的恢复和存储空间的充分利用，使用Minio存储名称为文件特征码的文件，有相同文件时不进行上传，如果需要恢复文件数据库的文件路径指向原来的Minio路径
4、选用Netty进行客户端和服务器的通讯，封装了统一的消息处理器和操作接口，从而规范了通讯流程，解决复用性低的问题
5、选用MyBatis进行数据访问层开发，完成Java和SQL语句的解耦
6、为了统一接口的返回，自定义统一的VO对象，并且封装了全局异常处理器，从而规范了异常返回、屏蔽了项目冗余的报错细节
7、采用 Nginx 完成前端项目部署、采用Web容器完成后端项目部署，并且使用宝塔面板对项目进行运维监
控。

# 负责工作(老板)

1）负责技术栈的选取与整体架构设计，包括需求分析、API文档设计以及数据库设计。
2）设计并开发核心功能模块，包括网盘上传下载和Socket控制客户端同步等。
3）实现网盘上传下载流程，并设计恢复文件功能，同时完成Socket控制客户端同步的功能。
4）利用Redis作为缓存中间件，实现验证码过期功能。
5）使用 Elasticsearch 实现了商品搜索功能的全文检索，提高了搜索的精准度和响应速度，使用异步双写同步。
6）利用RabbitMQ实现发送验证码和同步到Elasticsearch的操作。
7）使用Commons IO实现监听客户端文件修改并上传。

# 功能设计

o：前后端都完成
h：后端完成，前端未完成，测试通过
t：后端完成，前端未完成，待测试
x：待完成

minIO路径从UUID标记修改为SHA-256，保证减少存储空间的使用
把所有MD5修改为SHA-256
把客户端的UUID修改为会话id进行SHA-256加密存储

## 登录注册 o

### 发送验证码 o

### 验证验证码 o

## 基本网盘操作 x

### 文件列表(包含搜索文件) h

### 上传文件 t(最后完成测试)

### 上传文件(内部) h

### 恢复文件 h

### 删除文件 h

### 返回文件路径 t

## 网盘操作扩展

### 手动分享 x

### 提示更新 x

## 操作客户端 x

### 添加客户端 x

### 绑定客户端 x

### 删除客户端 x

## 客户端实现功能 x

### 生成uuid并根据uuid进行通讯 x

### 自动上传 x

### 自动下载 x

### 自动同步 x

## 扩展功能 x

### 登录属地功能

### 风险检测功能

### 异常邮箱反馈功能

# 连接配置(基于VPN异地组网)

## mysql

driver-class-name: com.mysql.cj.jdbc.Driver
url: jdbc:mysql://www:3306/file_saving_tool
username: file_saving_tool
password: Be8mhcSZZ8J3nAi3

## redis

host: www
password: "@lldwb_redis"
port: 7890

## minio

host: http://www:9000
username: lldwb
password: "98b058becd731"

## rabbitmq

host: iprus
port: 5672
username: admin
password: "ca171bc0"
exchange: test.exchange
deliveryMode: direct
key: canal-routing-key

## elasticsearch

uris: http://iprus:9200
username: elastic
password: ZKNfWXtbOAGyF3yv_caT

## mail

host: www
port: 25
username: file_saving_tool@lldwb.top
password: hxH^0oZ8U8b70EYs