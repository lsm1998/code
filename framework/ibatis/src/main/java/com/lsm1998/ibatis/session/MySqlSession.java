package com.lsm1998.ibatis.session;

import java.io.Serializable;
import java.util.List;

/**
 * @作者：刘时明
 * @时间:2018/12/27-14:54
 * @说明：
 */
public interface MySqlSession
{
    /**
     * 获取Mapper对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> clazz);

    /**
     * 保存实体
     *
     * @param object
     * @return
     */
   boolean insert(Object object);

    /**
     * 更新实体
     * @param object
     * @return
     */
   boolean update(Object object);


    /**
     * 根据类型+id，删除实体
     *
     * @param clazz
     * @param id
     * @return
     */
    boolean delete(Class<?> clazz, Serializable id);


    /**
     * 根据类型，获取所有实体
     *
     * @param clazz
     * @param <T>
     * @return
     */
   <T> List<T> getAll(Class<T> clazz);

    /**
     * 分页查询
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getAllByPage(Class<T> clazz, int start, int end);

    /**
     * 获取总记录条数
     *
     * @return
     */
    int getCount();

    /**
     * 关闭资源
     */
    void close();
}
