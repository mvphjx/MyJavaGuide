# MyJavaGuide java知识体系
##JavaSE基础

###基础语法和面向对象
    数据类型
    变量、赋值、基本运算
    流程控制：if while for
    函数

###集合框架
    主要接口如下
    Collection
    Set
    List
    Queue Deque
    
    
    Map
    SortedMap
    ConcurrentMap


###IO框架

#### InputStream/OutputStream：这是基类，它们是抽象类。
- FileInputStream/FileOutputStream：输入源和输出目标是文件的流。
- ByteArrayInputStream/ByteArrayOutputStream：输入源和输出目标是字节数组的流。
- DataInputStream/DataOutputStream：装饰类，按基本类型和字符串而非只是字节读写流。
- BufferedInputStream/BufferedOutputStream：装饰类，对输入输出流提供缓冲功能。
#### Reader/Writer：字符流的基类，它们是抽象类；   
- InputStreamReader/OutputStreamWriter：适配器类，将字节流转换为字符流；
- FileReader/FileWriter：输入源和输出目标是文件的字符流；
- CharArrayReader/CharArrayWriter：输入源和输出目标是char数组的字符流；
- StringReader/StringWriter：输入源和输出目标是String的字符流；
- BufferedReader/BufferedWriter：装饰类，对输入/输出流提供缓冲，以及按行读写功能；
- PrintWriter：装饰类，可将基本类型和对象转换为其字符串形式输出的类。

###网络编程
Socket

##JavaEE基础
###JDBC
    java Database Connectivity 数据库连接API
###JNDI
    Java Name and Directory Interface；JNDI API 被用于执行名字和目录服务。
###EJB
    Enterprise JavaBean；分布式规范
###RMI
    RemoteMethod Invoke；RMI是一种被EJB使用的更底层的协议。
###JSP
    Java Server Pages；Jsp页面由html代码和嵌入其中的Java新代码所组成。
###Servlet
    servlet是一种小型的java程序，它扩展了web服务器的功能。
###JPA
    Java Persistence API；Java持久层API
###其他
    JMS（Java Message Service）JMS是用于和面向消息的中间件相互通信的应用程序接口（API）。
    JTA（Java Transaction Architecture）Java事务API。
    JTS(Java Transaction Service) JTS是CORBA OTS事务监控的基本的实现。
    JavaMail JavaMail是用于存取邮件服务的API，它提供了一套邮件服务器的抽象类。
    JAF（JavaBeans Activation Framework）

##Java高级特性
### 并发编程
#### Thread
属性以及方法：

    id、name、优先级、状态、是否daemo线程、
    sleep方法  temporarily Stop execution，to block
    yield方法  Give in its current use of a processor
    join方法：Waits for this thread to die.
创建线程

    继承Thread；
    实现Runnable接口；
    实现CallAble接口；
    通过线程池创建。
状态

    RUNNABLE：线程在运行或具备运行条件只是在等待操作系统调度。
    WAITING/TIMED_WAITING：线程在等待某个条件或超时。
    BLOCKED：线程在等待锁，试图进入同步块。
    NEW/TERMINATED：线程还未启动或已结束。

#### 线程安全的机制
面对共享内存有两个问题，竞态条件和内存可见性，有以下几种解决方案：

    使用synchronized；
    使用显式锁；
    使用volatile；
    使用原子变量和CAS；
    写时复制；
    使用ThreadLocal。线程本地变量,每个线程都有同一个变量的独有拷贝
    线程安全容器

#### 线程的协作机制
协作场景：

    生产者/消费者协作模式；
    同时开始；
    主从协作模式；
    异步结果；
    集合点
协作机制：

    wait/notify；
    显式条件；
    线程的中断；
    协作工具类；
    阻塞队列；
    Future/FutureTask。
协作工具类

    读写锁ReentrantReadWriteLock。应用场景：在读多写少的场景中，让读操作并行可以明显提高性能。
    信号量Semaphore。应用场景：可以限制对资源的并发访问数
    倒计时门栓CountDownLatch。两种应用场景：一种是同时开始，另一种是主从协作;实现不同角色线程间的同步。
    循环栅栏CyclicBarrier。应用场景：集合点模式,实现同一角色线程间的协调一致

#### 线程池
    线程池大小
    队列
    任务拒绝策略
    线程工厂

#### 任务执行
任务执行服务大大简化了执行异步任务所需的开发，它引入了一个“执行服务”的概念，
将“任务的提交”和“任务的执行”相分离，“执行服务”封装了任务执行的细节，
对于任务提交者而言，它可以关注于任务本身，如提交任务、获取结果、取消任务，而不需要关注任务执行的细节，
如线程创建、任务调度、线程关闭等。

    Runnable和Callable：表示要执行的异步任务。
    Executor和ExecutorService：表示执行服务。
    Future：表示异步任务的结果。

#### 定时任务
    Timer
    ScheduledExecutorService，需要特别注意Timer的一些陷阱，实践中建议使用ScheduledExecutorService。
    Quartz
### 动态编程
#### 反射
    作用：在运行时，而非编译时，动态获取类型的信息。
    场景：序列化
#### 注解
    作用：注解可以被编译器、程序运行时和其他工具使用，用于增强或修改程序行为等
    场景：定制序列化(@JsonProperty )、依赖注入容器(@Autowrite) 等......
#### 动态代理
    作用：动态控制对象的访问。Java SDK动态代理、cglib动态代理
    场景：面向切面的编程AOP
#### 类加载器
    作用：类加载器ClassLoader就是加载其他类的类，它负责将字节码文件加载到内存，创建Class对象。
    场景：1）热部署；2）应用的模块化和相互隔离；3）从不同地方灵活加载
#### 函数式编程
    Lambda表达式
    函数式数据处理
    组合式异步编程CompletableFuture
    Stream API


### JVM
堆
栈
### JMM
JMM是一个语言级的内存模型，处理器内存模型是硬件级的内存模型，顺序一致性内存模型是一个理论参考模型


##框架
###Spring
###SpringMVC
###SptingBoot
###SpringData
###Hibernate
###Mybatis
###Mybatis-Plus


##数据结构与算法


##网络通信
TCP
HTTP

Socket
BIO
NIO
AIO
Netty

WebService
RPC




##缓存中间件
redis
memcached


##消息中间件
ActiveMQ
RabbitMQ
RocketMQ
kafka


##搜索中间件
Lucene
Solr
ElasticSearch
Logstash
Kibana


##存储中间件
fastdfs
hdfs
hbase
mongodb


##微服务
服务注册 eureka
负载均衡&Session共享
断路器
链路监控
分布式事务
容器
最佳实践：Spring Cloud


##DevOps
Maven
SVN/Git
Jenkins
Idea
禅道
知识库（钉钉、语雀）



##全局把控
###开发管理
敏捷开发
瀑布式开发
螺旋式开发
迭代式开发

###开发模式
TDD 测试驱动开发
BDD 行为驱动开发
ATDD 验收测试驱动开发
DDD 领域驱动开发

###架构分层和分类
业务架构
应用架构
数据架构
代码架构
技术架构

###架构演进
单体架构
分布式架构
微服务架构
service mesh
serverless

###架构核心要素
高性能架构
可用性架构
伸缩性架构
可扩展性架构
安全性架构

