<#if (packageName)??>
package ${packageName}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ${packageName}.domain.${entityName};
</#if>

/**
 * ${table.tableName} - ${(table.remark)!''}
 */
@Repository
public interface ${entityName}Repository extends JpaRepository<${entityName}, String>, JpaSpecificationExecutor<${entityName}> {
}
