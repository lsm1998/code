<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${(table.remark)!''}详情</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <link rel="stylesheet" href="../../lib/layui/css/layui.css">
    </head>
    <body>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>${(table.remark)!''}详情</legend>
    </fieldset>
    <form class="layui-form" lay-filter="${entityName?uncap_first}-detail" action="javascript:void(0);" onsubmit="return false;">
        <div class="layui-form-item">
    <#list table.columns as column>
        <#if (!column.primaryKey && column.javaProperty != "creationDate" && column.javaProperty != "createdBy" && column.javaProperty != "lastUpdatedDate" && column.javaProperty != "lastUpdatedBy") >
        <#if column.javaType == 'Timestamp' >
            <div class="layui-inline">
                <label class="layui-form-label">${(column.remark)!''}</label>
                <div class="layui-input-inline">
                    <input type="text" name="${column.javaProperty}" lay-verify="" autocomplete="off" class="layui-input timeSpace">
                </div>
            </div>
        <#else >
            <div class="layui-inline">
                <label class="layui-form-label">${(column.remark)!''}</label>
                <div class="layui-input-inline">
                    <input type="text" name="${column.javaProperty}" lay-verify="" autocomplete="off" class="layui-input">
                </div>
            </div>
        </#if>
        </#if>
    </#list>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="${entityName?uncap_first}-detail">提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>

    <script src="../../lib/layui/layui.js" charset="utf-8"></script>
    <script src="${entityName}Detail.js" type="text/javascript" charset="utf-8"></script>
    </body>
</html>
