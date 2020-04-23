package com.lsm1998.auto.engine;

import com.lsm1998.auto.database.DataBaseFactory;
import com.lsm1998.auto.database.IDataBase;
import com.lsm1998.auto.database.enums.ErrorEnum;
import com.lsm1998.auto.database.model.Table;
import com.lsm1998.auto.database.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * 驱动模板接口
 * 目前仅实现freemarker模板
 */
public class EngineTemplate {

    /**
     * freemarker配置对象实例化
     * 采用2.3.28版本
     */
    private Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

    /**
     * 默认字符集
     */
    static final String DEFAULT_ENCODING = "UTF-8";
    /**
     * 默认国际化
     */
    static final String DEFAULT_LOCALE = "zh_CN";

    /**
     * 设置freemarker全局配置参数
     */
    {
        try {
            configuration.setDirectoryForTemplateLoading(new File(getEngineTemplatePath()));
            configuration.setDefaultEncoding(DEFAULT_ENCODING);
            configuration.setLocale(new Locale(DEFAULT_LOCALE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CodeBuilderProperties codeBuilderProperties;

    private IDataBase dataBase;

    public EngineTemplate(CodeBuilderProperties codeBuilderProperties) {
        this.codeBuilderProperties = codeBuilderProperties;
        // 获取数据库对象
        dataBase = DataBaseFactory.newInstance(codeBuilderProperties.getDbConfig());
    }

    /**
     * 开始构建
     */
    public void builder(){
        this.loopProcess(this.getTables());
    }

    /**
     * 循环执行生成文件
     *
     * @param tableNames 表名列表
     */
    public void loopProcess(List<String> tableNames){
        for (String tableName : tableNames) {
            Table table = dataBase.getTable(tableName);
            System.out.println("Auto Builder Table > 【{"+tableName+"}】");
            process(table,EngineTypeEnum.JPA_ENTITY_JAVA);
            process(table,EngineTypeEnum.JPA_REPOSITORY_JAVA);
            process(table,EngineTypeEnum.JPA_CONTROLLER_JAVA);
            process(table,EngineTypeEnum.LAYUI_LIST_HTML);
            process(table,EngineTypeEnum.LAYUI_LIST_JS);
            process(table,EngineTypeEnum.LAYUI_DETAIL_HTML);
            process(table,EngineTypeEnum.LAYUI_DETAIL_JS);
            /*process(table,EngineTypeEnum.ENTITY_JAVA);
            process(table,EngineTypeEnum.I_SERVICES_JAVA);
            process(table,EngineTypeEnum.SERVICES_JAVA);*/
            process(table,EngineTypeEnum.I_DAO_JAVA);
            process(table,EngineTypeEnum.I_DAO_XML);
            /*process(table,EngineTypeEnum.LIST_HTML);
            process(table,EngineTypeEnum.LIST_JS);*/
        }
    }

    /**
     * 单个数据表生成文件
     *
     * @param table 数据表对象
     */
    public void process(Table table,EngineTypeEnum engineTypeEnum){
        try {
            String entityName = getEntityName(table.getTableName());
            String fileName = engineTypeEnum.getFileName(entityName);

            Template template = configuration.getTemplate(engineTypeEnum.getTemplateName());
            File file = new File(getProjectOutPath(fileName));
            // 写入freemarker模板内容
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), DEFAULT_ENCODING));

            DataModelEntity dataModelEntity = DataModelEntity.builder()
                    .projectCode(codeBuilderProperties.getProjectCode())
                    .packageName(codeBuilderProperties.getPackageName())
                    .entityName(entityName)
                    .className(engineTypeEnum.getClassName(entityName))
                    .table(table)
                    .build();

            // 执行生成
            template.process(dataModelEntity, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getTables(){
        List<String> tables = codeBuilderProperties.getTables();
        String generatorByPattern = codeBuilderProperties.getGeneratorByPattern();
        /*
         * 根据配置tables参数表名进行构建生成
         * 优先级高于generatorByPattern
         */
        if (null != tables && tables.size() > 0) {
            System.out.println("Using table name to generate code automatically, please wait...");
            return tables;
        }
        /*
         * 如果配置generatorByPattern参数，优先级高于tables
         */
        else if (StringUtil.isNotEmpty(generatorByPattern)) {
            System.out.println("Using expression method to generate code automatically, please wait...");
            return dataBase.getTableNames(generatorByPattern);
        }
        throw new RuntimeException(ErrorEnum.NO_BUILDER_TABLE.getMessage());
    }

    private String getEntityName(String tableName){
        String entityName = tableName;
        String ignorePrefix = codeBuilderProperties.getIgnoreClassPrefix();
        String ignoreSuffix = codeBuilderProperties.getIgnoreClassSuffix();
        if(StringUtil.isNotEmpty(ignorePrefix) && entityName.startsWith(ignorePrefix)){
            entityName = entityName.replaceFirst(ignorePrefix, "");
        }
        if(StringUtil.isNotEmpty(ignoreSuffix) && entityName.endsWith(ignoreSuffix)){
            entityName = entityName.substring(0,entityName.lastIndexOf(ignoreSuffix));
        }
        entityName = StringUtil.getCamelCaseString(entityName,true);
        return entityName;
    }

    private String getProjectBaseDir()
    {
        File file = new File(this.getClass().getName());
        String absolutePath = file.getAbsolutePath();
        StringBuffer baseTagerDir = new StringBuffer();
        baseTagerDir.append(absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)));
        baseTagerDir.append(File.separator);
        baseTagerDir.append("src");
        baseTagerDir.append(File.separator);
        return baseTagerDir.toString();
    }

    private String getProjectOutPath(String fileName)
    {
        return getProjectBaseDir() + "items" + File.separator + fileName;
    }

    private String getEngineTemplatePath()
    {
        try
        {
            return EngineTemplate.class.getResource("/freemarker").getFile();
        } catch (Exception var3)
        {
            var3.printStackTrace();
            return null;
        }
    }
}
