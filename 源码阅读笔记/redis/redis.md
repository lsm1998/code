# redis

### 环境

Redis3.0

### 自己实现

[Java语言实现的简单Redis](https://github.com/lsm1998/jedis)

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

### 1.命令执行流程解析

#### 查询命令（keys）

1.

#### 写入命令（set）

1.

### 2.数据类型实现

#### 字符串类型

Redis没有使用C语言原生字符串，而是封装实现了Sds结构体

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

Redis String类型编码方式有int、raw、embstr三种
1. 当长度小于21字节，且可以转换为long long，则为int；
2. 长度小于39，则为embstr；
3. 其他情况为raw；

+ 代码片段
````
robj *tryObjectEncoding(robj *o)
{
    long value;

    sds s = o->ptr;
    size_t len;

    redisAssertWithInfo(NULL, o, o->type == REDIS_STRING);

    // 只在字符串的编码为 RAW 或者 EMBSTR 时尝试进行编码
    if (!sdsEncodedObject(o)) return o;

    // 不对共享对象进行编码
    if (o->refcount > 1) return o;

    // 对字符串进行检查
    // 只对长度小于或等于 21 字节，并且可以被解释为整数的字符串进行编码
    len = sdslen(s);
    if (len <= 21 && string2l(s, len, &value))
    {
        /* This object is encodable as a long. Try to use a shared object.
         * Note that we avoid using shared integers when maxmemory is used
         * because every object needs to have a private LRU field for the LRU
         * algorithm to work well. */
        if (server.maxmemory == 0 &&
            value >= 0 &&
            value < REDIS_SHARED_INTEGERS)
        {
            decrRefCount(o);
            incrRefCount(shared.integers[value]);
            return shared.integers[value];
        } else
        {
            if (o->encoding == REDIS_ENCODING_RAW) sdsfree(o->ptr);
            o->encoding = REDIS_ENCODING_INT;
            o->ptr = (void *) value;
            return o;
        }
    }

    // 尝试将 RAW 编码的字符串编码为 EMBSTR 编码
    if (len <= REDIS_ENCODING_EMBSTR_SIZE_LIMIT)
    {
        robj *emb;
        if (o->encoding == REDIS_ENCODING_EMBSTR) return o;
        emb = createEmbeddedStringObject(s, sdslen(s));
        decrRefCount(o);
        return emb;
    }
    
    // 这个对象没办法进行编码，尝试从 SDS 中移除所有空余空间
    if (o->encoding == REDIS_ENCODING_RAW &&
        sdsavail(s) > len / 10)
    {
        o->ptr = sdsRemoveFreeSpace(o->ptr);
    }
    return o;
}
````

raw和embstr有何区别？
1. embstr，RedisObject对象头结构和SDS对象连续存储在一起的，使用malloc方法一次分配；
2. raw，RedisObject对象头结构和SDS对象不连续，需要两次malloc；

为什么是临界值是21和39？
1. 21取决于long类型范围；
2. embstr由redisObject和sdshdr组成，其中redisObject占16个字节，当buf内的字符串长度是39时，sdshdr的大小为4+4+39+1=48，那一个字节是'\0'，加起来刚好64；

#### list类型

使用压缩列表或双向链表实现

+ 代码片段

````
// 双向链表
typedef struct listNode 
{
    // 前置节点
    struct listNode *prev;
    // 后置节点
    struct listNode *next;
    // 节点的值
    void *value;
} listNode;

// 压缩列表
typedef struct zlentry
{
    // prevrawlen ：前置节点的长度
    // prevrawlensize ：编码 prevrawlen 所需的字节大小
    unsigned int prevrawlensize, prevrawlen;
    // len ：当前节点值的长度
    // lensize ：编码 len 所需的字节大小
    unsigned int lensize, len;
    // 当前节点 header 的大小
    // 等于 prevrawlensize + lensize
    unsigned int headersize;
    // 当前节点值所使用的编码类型
    unsigned char encoding;
    // 指向当前节点的指针
    unsigned char *p;
} zlentry;
````

Redis List类型编码方式有双向链表和压缩列表两种，默认是压缩列表

+ 压缩列表转双向链表的场景
1. list添加后检查节点个数，如果超过则转为双向链表，默认筏值512，可配置；
2. 插入长字符串时，默认筏值64，可配置；

+ 代码片段
````
void listTypePush(robj *subject, robj *value, int where)
{
    // 检查是否需要转换编码？
    listTypeTryConversion(subject, value);
    if (subject->encoding == REDIS_ENCODING_ZIPLIST &&
        ziplistLen(subject->ptr) >= server.list_max_ziplist_entries)
        listTypeConvert(subject, REDIS_ENCODING_LINKEDLIST);
    // ZIPLIST
    if (subject->encoding == REDIS_ENCODING_ZIPLIST)
    {
        int pos = (where == REDIS_HEAD) ? ZIPLIST_HEAD : ZIPLIST_TAIL;
        // 取出对象的值，因为 ZIPLIST 只能保存字符串或整数
        value = getDecodedObject(value);
        subject->ptr = ziplistPush(subject->ptr, value->ptr, sdslen(value->ptr), pos);
        decrRefCount(value);
        // 双端链表
    } else if (subject->encoding == REDIS_ENCODING_LINKEDLIST)
    {
        if (where == REDIS_HEAD)
        {
            listAddNodeHead(subject->ptr, value);
        } else
        {
            listAddNodeTail(subject->ptr, value);
        }
        incrRefCount(value);
    } else
    {
        // 未知编码
        redisPanic("Unknown list encoding");
    }
}
````

压缩列表和双向链表区别？
1. 压缩列表内存是连续的，不使用指针，节约内存；
2. 压缩列表内存固定，每次修改大小需要重新分配内存；

#### set类型

使用整数集合或HT(hash table)实现

#### hash类型

使用压缩列表或HT(hash table)实现

#### zset类型

使用压缩列表或跳表实现

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

有序集合，当一个集合只包含整数，且数量不多，Redis会使用intset存储

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

创建set时，如果都是数字，则创建intset

+ 代码片段

````
robj *setTypeCreate(robj *value)
{
    // 如果集合的元素都可以表示为 long long 类型，则创建整数集
    if (isObjectRepresentableAsLongLong(value, NULL) == REDIS_OK)
        return createIntsetObject();
    return createSetObject();
}
````

intset编码升级为HT编码的2个条件（前提，当前set是intset编码）：

1. 添加set时，某个add的元素不能转换为long long；
2. 添加set时，数量超过限制，默认筏值512，可配置；

+ 代码片段

````
// 如果对象的值可以编码为整数的话，那么将对象的值添加到 intset 中
if (isObjectRepresentableAsLongLong(value, &llval) == REDIS_OK)
{
    uint8_t success = 0;
    subject->ptr = intsetAdd(subject->ptr, llval, &success);
    if (success)
    {
        // 添加成功，检查集合在添加新元素之后是否需要转换为字典
        if (intsetLen(subject->ptr) > server.set_max_intset_entries)
            setTypeConvert(subject, REDIS_ENCODING_HT);
        return 1;
    }
} else
{
    // 如果对象的值不能编码为整数，那么将集合从 intset 编码转换为 HT 编码
    // 然后再执行添加操作
    setTypeConvert(subject, REDIS_ENCODING_HT);
    redisAssertWithInfo(NULL, value, dictAdd(subject->ptr, value, NULL) == DICT_OK);
    incrRefCount(value);
    return 1;
}
````

#### ziplist

压缩列表，当集合set/hash集合只有少量元素，且元素为整数或短字符串时Redis会使用ziplist存储


### 3.Redis持久化

#### AOF
Append-only file，只追加操作文件，将每次执行的命令追加在AOF文件末尾，重新启动时，程序就可以通过重新执行AOF文件中的命令来达到重建数据集的目的；

* 优点：相对于RDB来说，保存的数据更完整，更好的容灾性，每次追加一条命令，开销小；
* 缺点：AOF文件体积大，加载速度慢；

#### RDB
是一个紧凑的二进制文件，保存了某个时间点的数据集

