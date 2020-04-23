package com.lsm1998.auto.database;

import com.lsm1998.auto.database.enums.DbTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DbConfig
{
    private String dbName;
    private String dbUserName;
    private String dbPassword;
    private String dbUrl;
    private String dbDriverClassName;
    private DbTypeEnum dbType;
    private String param;

    public String getDbUrl()
    {
        if(dbType.equals(DbTypeEnum.SQLServer))
        {
            return dbUrl + ";DatabaseName=" + dbName;
        }else if(dbType.equals(DbTypeEnum.Oracle)){
            return dbUrl + ":" + dbName;
        }
        return dbUrl + "/" + dbName + param;
    }
}
