package com.lsm1998.auto.database.util;

import com.lsm1998.auto.database.enums.JavaTypeEnum;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Java数据类型装载器
 */
public class JavaTypeResolver {

    /**
     * java 字段类型映射集合
     */
    private static Map<Integer, JavaTypeEnum> typeMap;


    static {
        typeMap = new HashMap<Integer, JavaTypeEnum>();
        typeMap.put(Types.ARRAY, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.BIGINT, JavaTypeEnum.TYPE_LONG);
        typeMap.put(Types.BINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.BIT, JavaTypeEnum.TYPE_BOOLEAN);
        typeMap.put(Types.BLOB, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.BOOLEAN, JavaTypeEnum.TYPE_BOOLEAN);
        typeMap.put(Types.CHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.CLOB, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.DATALINK, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.DATE, JavaTypeEnum.TYPE_DATE);
        typeMap.put(Types.DISTINCT, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.DOUBLE, JavaTypeEnum.TYPE_DOUBLE);
        typeMap.put(Types.FLOAT, JavaTypeEnum.TYPE_DOUBLE);
        typeMap.put(Types.INTEGER, JavaTypeEnum.TYPE_INTEGER);
        typeMap.put(Types.JAVA_OBJECT, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Jdbc4Types.LONGNVARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.LONGVARBINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.LONGVARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Jdbc4Types.NCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Jdbc4Types.NCLOB, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Jdbc4Types.NVARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.NULL, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.OTHER, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.REAL, JavaTypeEnum.TYPE_FLOAT);
        typeMap.put(Types.REF, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.SMALLINT, JavaTypeEnum.TYPE_SHORT);
        typeMap.put(Types.STRUCT, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.TIME, JavaTypeEnum.TYPE_DATE);
        typeMap.put(Types.TIMESTAMP, JavaTypeEnum.TYPE_TIMESTAMP);
        typeMap.put(Types.TINYINT, JavaTypeEnum.TYPE_BYTE);
        typeMap.put(Types.VARBINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.VARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.DECIMAL, JavaTypeEnum.TYPE_BIG_DECIMAL);
        typeMap.put(Types.NUMERIC, JavaTypeEnum.TYPE_BIG_DECIMAL);
    }

    /**
     * 获取java数据类型
     *
     * @param jdbcType       数据库类型
     * @param isFullJavaType 是否获取java类型的全路径
     * @return java数据类型全路径
     */
    public static String getJavaType(int jdbcType, boolean isFullJavaType) {
        return isFullJavaType ? typeMap.get(jdbcType).getFullName() : typeMap.get(jdbcType).getShortName();
    }
}
