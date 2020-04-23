package ${packageName}.controller;

import com.swiftelink.core.request.IUserPrincipal;
import com.swiftelink.core.annotation.JeastOperation;
import com.swiftelink.core.annotation.JeastResource;
import com.swiftelink.jeast.core.request.RequestContext;
import ${packageName}.domain.${entityName};
import ${packageName}.repository.${entityName}Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 * ${table.tableName} - ${(table.remark)!''}
 * @author jeast-code
 * Date：${.now}
 */
@RestController
@RequestMapping("/${entityName?uncap_first}")
@JeastResource(code = "${entityName?uncap_first}",desc = "${(table.remark)!''}")
@Slf4j
public class ${className} {

    @Autowired
    private ${entityName}Repository ${entityName?uncap_first}Repository;

    @GetMapping("list/page")
    @JeastOperation(code = "read",desc = "查询")
    public Page<${entityName}> findList(${entityName} ${entityName?uncap_first}, @RequestParam int page, @RequestParam int limit){
        Pageable pageable = new PageRequest(page-1, limit, new Sort(Sort.Direction.DESC, "creationDate") );
        return ${entityName?uncap_first}Repository.findAll(Example.of(${entityName?uncap_first}),pageable);
    }

    @GetMapping("")
    @JeastOperation(code = "read",desc = "查询")
    public ${entityName} findById(@RequestParam String id){
        return ${entityName?uncap_first}Repository.findOne(id);
    }

    @PostMapping("")
    @JeastOperation(code = "add",desc = "新增")
    @Transactional
    public ${entityName} add(@RequestBody ${entityName} ${entityName?uncap_first}) throws Exception {
        String operaUser = RequestContext.getCurrent().getUser().getUsername();
        ${entityName?uncap_first}.setCreatedBy(operaUser);
        ${entityName?uncap_first}.setCreationDate(new Date());
        return ${entityName?uncap_first}Repository.save(${entityName?uncap_first});
    }

    @PutMapping("")
    @JeastOperation(code = "update",desc = "更新")
    @Transactional
    public ${entityName} update(@RequestBody ${entityName} ${entityName?uncap_first}) {
        ${entityName} target${entityName} = ${entityName?uncap_first}Repository.findOne(${entityName?uncap_first}.getId());
        BeanUtils.copyProperties(${entityName?uncap_first},target${entityName},"createdBy","creationDate");
        String operaUser = RequestContext.getCurrent().getUser().getUsername();
        Date currDate = new Date();
        ${entityName?uncap_first}.setLastUpdatedBy(operaUser);
        ${entityName?uncap_first}.setLastUpdatedDate(currDate);
        return ${entityName?uncap_first}Repository.save(target${entityName});
    }

    @DeleteMapping("")
    @JeastOperation(code = "delete",desc = "删除")
    public void delete(@RequestParam String id){
        ${entityName?uncap_first}Repository.delete(id);
    }
}
