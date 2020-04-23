<#if (packageName)??>
package ${packageName}.service;
</#if>

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import ${packageName}.vo.${entityName}VO;
import com.deta.it.jeast.core.base.PagedResult;
import com.deta.it.jeast.core.exception.impl.ApplicationException;
import io.swagger.annotations.Api;

/**
 * ${table.tableName} - ${(table.remark)!''}
 * @author jeast-code
 * Date：${.now}
 */
@Api(value = "${entityName?uncap_first}", description = "${(table.remark)!''}")
@Path("/${entityName?uncap_first}")
@Produces(MediaType.APPLICATION_JSON)
public interface ${className} {

    /**
     * 根据取得${(table.remark)!''}列表
     * @param  ${entityName?uncap_first}
     * @return PagedResult
     * @throws ApplicationException
     */
    @ApiOperation("分页查询")
    @GET
    @Path("/list/page")
    PagedResult<${entityName}VO> findListPaged(@QueryParam("") ${entityName}VO ${entityName?uncap_first}) throws ApplicationException;

	/**
     * 根据id取得${(table.remark)!''}对象
     * @param  id
     * @return
     * @throws ApplicationException
     */
    @ApiOperation("单个查询")
    @GET
    @Path("/get/{id}")
    ${entityName}VO get${entityName}ById(@PathParam("id") <#list table.primaryKeys as column>${column.javaType} ${column.javaProperty}<#if column_index != (table.primaryKeys?size - 1)>,</#if></#list>) throws ApplicationException;
    
    /**
     * 保存${(table.remark)!''}对象
     * @param  ${entityName?uncap_first}
     * @throws ApplicationException
     */
    @ApiOperation("单个创建")
    @POST
    @Path("/create")
    void create${entityName}(${entityName}VO ${entityName?uncap_first}) throws ApplicationException;

    /**
     * 批量保存${(table.remark)!''}对象
     * @param  ${entityName?uncap_first}s
     * @throws ApplicationException
     */
    @ApiOperation("批量创建")
    @POST
    @Path("/list/create")
    void create${entityName}List(List<${entityName}VO> ${entityName?uncap_first}s) throws ApplicationException;
     
    /**
     * 更新${(table.remark)!''}对象
     * @param  ${entityName?uncap_first}
     * @throws ApplicationException
     */
    @ApiOperation("单个更新")
    @PUT
    @Path("/update")
    void update${entityName}(${entityName}VO ${entityName?uncap_first}) throws ApplicationException;

    /**
     * 批量更新${(table.remark)!''}对象
     * @param  ${entityName?uncap_first}s
     * @throws ApplicationException
     */
    @ApiOperation("批量更新")
    @PUT
    @Path("/list/update")
    void update${entityName}List(List<${entityName}VO> ${entityName?uncap_first}s) throws ApplicationException;

    /**
     * 根据id删除${(table.remark)!''}对象
     * @param  id
     * @throws ApplicationException
     */
    @ApiOperation("单个删除")
    @PUT
    @Path("/del/{id}")
    void del${entityName}ById(@PathParam("id") <#list table.primaryKeys as column>${column.javaType} ${column.javaProperty}<#if column_index != (table.primaryKeys?size - 1)>,</#if></#list>) throws ApplicationException;

    /**
     * 批量根据删除${(table.remark)!''}对象
     * @param  ${entityName?uncap_first}s
     * @throws ApplicationException
     */
    @ApiOperation("批量删除")
    @PUT
    @Path("list/del")
    void delete${entityName}List(List<${entityName}VO> ${entityName?uncap_first}s) throws ApplicationException;

    /**
     * 批量操作${(table.remark)!''}
     * @param batchVO
     * @throws ApplicationException
     */
    @ApiOperation("批量操作")
    @PUT
    @Path("/batch")
    void batchOperation(BatchVO<${entityName}VO> batchVO) throws ApplicationException;
}