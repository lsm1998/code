/*
 * 作者：刘时明
 * 时间：2019/12/21-14:09
 * 作用：
 */
package com.test.mapper;

import com.lsm1998.ibatis.annotation.MyParam;
import com.lsm1998.ibatis.annotation.MySelect;
import com.test.entity.User;

import java.util.List;

public interface UserMapper
{
    @MySelect("SELECT * FROM boot_user LIMIT #{page},#{size}")
    List<User> list(@MyParam("page") int page, @MyParam("size")int size);
}
