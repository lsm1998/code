package com.lsm1998.auto.engine;

public enum  EngineTypeEnum {

    JPA_ENTITY_JAVA("JAPEntityTemplate.ftl","","",".java"),
    JPA_REPOSITORY_JAVA("JpaRepositoryTemplate.ftl","","Repository",".java"),
    JPA_CONTROLLER_JAVA("JpaControllerTemplate.ftl","","Controller",".java"),
    LAYUI_LIST_HTML("LayuiListHtml.ftl","","List",".html"),
    LAYUI_LIST_JS("LayuiListJs.ftl","","List",".js"),
    LAYUI_DETAIL_HTML("LayuiDetailHtml.ftl","","Detail",".html"),
    LAYUI_DETAIL_JS("LayuiDetailJs.ftl","","Detail",".js"),
    ENTITY_JAVA("EntityTemplate.ftl","","VO",".java"),
    I_SERVICES_JAVA("IServiceTemplate.ftl","I","Service",".java"),
    SERVICES_JAVA("ServiceTemplate.ftl","","Service",".java"),
    I_DAO_JAVA("IDaoTemplate.ftl","I","Dao",".java"),
    I_DAO_XML("IDaoXmlTemplate.ftl","I","Dao",".xml"),
    LIST_HTML("ListHtmlTemplate.ftl","","List",".html"),
    LIST_JS("ListJsTemplate.ftl","","List",".js");

    EngineTypeEnum(String templateName, String prefix, String suffix, String extension) {
        this.templateName = templateName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.extension = extension;
    }

    private String templateName;

    private String prefix;

    private String suffix;

    private String extension;

    public String getClassName(String entityName){
        StringBuffer className = new StringBuffer();
        className.append(prefix);
        className.append(entityName);
        className.append(suffix);
        return className.toString();
    }

    public String getFileName(String entityName){
        StringBuffer fileName = new StringBuffer();
        fileName.append(getClassName(entityName));
        fileName.append(extension);
        return fileName.toString();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }}
