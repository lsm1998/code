package com.lsm1998.ibatis.auto;

import com.lsm1998.ibatis.annotation.*;
import com.lsm1998.ibatis.builder.MyAnnotationConfigBuilder;
import com.lsm1998.ibatis.util.MyFieldUtil;
import com.lsm1998.ibatis.util.MySQLUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import static com.lsm1998.ibatis.enums.AutoIncrement.TRUE;

/**
 * @作者：刘时明
 * @时间：18-12-24-下午4:43
 * @说明：
 */
@Slf4j
public class AutoTables
{
    private String dir;

    public AutoTables()
    {
        this.dir = MyAnnotationConfigBuilder.SEPARATE;
        scanPath("src" + dir + "main" + dir + "java" + dir);
    }

    private void scanPath(String path)
    {
        File[] files = new File(path).listFiles((dir, src) -> dir.isDirectory() || src.endsWith(".java"));
        if (files != null)
        {
            for (File f : files)
            {
                if (f.isDirectory())
                {
                    scanPath(f.getAbsolutePath());
                } else
                {
                    loadEntry(f.getAbsolutePath());
                }
            }
        }else
        {
            // 获取路径出问题
            String classpath = AutoTables.class.getClassLoader().getResource("").getPath();
            classpath = classpath.substring(0, classpath.length() - 15) + "src" + MyAnnotationConfigBuilder.SEPARATE + "main" + MyAnnotationConfigBuilder.SEPARATE + "java";
            scanPath(classpath);
        }
    }

    private void loadEntry(String path)
    {
        int index = path.indexOf("src" + dir + "main" + dir + "java" + dir);
        int lastIndex = path.lastIndexOf(".");
        Class<?> clazz;
        try
        {
            clazz = Class.forName(path.substring(index + 14, lastIndex).replace(dir, "."));
        } catch (ClassNotFoundException e)
        {
            log.error("路径找不到:{}",path.substring(index + 14, lastIndex).replace(dir, "."));
            return;
        }
        if (clazz.isAnnotationPresent(MyEntry.class) && clazz.isAnnotationPresent(MyTable.class))
        {
            loadEntry(clazz);
        }
    }

