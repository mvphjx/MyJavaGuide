##定义
远程过程调用（Remote Procedure Call）
是分布式系统常见的一种通信方法，从跨进程到跨物理机已经有几十年的历史。

##跨进程交互方式
- 直接交互（HTTP/RPC/WebService）
- 依赖中间件（Mysql/RabbitMQ/Kafaka/Redis）

##RPC框架介绍
 
 X|gRPC|thrift|RMI|dubbo|HadoopRPC
---|---|---|---|---|---
开发语言|多语言|多语言|Java|Java|Java
序列化|protobuf|thrift格式|Java序列化|hession2|R/Writable
注册中心|无|无|Jdk自带|zookeeper等|N无
跨语言|支持|支持|不支持|不支持|不支持
服务定义|Protobuf文件|Thrift文件|Java接口|Java接口|Java接口
服务治理|无|无|无|有|无

##技术栈
- 序列化：fastjson
- 网络通信 jetty 

##代码实现
- 第一步:创建工程、制定协议、通用工具方法
- 第二步:实现序列化模块
- 第三步:实现网络模块
- 第四步:实现Server模块
- 第五步:实现Client模块
- 第六步: gk-rpc使用案例


##代码结构
    codec   序列化模块
    proto   协议模块
    transport 网络模块
    server  服务器模块
    client  客户端模块




##跨进程交互方式

