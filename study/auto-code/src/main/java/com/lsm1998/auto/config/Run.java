package com.lsm1998.auto.config;

import com.lsm1998.auto.database.DbConfig;
import com.lsm1998.auto.database.enums.DbTypeEnum;
import com.lsm1998.auto.engine.CodeBuilderProperties;
import com.lsm1998.auto.engine.EngineTemplate;

import java.util.Arrays;
import java.util.Collections;

public class Run {

    public static void main(String[] args) {
        DbConfig db = DbConfig.builder()
                .dbType(DbTypeEnum.MySQL)
                .dbUrl("jdbc:mysql://127.0.0.1:3306")
                .param("?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true")
                .dbName("test")
                .dbUserName("root")
                .dbPassword("123456")
                .build();

        CodeBuilderProperties codeBuilder = CodeBuilderProperties.builder()
                .dbConfig(db)
                .projectCode("demo")
                // .generatorByPattern("ips%")
                .tables(Collections.singletonList("t_user"))
                //.ignoreClassPrefix("aitcp_")
                //.ignoreClassSuffix("_t")
                .packageName("com.lsm1998.demo")
                .build();

        EngineTemplate engine = new EngineTemplate(codeBuilder);
        engine.builder();
    }

}
