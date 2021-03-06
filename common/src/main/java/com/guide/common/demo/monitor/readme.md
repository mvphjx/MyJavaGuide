# 设计模式实战 性能计数器

## 需求分析

- 接口统计信息：包括接口响应时间的统计信息，以及接口调用次数的统计信息等。
- 统计信息的类型：max、min、avg、percentile、count、tps 等。
- 统计信息显示格式：Json、Html、自定义显示格式。
- 统计信息显示终端：Console、Email、HTTP 网页、日志、自定义显示终端。
## 设计

### 数据采集
负责打点采集原始数据，包括记录每次接口请求的响应时间和请求时间。
数据采集过程要高度容错，不能影响到接口本身的可用性。
除此之外，因为这部分功能是暴露给框架的使用者的，所以在设计数据采集 API 的时候，我们也要尽量考虑其易用性。
### 存储
负责将采集的原始数据保存下来，以便后面做聚合统计。
数据的存储方式有多种，比如：Redis、MySQL、HBase、日志、文件、内存等。
数据存储比较耗时，为了尽量地减少对接口性能（比如响应时间）的影响，采集和存储的过程异步完成。
### 聚合统计
负责将原始数据聚合为统计数据，比如：max、min、avg、pencentile、count、tps 等。
为了支持更多的聚合统计规则，代码希望尽可能灵活、可扩展。
### 显示
负责将统计数据以某种格式显示到终端，比如：输出到命令行、邮件、网页、自定义显示终端等。
