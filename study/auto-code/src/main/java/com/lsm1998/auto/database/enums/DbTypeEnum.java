package com.lsm1998.auto.database.enums;

import com.lsm1998.auto.database.IDataBase;
import com.lsm1998.auto.database.impl.*;
import lombok.Getter;

/**
 * 数据库类型枚举
 */
@Getter
public enum DbTypeEnum {
    /**
     * MySQL数据库类型
     */
    MySQL("com.mysql.cj.jdbc.Driver", MySqlIDataBase.class),
    /**
     * Db2数据库类型
     */
    Db2("com.ibm.db2.jcc.DB2Driver", Db2IDataBase.class),
    /**
     * PostgreSQL数据库类型
     */
    PostgreSQL("org.postgresql.Driver", PostgreSqlIDataBase.class),
    /**
     * SQLServer数据库类型
     */
    SQLServer("com.microsoft.sqlserver.jdbc.SQLServerDriver", SqlServerIDataBase.class),
    /**
     * Oracle数据库类型
     */
    Oracle("oracle.jdbc.driver.OracleDriver", OracleIDataBase.class);
    /**
     * 驱动类全限定名
     */
    private String value;
    /**
     * 数据库实现类位置
     */
    private Class<? extends IDataBase> dataBaseImplClass;

    DbTypeEnum(String value, Class<? extends IDataBase> dataBaseImplClass) {
        this.value = value;
        this.dataBaseImplClass = dataBaseImplClass;
    }
}
