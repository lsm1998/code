<script type="text/javascript" src="${entityName}List.js"></script>

<div id="${entityName?uncap_first}Tabs" class="easyui-tabs">
    <div title="列表">
        <form id="${entityName?uncap_first}ListForm">
            <div class="jeast-form-body">
                <ul>
                    <li class="jeast-form-row">
                        <samp>查询条件</samp>
                        <span class="jeast-form-input">
							<input name="property" class="easyui-textbox"/>
						</span>
                    </li>
                </ul>

                <div class="jeast-button-panel">
                    <a href="javascript:void(0);" class="easyui-linkbutton jeast-form-query"
                       data-options="iconCls:'icon-search'">查询</a>
                    <a href="javascript:" class="easyui-linkbutton jeast-form-clear" data-options="iconCls:'icon-clear'"
                       onclick="${projectCode}.${entityName?uncap_first}.List.reset()">重置</a>
                </div>
            </div>
        </form>

        <div class="jeastgrid-button-panel">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
               onclick="${projectCode}.${entityName?uncap_first}.List.doAdd()">新增</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
               onclick="${projectCode}.${entityName?uncap_first}.List.doDel()">删除</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
               onclick="${projectCode}.${entityName?uncap_first}.List.doSave()">保存</a>
        </div>
        <div id="${entityName?uncap_first}ListGrid" style="height:560px"></div>
    </div>
</div>