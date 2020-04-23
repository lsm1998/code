package com.lsm1998.auto.database.impl;

import com.lsm1998.auto.database.DbConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Oracle数据库实现
 */
public class OracleIDataBase extends DataBase {

    public OracleIDataBase(DbConfig dbConfig) {
        super(dbConfig);
    }

    /**
     * 获取表的备注信息
     *
     * @param tableName 表名
     * @return 表备注信息
     */
    @Override
    public String getTableComment(String tableName) {
        try {
            ResultSet resultSet = connection.getMetaData().getTables(null, "%", tableName, new String[]{"TABLE"});
            if (resultSet.next()) {
                return resultSet.getString("REMARKS");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