    private void loadEntry(Class<?> clazz)
    {
        log.info("找到实体:{}",clazz);
        MyTable table = clazz.getAnnotation(MyTable.class);
        String tableName = table.name();

        if (MySQLUtil.tableIsExis(tableName))
        {
            Map<String, String[]> map = MySQLUtil.getTableStructure(tableName);
            Set<String> fieldSet = new HashSet<>();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields)
            {
                if (f.isAnnotationPresent(MyNotColumn.class))
                {
                    continue;
                }
                    String fieldName;
                    if (f.isAnnotationPresent(MyColumn.class))
                    {
                        MyColumn myColumn = f.getAnnotation(MyColumn.class);
                        fieldName = myColumn.value();
                        if (fieldName.equals(""))
                        {
                            fieldName = f.getName();
                        }
                    } else
                    {
                        fieldName = f.getName();
                    }
                    StringBuilder sql = new StringBuilder();
                    boolean isId = f.isAnnotationPresent(MyId.class);
                    boolean isIncrement = false;
                    if (isId)
                    {
                        MyId myId = f.getAnnotation(MyId.class);
                        isIncrement = myId.value() == TRUE;
                    }
                    if (map.containsKey(fieldName))
                    {
                        sql.append("ALTER TABLE " + tableName + " MODIFY ");
                        String type = MyFieldUtil.getDefultType(f);
                        int len = MyFieldUtil.getDefultLen(f);
                        boolean isUnique = false;
                        boolean isNullable = false;
                        if (f.isAnnotationPresent(MyColumn.class))
                        {
                            MyColumn myColumn = f.getAnnotation(MyColumn.class);
                            if (!myColumn.type().equals(""))
                            {
                                type = myColumn.type();
                            }
                            if (myColumn.length() != -1)
                            {
                                len = myColumn.length();
                            }
                            isUnique = myColumn.unique();
                            isNullable = myColumn.nullable();
                        }

                        if (type.equals(map.get(fieldName)[0]) && len == Integer.parseInt(map.get(fieldName)[1]))
                        {
                            log.info("与原表一致");
                        } else
                        {
                            String exeSql = MySQLUtil.generateSQL(sql, fieldName, type, len, isUnique, isNullable, false, false);
                            if (isIncrement)
                            {
                                exeSql = exeSql + " auto_increment";
                            }
                            log.info("与原表不一致，需要更新，SQL语句:{}" , exeSql);
                            boolean result = MySQLUtil.exeSql(exeSql);
                            if(!result)
                            {
                                log.error("更新失败");
                            }
                        }
                    } else
                    {
                        // ALTER TABLE [表名] ADD [字段名] NVARCHAR (50) NULL
                        sql.append("ALTER TABLE " + tableName + " ADD ");
                        String type;
                        boolean isUnique;
                        boolean isNullable;
                        int len;
                        if (f.isAnnotationPresent(MyColumn.class))
                        {
                            MyColumn myColumn = f.getAnnotation(MyColumn.class);
                            isUnique = myColumn.unique();
                            isNullable = myColumn.nullable();
                            type = myColumn.type();
                            if (type.equals(""))
                            {
                                type = MyFieldUtil.getDefultType(f);
                            }
                            len = myColumn.length();
                            if (len == -1)
                            {
                                len = MyFieldUtil.getDefultLen(f);
                            }
                        } else
                        {
                            isUnique = false;
                            isNullable = false;
                            len = MyFieldUtil.getDefultLen(f);
                            type = MyFieldUtil.getDefultType(f);
                        }
                        String exeSql = MySQLUtil.generateSQL(sql, fieldName, type, len, isUnique, isNullable, isId, isIncrement);
                        log.info("新增字段，SQL语句{}", exeSql);
                        try
                        {
                            MySQLUtil.exeSql(exeSql);
                        } catch (Exception e)
                        {
                            MySQLUtil.exeSql("ALTER TABLE " + tableName + " DROP COLUMN " + fieldName);
                            MySQLUtil.exeSql(exeSql);
                        }
                    }
                    fieldSet.add(fieldName);
            }
            // 删除没有映射的字段
            for (String s : map.keySet())
            {
                if (!fieldSet.contains(s))
                {
                    log.info("{},没有映射，需要删除",s);
                    MySQLUtil.exeSql("ALTER TABLE " + tableName + " DROP COLUMN " + s);
                }
            }
        } else
        {
            log.info("开始新建表:{}" , tableName);
            StringBuilder sql = new StringBuilder();
            sql.append("create table " + tableName + "(");
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields)
            {
                if (!f.isAnnotationPresent(MyNotColumn.class))
                {
                    boolean isId = false;
                    boolean isIncrement = true;
                    boolean isUnique = false;
                    boolean isNullable = false;
                    String value;
                    String type;
                    int len = -1;
                    if (f.isAnnotationPresent(MyId.class))
                    {
                        isId = true;
                        MyId myId = f.getAnnotation(MyId.class);
                        isIncrement = myId.value() == TRUE;
                    }
                    if (f.isAnnotationPresent(MyColumn.class))
                    {
                        MyColumn myColumn = f.getAnnotation(MyColumn.class);
                        isUnique = myColumn.unique();
                        isNullable = myColumn.nullable();
                        value = myColumn.value();
                        len = myColumn.length();
                        if (len == -1)
                        {
                            len = MyFieldUtil.getDefultLen(f);
                        }
                        if (value.equals(""))
                        {
                            value = f.getName();
                        }
                        type = myColumn.type();
                        if("".equals(type))
                        {
                            type=MyFieldUtil.getDefultType(f);
                        }
                    } else
                    {
                        value = f.getName();
                        type = MyFieldUtil.getDefultType(f);
                    }
                    sql.append(MySQLUtil.generateSQL(new StringBuilder(), value, type, len, isUnique, isNullable, isId, isIncrement) + ",");
                }
            }
            List<String> list = MySQLUtil.getIndexNameList(fields);

            for (String str : list)
            {
                sql.append("index(" + str + "),");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(")character set utf8");
            MySQLUtil.exeSql(sql.toString());
            log.info("建表SQL:{}" , sql);
        }
    }
}
