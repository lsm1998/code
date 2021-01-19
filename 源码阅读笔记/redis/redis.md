# redis

### 环境

Redis3.0

### 基本

+ 服务端main函数入口：/src/redis.c

### 命令执行流程解析

#### 查询命令（keys）
1. 你

#### 写入命令（set）
1. 我

### 1.数据类型实现

#### 字符串类型

redis没有使用C语言原生字符串，封装实现了Sds结构体
<br>代码片段
````
/*
 * 保存字符串对象的结构
 */
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

#### set类型

#### hash类型

#### zset类型
