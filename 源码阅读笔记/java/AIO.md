# AIO

+ 一直听说Java AIO没有基于unix aio；

### 环境

基于Java11

### 源码分析

1. 使用AIO需要AsynchronousServerSocketChannel类，通过其accept方法注册一个事件回调处理器；
2. 最后调用UnixAsynchronousServerSocketChannelImpl的implAccept方法
   <br>代码片段
````
// register for connections
port.startPoll(fdVal, Net.POLLIN);
````
3. 通过第2点基本实锤，就是基于多路复用

### reactor和proactor

1. reactor反应器，proactor主动器；
2. Reactor实现了一个被动的事件分离和分发模型，服务等待请求事件的到来，再通过不受间断的同步处理事件，从而做出反应；
3. Proactor实现了一个主动的事件分离和分发模型；这种设计允许多个任务并发的执行，从而提高吞吐量；并可执行耗时长的任务（各个任务间互不影响）；

### 为什么Netty基于NIO而不是AIO？

1. Java NIO和AIO都是基于多路复用，AIO没有真正的基于unix aio；
2. Netty整体架构是reactor模型, 而AIO是proactor模型；
3. AIO接收数据需要预先分配缓存；
4. Linux上AIO不够成熟，处理回调结果速度跟不到处理需求；
5. AIO编程模式比NIO复杂；

````
Netty作者原话：
Not faster than NIO (epoll) on unix systems (which is true)
There is no daragram suppport
Unnecessary threading model (too much abstraction without usage)
````

### 既然AIO这么牛，为什么多数还是使用epoll？
````
linux的AIO的实现方式是内核和应用共享一片内存区域，应用通过检测这个内存区域
（避免调用nonblocking的read、write函数来测试是否来数据，因为即便调用nonblocking
的read和write由于进程要切换用户态和内核态，仍旧效率不高）来得知fd是否有数据，
可是检测内存区域毕竟不是实时的，你需要在线程里构造一个监控内存的循环，设置sleep，
总的效率不如epoll这样的实时通知
````
