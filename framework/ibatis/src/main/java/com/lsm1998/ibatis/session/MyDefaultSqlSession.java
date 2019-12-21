/*
 * 作者：刘时明
 * 时间：2019/12/21-15:19
 * 作用：
 */
package com.lsm1998.ibatis.session;

import com.lsm1998.ibatis.reflect.MyMapperProxyFactory;
import com.lsm1998.ibatis.util.MySQLUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class MyDefaultSqlSession implements MySqlSession
{
    private Connection connection;
    private boolean autoCommit;

    protected MyDefaultSqlSession(Connection connection)
    {
        this.connection = connection;
    }

    protected MyDefaultSqlSession(Connection connection, boolean autoCommit)
    {
        this.connection = connection;
        this.autoCommit = autoCommit;
    }

    public <T> T getMapper(Class<?> clazz)
    {
        try
        {
            return MyMapperProxyFactory.getProxy(clazz, connection);
        } catch (Exception e)
        {
            System.err.println("获取" + clazz + "对象出现异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 保存或者更新实体
     *
     * @param object
     * @return
     */
    public boolean insert(Object object)
    {
        return MySqlSessionDefaultUtil.insert(connection, object);
    }

    public boolean update(Object object)
    {
        return MySqlSessionDefaultUtil.update(connection, object);
    }

    /**
     * 根据类型+id，删除实体
     *
     * @param clazz
     * @param id
     * @return
     */
    public boolean delete(Class<?> clazz, Serializable id)
    {
        return MySqlSessionDefaultUtil.delete(connection, clazz, id);
    }

    /**
     * 根据类型，获取所有实体
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getAll(Class<T> clazz)
    {
        return MySqlSessionDefaultUtil.getAll(connection, clazz);
    }

    /**
     * 分页查询
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getAllByPage(Class<T> clazz, int start, int end)
    {
        return MySqlSessionDefaultUtil.getAllByPage(connection, clazz, start, end);
    }

    /**
     * 获取总记录条数
     *
     * @return
     */
    public int getCount()
    {
        return MySqlSessionDefaultUtil.getCount(connection);
    }

    public void close()
    {
        MySQLUtil.closeAll(connection, null, null);
    }
}
