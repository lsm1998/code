package com.lsm1998.auto.database.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据表内的列对象
 */
@Getter
@Setter
@Builder
public class Column {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 是否为主键
     */
    private boolean primaryKey;
    /**
     * 是否为外键
     */
    private boolean foreignKey;
    /**
     * 列长度
     */
    private int size;
    /**
     * 小数点位数
     */
    private int decimalDigits;
    /**
     * 是否为空
     */
    private boolean nullable;
    /**
     * 是否自增
     */
    private boolean autoincrement;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 数据库类型
     */
    private int jdbcType;
    /**
     * java.sql.Types对应的类型名称
     */
    private String jdbcTypeName;
    /**
     * 列名格式化后对应实体类内的属性
     */
    private String javaProperty;
    /**
     * java.lang.xxx数据类型
     */
    private String javaType;
    /**
     * java.lang.xxx数据类型全名称
     */
    private String fullJavaType;
}
