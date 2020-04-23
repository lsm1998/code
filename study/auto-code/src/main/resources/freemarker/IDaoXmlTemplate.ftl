<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<#macro mapperEl value>${r"#{"}${value.javaProperty}, jdbcType=${value.jdbcTypeName}}</#macro>
<#macro mapperlistEl value>${r"#{item."}${value.javaProperty}, jdbcType=${value.jdbcTypeName}}</#macro>
<#assign dbKey="`id`">
<#assign javaKey="#\{item.id}">
<#assign javaKeyPrefix="#\{item.">

<#list table.columns as column>
    <#if column.primaryKey >
        <#assign dbKey="`${column.columnName}`"/>
        <#assign javaKey=javaKeyPrefix + "${column.javaProperty}}"/>
    </#if >
</#list>

<#assign idAnnotation="<!-- 如果id非自增长请手动添加id字段 -->" >
<mapper namespace="${packageName}.dao.I${entityName}Dao">
    <resultMap id="baseResultMap" type="${packageName}.vo.${entityName}VO">
<#list table.columns as column>
    <#if column.primaryKey  >
        <id column="${column.columnName}" property="${column.javaProperty}" jdbcType="${column.jdbcTypeName}" />
    <#else>
        <result column="${column.columnName}" property="${column.javaProperty}" jdbcType="${column.jdbcTypeName}" />
    </#if>
</#list>
    </resultMap>

    <sql id="base_query_sql">
        select
        <#list table.columns as column>
            ${table.tableAlias}.`${column.columnName}`<#if column_index != (table.columns?size - 1)>,</#if>
        </#list>
        from `${table.tableName}` ${table.tableAlias}
    </sql>

    <sql id="base_query_condition">
        <where>
        <#list table.columns as column>
            <if test="${column.javaProperty} != null and ${column.javaProperty} != ''">
                and ${table.tableAlias}.`${column.columnName}` = <@mapperEl column />
            </if>
        </#list>
        </where>
    </sql>

    <select id="findList" resultType="${packageName}.vo.${entityName}VO">
        <include refid="base_query_sql"></include>
        <include refid="base_query_condition"></include>
        order by ${table.tableAlias}.`last_updated_date` desc
    </select>

<#list table.primaryKeys as column>
    <select id="getBy${column.javaProperty?cap_first}" resultType="${packageName}.vo.${entityName}VO">
        <include refid="base_query_sql"></include>
        where ${table.tableAlias}.`${column.columnName}` = <@mapperEl column />
        order by ${table.tableAlias}.`last_updated_date` desc
    </select>
</#list>

	<insert id="createList" parameterType="${packageName}.vo.${entityName}VO">
		insert into
            `${table.tableName}`
		(
            ${idAnnotation}
		<#list table.columns as column>
            <#if !column.primaryKey  >
            `${column.columnName}`<#if column_index != (table.columns?size - 1)>,</#if>
            </#if>
		</#list>
		)
		values
        <foreach collection="list" item="item" open="" separator="," close="">
		(
            ${idAnnotation}
	    <#list table.columns as column>
            <#if !column.primaryKey  >
            <@mapperlistEl column /><#if column_index != (table.columns?size - 1)>,</#if>
            </#if>
		</#list>
		)
        </foreach>
	</insert>

    <update id="updateList" parameterType="${packageName}.vo.${entityName}VO">
        update
            `${table.tableName}`
        <trim prefix="set" suffixOverrides=",">
        <#list table.columns as column>
            <#if !column.primaryKey>
            <trim prefix="${column.columnName}=case" suffix="end,">
                <foreach collection="list" item="item" index="index">
                    <if test="item.${column.javaProperty} != null<#if column.javaType=='String'> and item.${column.javaProperty} != ''</#if>">
                        when ${dbKey} = ${javaKey} then <@mapperlistEl column/>
                    </if>
                </foreach>
            </trim>
            </#if>
        </#list>
        </trim>
        where
        <foreach collection="list" separator="or" item="item" index="index">
            `id`=  ${javaKey}
        </foreach>
    </update>

    <delete id="deleteList">
        delete from `${table.tableName}`
        <#list table.primaryKeys as column>
        where ${column.columnName} in
        <foreach collection="list" item="item" open="(" separator="," close=")">
            <@mapperlistEl column />

        </foreach>
        </#list>
    </delete>
</mapper>