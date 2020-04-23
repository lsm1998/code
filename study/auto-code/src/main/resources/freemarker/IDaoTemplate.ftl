<#if (packageName)??>
package ${packageName}.dao;
</#if>
import java.util.List;
import ${packageName}.vo.${entityName}VO;

/**
 * ${table.tableName} - ${(table.remark)!''}
 */
public interface I${entityName}Dao {

    List<${entityName}VO> findList(${entityName}VO ${entityName?uncap_first}VO);

    <#list table.primaryKeys as column>
    /**
    * 根据id查询单个实体${(table.remark)!''}
    */
    ${entityName}VO getBy${column.javaProperty?cap_first}(${column.fullJavaType} ${column.javaProperty});
    </#list>

    /**
    * 批量创建实体${(table.remark)!''}
    */
    void createList(@Param("list") List<${entityName}VO> ${entityName?uncap_first}VO);

    /**
    * 批量更新实体${(table.remark)!''}
    */
    void updateList(@Param("list") List<${entityName}VO> ${entityName?uncap_first}VO);

    /**
    * 批量删除实体${(table.remark)!''}
    */
	void deleteList(@Param("list") List<${entityName}VO> ${entityName?uncap_first}VO);
}