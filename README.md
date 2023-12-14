# 技术采用
SpringBoot、Mybatis、MySQL
Redis、Elasticsearch、RabbitMQ
Vue
# 连接配置
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

# 项目描述
该文件保存工具的开发不仅仅满足了人们对文件保存的相关需求，还提供了文件搜索、文件分享、文件下载等功能。
在此基础上还提供了远程管理多个客户端(微被控端)的功能，控制远程客户端实现自动上传下载和同步操作。
网页上的服务端提供传统网盘的基本功能，同时可以控制多个客户端(微被控端)。
# 项目职责
1）负责技术栈的选取与整体架构设计、包括需求分析、API 文档设计、数据库设计。
2）设计并开发了文件搜索、文件分享、文件下载和远程控制客户端等核心功能模块。
3）实现订单流程，包括用户下单、卖家确认订单和发货，引入 RabbitMQ 作为消息队列，使订单流程异步化
处理，降低了系统响应时间，提高系统的并发能力。
4）利用 Redis 作为缓存中间件，存储商品库存信息，有效地避免了超卖和库存不足问题。
5）使用 Elasticsearch 实现了商品搜索功能的全文检索，提高了搜索的精准度和响应速度。
6）设计并实现了前后端数据交互接口，利用 Vue、uniapp 等技术优化了用户界面，并通过 Axios 进行前后
端数据的传输与交互。
# 项目感想
 
# 功能设计
o：前后端都完成
h：后端完成，前端未完成，测试通过
t：后端完成，前端未完成，待测试
x：待完成

minIO路径从UUID标记修改为SHA-256，保证减少存储空间的使用
把所有MD5修改为SHA-256

## 登录注册 o
### 发送验证码 o
### 验证验证码 o

## 基本网盘操作 x
### 文件列表 t
### 搜索文件 t
### 上传文件 t(先完成测试)
### 上传文件(内部) h
### 恢复文件 h
### 删除文件 h
### 手动分享 x
### 提示更新 x

## 操作客户端 x
### 添加客户端
### 删除客户端

## 客户端实现功能 x
### 生成uuid并根据uuid进行通讯 x
### 自动上传
### 自动下载
### 自动同步

## 扩展功能 x
### 登录属地功能
### 风险检测功能
### 异常邮箱反馈功能