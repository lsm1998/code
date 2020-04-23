package com.lsm1998.auto.database.model;

import com.lsm1998.auto.database.enums.JavaTypeEnum;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * 数据表对象
 */
@Data
@Builder
public class Table {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表驼峰名称
     */
    private String tableCamelName;
    /**
     * 表类型
     */
    private String tableType;
    /**
     * 表别名
     */
    private String tableAlias;
    /**
     * 表备注信息
     */
    private String remark;
    /**
     * catalog
     */
    private String catalog;
    /**
     * schema
     */
    private String schema;
    /**
     * 数据列列表
     */
    private List<Column> columns;
    /**
     * 主键列表
     */
    private List<Column> primaryKeys;
    /**
     * 是否存在java.sql.Date类型的列
     */
    private boolean hasSqlDate;
    /**
     * 是否存在TimeStamp的列
     */
    private boolean hasTimeStamp;
    /**
     * 是否存在BigDecimal的列
     */
    private boolean hasBigDecimal;

    /**
     * 构建对象后设置是否存在特殊类型的字段
     * 如：java.math.BigDecimal、java.sql.TimeStamp等
     *
     * @return Table实例
     */
    public Table buildAfterSetting() {
        for (Column column : columns) {
            // 是否存在bigDecimal的列
            if (JavaTypeEnum.TYPE_BIG_DECIMAL.getFullName().equals(column.getFullJavaType())) {
                this.hasBigDecimal = true;
            }
            // 是否存在timeStamp的列
            if (JavaTypeEnum.TYPE_TIMESTAMP.getFullName().equals(column.getFullJavaType())) {
                this.hasTimeStamp = true;
            }
            // 是否存在java.sql.Date的列
            if (JavaTypeEnum.TYPE_DATE.getFullName().equals(column.getFullJavaType())) {
                this.hasSqlDate = true;
            }
        }

        return this;
    }
}
