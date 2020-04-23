package com.lsm1998.auto.database.model;

import lombok.Builder;
import lombok.Data;

/**
 * 数据表内的主键、外键对象
 */
@Data
@Builder
public class Key {
    /**
     * 主键表名
     */
    private String pkTableName;
    /**
     * 主键列名
     */
    private String pkColumnName;
    /**
     * 外键表名
     */
    private String fkTableName;
    /**
     * 外键列名
     */
    private String fkColumnName;
    /**
     * 外键中的序列号（值1表示外键的第一列，值2表示外键中的第二列）。
     */
    private Integer seq;

}
