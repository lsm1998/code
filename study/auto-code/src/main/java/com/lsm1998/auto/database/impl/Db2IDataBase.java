package com.lsm1998.auto.database.impl;

import com.lsm1998.auto.database.DbConfig;

/**
 * DB2数据库实现
 */
public class Db2IDataBase extends DataBase {

    public Db2IDataBase(DbConfig dbConfig) {
        super(dbConfig);
    }

    @Override
    public String getTableComment(String tableName){
        // TODO 暂未支持
        return null;
    }
}
