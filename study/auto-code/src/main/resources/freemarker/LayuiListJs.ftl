layui.use(['jquery','form','layedit','laydate','table'], function(){
    var $ = layui.jquery
        ,form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate
        ,table = layui.table
        ,jeast_page = top.layui.jeast_page;

    var gridOption = {
        elem:'#${entityName?uncap_first}ListGrid',
        url:'/${entityName?uncap_first}/list/page',
        toolbar:'#${entityName?uncap_first}Toolbar',
        page:true,
        cols:[[
            {fixed: 'left', title:'操作', toolbar: '#${entityName?uncap_first}Bar', width:120},
            <#list table.columns as column>
            {field:'${column.javaProperty}', width:120, sort: true,title:'${(column.remark)!''}'}<#if column_index != (table.columns?size - 1)>,</#if>
            </#list>
        ]],
        done : function () {
            top.layui.jeast_core.ready();
        }
    };

    table.render(gridOption);

    var filterNullProps = function(params){
        let pam = {}
        for(let i in params){
            if(params[i]){
                pam[i] = trans[i]
            }
        }
        return pam;
    }

    form.on('submit(${entityName?uncap_first})', function(data) {
        var jsonData = data.field;
        var params = $.param(filterNullProps(jsonData));
        if(!params){
            gridOption.url = '/${entityName?uncap_first}/list/page';
        }else{
            gridOption.url = '/${entityName?uncap_first}/list/page?'+$.param(jsonData);
        }
        table.render().reload(gridOption);
        return false;
    });

    table.on('toolbar(${entityName?uncap_first})',function (obj) {
        switch(obj.event){
            case 'add':
                jeast_page._forward('aitcp/${entityName?uncap_first}/${entityName}Detail.html','${(table.remark)!''}新增');
                break;
        };
    });

    table.on('tool(${entityName?uncap_first})', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('确认删除？',{
                btn: ['删除','取消']
            },function () {
                $.ajax({
                    type: "DELETE",
                    url: '/${entityName?uncap_first}?id='+data.${table.primaryKeys[0].javaProperty},
                    contentType: "application/json; charset=utf-8",
                    data: null,
                    success: function (data) {
                        console.info(data);
                        $('button[type=submit][lay-filter=${entityName?uncap_first}]').click();
                    },
                    error: function (data) {
                        console.info(data);
                    }
                });
                layer.msg('操作成功');
            });
        } else if(obj.event === 'edit'){
            jeast_page._forward('aitcp/${projectCode}/${entityName}Detail.html?${table.primaryKeys[0].javaProperty}='+data.${table.primaryKeys[0].javaProperty},'${(table.remark)!''}编辑');
        }
    });

    $('.timeSpace').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type: 'datetime'
        });
    });

});
