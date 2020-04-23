package com.lsm1998.auto.database.impl;

import com.lsm1998.auto.database.DbConfig;
import com.lsm1998.auto.database.enums.ErrorEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SqlServer数据库实现
 */
public class SqlServerIDataBase extends DataBase {

    /**
     * SqlServer查询表状态的执行SQL
     */
    private static final String TABLE_COMMENT_SQL = "select convert(varchar(200), tableDesc.value) COMMENT from sys.tables\n" +
            "left join sys.extended_properties tableDesc on tables.object_id = tableDesc.major_id and tableDesc.minor_id = 0\n" +
            "where tables.name=?";
    /**
     * 表备注字段名称
     */
    private static final String TABLE_COMMENT_COLUMN = "COMMENT";

    public SqlServerIDataBase(DbConfig dbConfig) {
        super(dbConfig);
    }

    @Override
    public String getTableComment(String tableName){
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
