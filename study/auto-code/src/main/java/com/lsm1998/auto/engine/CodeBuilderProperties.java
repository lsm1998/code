package com.lsm1998.auto.engine;

import com.lsm1998.auto.database.DbConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CodeBuilderProperties
{
    // 数据源配置
    private DbConfig dbConfig;
    // 表名
    private List<String> tables;
    // 表名表达式
    private String generatorByPattern;
    // 前缀
    private String ignoreClassPrefix;
    // 后缀
    private String ignoreClassSuffix;
    // 包名
    private String packageName;
    // 工程名
    private String projectCode;
}
