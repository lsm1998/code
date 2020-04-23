package com.lsm1998.auto.engine;

import com.lsm1998.auto.database.model.Table;
import lombok.Builder;
import lombok.Data;

/**
 * 模板结果实体
 * 没一个模板都会有该实体的对象实例传递
 * 比如：freemarker在process时传递该实体的实例到freemarker模板内
 */
@Data
@Builder
public class DataModelEntity {
    /**
     * 表格实例
     */
    private Table table;

    /**
     * 实体类名称
     */
    private String entityName;
    /**
     * 类名
     */
    private String className;
    /**
     * 包名
     * 如：com.xxx.xxx.user
     */
    private String packageName;

    /**
     * 项目编码
     */
    private String projectCode;
}
