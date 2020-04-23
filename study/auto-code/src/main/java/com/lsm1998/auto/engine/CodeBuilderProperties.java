package com.lsm1998.auto.engine;

import com.lsm1998.auto.database.DbConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CodeBuilderProperties
{
    private DbConfig dbConfig;
    private List<String> tables;
    private String generatorByPattern;
    private String ignoreClassPrefix;
    private String ignoreClassSuffix;
    private String packageName;
    private String projectCode;
}
