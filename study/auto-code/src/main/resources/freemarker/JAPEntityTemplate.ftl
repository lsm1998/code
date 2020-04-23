package com.swiftelink.${entityName}.domain;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
* Table:${table.tableName} - ${(table.remark)!''}
*/
@Entity
@Table(name="${table.tableName}")
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicUpdate
@ApiModel("${(table.remark)!''}")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Builder
public class ${entityName} implements Serializable {
<#list table.columns as column>
<#if (column.primaryKey?string('yes', 'no'))=='yes'>
    /**
     * ${(column.remark)!''}
     */
    @Id
    @Column(name = "${column.columnName}" , length = ${column.size?c})
    @GeneratedValue(generator = "jpa-uuid")
    private ${column.javaType} ${column.javaProperty};

<#else >
    /**
     * ${(column.remark)!''}
     */
    <#if column.jdbcType == 12>
    @Column(name = "${column.columnName}" , length = ${column.size?c})
    <#elseif column.jdbcType == 3>
    @Column(name = "${column.columnName}" , precision = ${column.size?c} , scale = ${column.decimalDigits})
    <#elseif column.jdbcType = -1>
    @Column(name = "${column.columnName}", columnDefinition = "text")
    <#elseif column.jdbcType = 93>
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "${column.columnName}")
    <#else >
    @Column(name = "${column.columnName}")
    </#if>
    <#if column.javaType == 'Timestamp'>
    private Date ${column.javaProperty};
    <#else >
    private ${column.javaType} ${column.javaProperty};
    </#if>

</#if>
</#list>
}
