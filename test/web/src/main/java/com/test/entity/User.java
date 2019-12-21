/*
 * 作者：刘时明
 * 时间：2019/12/21-13:27
 * 作用：
 */
package com.test.entity;

import com.lsm1998.ibatis.annotation.MyColumn;
import com.lsm1998.ibatis.annotation.MyEntry;
import com.lsm1998.ibatis.annotation.MyId;
import com.lsm1998.ibatis.annotation.MyTable;
import com.lsm1998.ibatis.enums.AutoIncrement;
import lombok.Data;

import java.io.Serializable;

@Data
@MyEntry
@MyTable(name = "boot_user")
public class User implements Serializable
{
    // 指定主键，并不需要自增
    @MyId(AutoIncrement.FALSE)
    private Integer id;
    // 指定字段名、设置唯一索引、不为空、长度=55
    @MyColumn(value = "name",index = true,unique=true,nullable = true,length = 55)
    private String username;
    // 使用默认的配置
    private Integer age;
}
