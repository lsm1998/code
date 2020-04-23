package ${packageName}.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import org.apache.commons.collections4.ListUtils;
import com.deta.it.jeast.core.base.PagedResult;
import com.deta.it.jeast.core.ioc.ISelfInject;
import com.deta.it.jeast.core.log.Logger;
import com.deta.it.jeast.core.exception.impl.ApplicationException;
import com.deta.it.jeast.core.exception.impl.ExceptionHandler;
import com.deta.it.jeast.core.annotation.JeastOperation;
import com.deta.it.jeast.core.annotation.JeastResource;
import ${packageName}.vo.${entityName}VO;

/**
 * ${table.tableName} - ${(table.remark)!''}
 * @author jeast-code
 * Date：${.now}
 */
@Named
@JeastResource(code="${projectCode}.${entityName}", desc="${projectCode} ${entityName}")
public class ${className} implements I${entityName}Service, ISelfInject {

    private static Logger logger = Logger.getInstance(${className}.class);

    @Inject
	private I${entityName}Dao ${entityName?uncap_first}Dao;

    private I${entityName}Service self;

    /**
     * 根据取得${(table.remark)!''}列表
     * @param  ${entityName?uncap_first}
     * @return PagedResult
     * @throws ApplicationException
     */
    @Override
    @JeastOperation(code="findPaged", desc="find paged")
    public PagedResult<${entityName}VO> findListPaged(${entityName}VO ${entityName?uncap_first}) throws ApplicationException {
        PageHelper.startPage(${entityName?uncap_first}.getPage(), ${entityName?uncap_first}.getRows());
        List<${entityName}VO> resultList = ${entityName?uncap_first}Dao.findList(${entityName?uncap_first});
        return new PagedResult<${entityName}VO>(resultList);
    }

    /**
     * 根据id取得${(table.remark)!''}
     * @param id
     * @return ${entityName}VO
     * @throws ApplicationException
     */
    @Override
    @JeastOperation(code="find", desc="find")
    public ${entityName}VO get${entityName}ById(<#list table.primaryKeys as column>${column.javaType} ${column.javaProperty}<#if column_index != (table.primaryKeys?size - 1)>,</#if></#list>) throws ApplicationException {
        return ${entityName?uncap_first}Dao.getById(id);
    }
    
    /**
     * 保存${(table.remark)!''}
     * @param ${entityName?uncap_first}
     * @throws ApplicationException
     */
     @Override
     @JeastOperation(code="create", desc="create")
     public void create${entityName}(${entityName}VO ${entityName?uncap_first}) throws ApplicationException {
        this.create${entityName}List(Arrays.asList(${entityName?uncap_first}));
     }

     /**
      * 批量保存${(table.remark)!''}
      * @param ${entityName?uncap_first}s
      * @throws ApplicationException
      */
     @Override
     @JeastOperation(code="create", desc="create")
     public void create${entityName}List(List<${entityName}VO> ${entityName?uncap_first}s) throws ApplicationException{
        ListUtils.partition(${entityName?uncap_first}s,100).forEach(${entityName?uncap_first}Dao::createList);
     }
     
     /**
      * 更新${(table.remark)!''}
      *
      * @param ${entityName?uncap_first}
      * @throws ApplicationException
      */
     @Override
     @JeastOperation(code="update", desc="update")
     public void update${entityName}(${entityName}VO ${entityName?uncap_first}) throws ApplicationException {
        this.update${entityName}List(Arrays.asList(${entityName?uncap_first}));
     }

     /**
      * 批量更新${(table.remark)!''}
      * @param ${entityName?uncap_first}s
      * @throws ApplicationException
      */
     @Override
     @JeastOperation(code="update", desc="update")
     public void update${entityName}List(List<${entityName}VO> ${entityName?uncap_first}s) throws ApplicationException{
        ListUtils.partition(${entityName?uncap_first}s,100).forEach(${entityName?uncap_first}Dao::updateList);
     }

     /**
      * 根据id删除${(table.remark)!''}
      * @param id
      * @throws ApplicationException
      */
     @Override
     @JeastOperation(code="delete", desc="delete")
     public void del${entityName}ById(<#list table.primaryKeys as column>${column.javaType} ${column.javaProperty}<#if column_index != (table.primaryKeys?size - 1)>,</#if></#list>) throws ApplicationException {
        ${entityName}VO ${entityName} = new ${entityName}VO();
        <#list table.primaryKeys as column>
        ${entityName}.set${column.javaProperty?cap_first}(${column.javaProperty});
        </#list>
        this.delete${entityName}List(Arrays.asList(${entityName}));
     }

     /**
      * 批量根据删除${(table.remark)!''}
      * @param ${entityName?uncap_first}s
      * @throws ApplicationException
      */
     @Override
     @JeastOperation(code="delete", desc="delete")
     public void delete${entityName}List(List<${entityName}VO> ${entityName?uncap_first}s) throws ApplicationException{
        ListUtils.partition(${entityName?uncap_first}s,100).forEach(${entityName?uncap_first}Dao::deleteList);
     }

     /**
      * 批量操作${(table.remark)!''}
      * @param batchVO
      * @throws ApplicationException
      */
     @Override
     public void batchOperation(BatchVO<${entityName}VO> batchVO) throws ApplicationException{
        if (!CollectionUtils.isEmpty(batchVO.getItemsCreate())) {
            self.create${entityName}List(batchVO.getItemsCreate());
        }
        if (!CollectionUtils.isEmpty(batchVO.getItemsUpdate())) {
            self.update${entityName}List(batchVO.getItemsUpdate());
        }
        if (!CollectionUtils.isEmpty(batchVO.getItemsDelete())) {
            self.delete${entityName}List(batchVO.getItemsDelete());
        }
     }

     @Override
     public void setSelf(Object self) {
         this.self = (I${entityName}Service) self;
     }
}