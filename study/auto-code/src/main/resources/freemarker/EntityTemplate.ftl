<#if (packageName)??>
package ${packageName}.vo;
</#if>

import com.deta.it.jeast.core.base.BaseResourceVO;
/**
 * ${entityName}VO
 * <p>Table: <strong>${table.tableName}</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 <#list table.columns as column>
 *   <tr><td>${column.javaProperty}</td><td>{@link ${column.javaType}}</td><td>${column.columnName}</td><td>${column.jdbcTypeName}</td><td>${(column.remark)!''}</td></tr>
 </#list>
 * </table>
 *
 */
public class ${entityName}VO extends BaseResourceVO {
<#assign filterField = ["createdBy", "creationDate", "lastUpdatedBy", "lastUpdatedDate"]>
 	<#list table.columns as column>
    <#if !filterField?seq_contains(column.javaProperty) >
    private ${column.javaType} ${column.javaProperty};
    </#if>
 	</#list>

 	<#list table.columns as column>
 	<#if !filterField?seq_contains(column.javaProperty) >
	/**
     * 获取${(column.remark)!''}
     */
	public ${column.javaType} get${column.javaProperty?cap_first}(){
		return this.${column.javaProperty};
	}

	/**
     * 设置${(column.remark)!''}
     */
	public void set${column.javaProperty?cap_first}(${column.javaType} ${column.javaProperty}){
		this.${column.javaProperty} = ${column.javaProperty};
	}

	</#if>
 	</#list>
 }