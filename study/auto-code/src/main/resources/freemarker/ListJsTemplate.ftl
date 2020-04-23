Jeast.Module.define('${projectCode}.${entityName?uncap_first}.List',function( page, $S ){

    page.currentGrid = $('#${entityName?uncap_first}ListGrid');

    page.currentForm = $S('#${entityName?uncap_first}ListForm');

	page.ready = function(){
		$('#${entityName?uncap_first}Tabs').jeastTabs();
		page.initGrid();
	};

	page.initGrid = function() {
		var gridDataGrid = {
			queryButton: '.jeast-form-query',
			queryForm: '#${entityName?uncap_first}ListForm',
			url: 'services/*/${entityName?uncap_first}/list/page',
			width: '100%',
			loadMsg: '加载中...',
			striped: true,
			pagination: true,
			queryParams: {},
			method: 'get',
			rownumbers: true,
			singleSelect: false,
			columns: [[
			<#list table.columns as column>
				{field: '${column.javaProperty}', title: '${(column.remark)!''}', width: 150, align: 'center', editor: {type: 'text'}}<#if column_index != (table.columns?size - 1)>,</#if>
			</#list>
			]]
		};
        page.currentGrid.jeastGrid(gridDataGrid);
	};


	//查询方法
	page.query = function(){
		var jsonData = page.currentForm.jsonData();
		page.currentGrid.datagrid('load',jsonData);
	};

	//新增方法
	page.doAdd = function(){
        page.currentGrid.datagrid('appendRow', {'id':page.id});
	};

	//删除方法
	page.doDel = function(){
		//删除时先获取选择行
		var delRow = page.currentGrid.datagrid('getSelections') || [];
		if(delRow.length == 0){
			$.messager.alert('提示', '请选择要删除的行', 'error');
			return false;
		}

		$.messager.confirm('确认', '你确定要删除吗?', function (flag) {
			if (flag) {
				Jeast.doPut('services/*/${entityName?uncap_first}/list/del',delRow,function(){
					$.messager.alert('提示','删除成功！','info');
					page.query();
				});
			}
		});
	};

	//保存方法
	page.doSave = function(){
		var batchVO = {};
		var itemsCreate = page.currentGrid.datagrid('getChanges','inserted') || [];
		var itemsUpdate = page.currentGrid.datagrid('getChanges','updated')  || [];
		if(itemsCreate.length==0 && itemsUpdate.length ==0){
			$.messager.alert('提示','没有数据可以保存！','warning');
			return false;
		}

		batchVO.itemsCreate = itemsCreate;
		batchVO.itemsUpdate = itemsUpdate;

		if(itemsCreate != null || itemsUpdate != null){
			// check
		}
		Jeast.doPut('services/*/${entityName?uncap_first}/batch',batchVO,function(){
			$.messager.alert('提示','保存成功！','info');
			page.query();
		});
	};

	//重置方法
	page.reset = function(){
        page.currentForm.form('reset');
	};

});