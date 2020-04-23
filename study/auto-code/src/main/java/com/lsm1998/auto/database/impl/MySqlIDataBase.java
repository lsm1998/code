package com.lsm1998.auto.database.impl;

import com.lsm1998.auto.database.DbConfig;
import com.lsm1998.auto.database.enums.ErrorEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySQL数据库类型实体
 */
public class MySqlIDataBase extends DataBase {
    /**
     * MySQL查询表状态的执行SQL
     */
    private static final String TABLE_COMMENT_SQL = "show table status where NAME=?";
    /**
     * 表备注字段名称
     */
    private static final String TABLE_COMMENT_COLUMN = "COMMENT";

    public MySqlIDataBase(DbConfig dbConfig) {
        super(dbConfig);
    }

    /**
     * 获取表备注信息
     *
     * @param tableName 表名
     * @return 表备注信息
     */
    @Override
    public String getTableComment(String tableName) {
        try {
            PreparedStatement statement = connection.prepareStatement(TABLE_COMMENT_SQL);
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(TABLE_COMMENT_COLUMN);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format(ErrorEnum.NOT_GET_COMMENT.getMessage(), tableName));
    }
}
