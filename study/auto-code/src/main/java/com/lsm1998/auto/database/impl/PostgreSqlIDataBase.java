package com.lsm1998.auto.database.impl;

import com.lsm1998.auto.database.DbConfig;

/**
 * Postgresql 数据库
 */
public class PostgreSqlIDataBase extends DataBase {

    public PostgreSqlIDataBase(DbConfig dbConfig) {
        super(dbConfig);
    }

    @Override
    public String getTableComment(String tableName) {
        // TODO 暂未支持
        return null;
    }
}
