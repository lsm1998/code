# redis

### 环境

Redis3.0

### 基本

+ 服务端main函数入口：/src/redis.c

### 网络模型

````
使用evport、epoll、kqueue、select其中一种
````

+ 代码片段

````
#ifdef HAVE_EVPORT
#include "ae_evport.c"
#else
#ifdef HAVE_EPOLL
#include "ae_epoll.c"
#else
#ifdef HAVE_KQUEUE

#include "ae_kqueue.c"

#else
#include "ae_select.c"
#endif
#endif
#endif
````

### 命令执行流程解析

#### 查询命令（keys）

1.

#### 写入命令（set）

1.

### 1.数据类型实现

#### 字符串类型

redis没有使用C语言原生字符串，而是封装实现了Sds结构体

+ 代码片段

````
struct sdshdr
{
    // buf 中已占用空间的长度
    int len;
    // buf 中剩余可用空间的长度
    int free;
    // 数据空间
    char buf[];
};
````

#### list类型

使用双向链表实现

+ 代码片段

````
typedef struct listNode 
{
    // 前置节点
    struct listNode *prev;
    // 后置节点
    struct listNode *next;
    // 节点的值
    void *value;
} listNode;
````

#### set类型

#### hash类型

#### zset类型

使用跳表实现

+ 代码片段

````
typedef struct zskiplistNode
{
    // 成员对象
    robj *obj;
    // 分值
    double score;
    // 后退指针
    struct zskiplistNode *backward;
    // 层
    struct zskiplistLevel
    {
        // 前进指针
        struct zskiplistNode *forward;
        // 跨度
        unsigned int span;
    } level[];
} zskiplistNode;
````

#### intset

当一个集合只包含整数，且数量不多，Redis会使用intset存储

+ 代码片段
````
typedef struct intset
{
    // 编码方式
    uint32_t encoding;
    // 集合包含的元素数量
    uint32_t length;
    // 保存元素的数组
    int8_t contents[];
} intset;
````
