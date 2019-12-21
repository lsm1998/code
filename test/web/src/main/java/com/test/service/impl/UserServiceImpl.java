/*
 * 作者：刘时明
 * 时间：2019/12/21-14:10
 * 作用：
 */
package com.test.service.impl;

import com.lsm1998.ibatis.session.MySqlSession;
import com.lsm1998.ibatis.session.MySqlSessionFactory;
import com.lsm1998.spring.beans.annotation.MyAutowired;
import com.lsm1998.spring.beans.annotation.MyService;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.UserService;

import java.util.List;

@MyService
public class UserServiceImpl implements UserService
{
    @MyAutowired
    private MySqlSessionFactory sessionFactory;

    @Override
    public int insertUser(User user)
    {
        MySqlSession sqlSession = sessionFactory.openSession();
        boolean result=sqlSession.insert(user);
        sqlSession.close();
        return result?1:0;
    }

    @Override
    public List<User> list(int page, int size)
    {
        System.out.println("list...");
        MySqlSession sqlSession = sessionFactory.openSession();
        System.out.println("sqlSession="+sqlSession);
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        List<User> list=mapper.list((page-1)*size, size);
        sqlSession.close();
        return list;
    }
}
