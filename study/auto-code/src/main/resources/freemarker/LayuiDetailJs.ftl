function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
layui.use(['jquery','form','layedit','laydate', 'util'], function(){
    var $ = layui.jquery
        ,$T = top.layui.jquery
        ,form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate
        ,jeast_page = top.layui.jeast_page
        ,util = layui.util;
    var ${table.primaryKeys[0].javaProperty} = getQueryString('${table.primaryKeys[0].javaProperty}');
    var ${entityName?uncap_first}Data = {};

    var initData = function(){
        if(!${table.primaryKeys[0].javaProperty}){
            return false;
        }
        $.getJSON('/${entityName?uncap_first}',{id:${table.primaryKeys[0].javaProperty}},function (data) {
            ${entityName?uncap_first}Data = data;
            form.val('${entityName?uncap_first}-detail',data);
        });
    }

    initData();

    form.on('submit(${entityName?uncap_first}-detail)', function(data){
        var jsonData = data.field;
        jsonData.${table.primaryKeys[0].javaProperty} = ${table.primaryKeys[0].javaProperty}?${table.primaryKeys[0].javaProperty}:jsonData.${table.primaryKeys[0].javaProperty};
        console.info(jsonData);
        $.ajax({
            type: ${table.primaryKeys[0].javaProperty}?"PUT":"POST",
            url: '/${entityName?uncap_first}',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(jsonData),
            dataType: "json",
            success: function (data) {
                console.info(data);
                top.layer.msg('操作成功');
                $T('.weIframe').each(function (i,iframe) {
                    if(iframe.src.includes('aitcp/${entityName?uncap_first}/${entityName}List.html')){
                        iframe.contentWindow.location.reload();
                        return false;
                    }
                });
                $T('.layui-tab .layui-this .layui-tab-close').click();
            },
            error: function (data) {
                console.info(data);
            }
        });
        return false;
    });

    $('.timeSpace').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type: 'datetime'
        });
    });
});
