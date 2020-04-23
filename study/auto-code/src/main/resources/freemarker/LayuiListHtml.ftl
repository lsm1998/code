<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${(table.remark)!''}列表</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <link rel="stylesheet" href="../../lib/layui/css/layui.css">
    </head>
    <body>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>查询条件</legend>
    </fieldset>
    <form class="layui-form" action="javascript:void(0);">
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
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="${entityName?uncap_first}">查询</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>结果列表</legend>
    </fieldset>
    <table id="${entityName?uncap_first}ListGrid" class="layui-table" lay-filter="${entityName?uncap_first}">
    </table>

    <script type="text/html" id="${entityName?uncap_first}Toolbar">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="add" title="新增"><i class="layui-icon layui-icon-add-1"></i></button>
        </div>
    </script>

    <script type="text/html" id="${entityName?uncap_first}Bar">
        <a class="layui-btn layui-btn-xs" lay-event="edit" title="编辑"><i class="layui-icon layui-icon-edit"></i></a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" title="删除"><i class="layui-icon layui-icon-delete"></i></a>
    </script>

    <script src="../../lib/layui/layui.js" charset="utf-8"></script>
    <script src="${entityName}List.js" type="text/javascript" charset="utf-8"></script>
    </body>
</html>
