package com.lsm1998.auto.database;

import com.lsm1998.auto.database.enums.DbTypeEnum;
import com.lsm1998.auto.database.enums.ErrorEnum;

import java.lang.reflect.Constructor;

/**
 * 数据库工厂
 */
public class DataBaseFactory {
    /**
     * 构造函数私有化
     * 禁止通过new方式实例化对象
     */
    private DataBaseFactory() {
    }

    /**
     * 获取配置的数据库类型实例
     *
     * @param dbConfig 配置构建参数实体
     * @return 数据库实例
     */
    public static IDataBase newInstance(DbConfig dbConfig) {
        // 数据库类型枚举实例
        DbTypeEnum dbTypeEnum = dbConfig.getDbType();
        try {
            // 获取数据库实现类的构造函数
            Constructor constructor = dbTypeEnum.getDataBaseImplClass().getConstructor(DbConfig.class);
            // 反射获取数据库实现类实例
            return (IDataBase) constructor.newInstance(dbConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException(ErrorEnum.NOT_ALLOW_DB_TYPE.getMessage());
    }
}
