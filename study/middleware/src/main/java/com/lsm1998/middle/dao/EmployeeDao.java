/**
 * 作者：刘时明
 * 时间：2021/3/26
 */
package com.lsm1998.middle.dao;

import com.lsm1998.middle.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EmployeeDao extends ElasticsearchRepository<Employee, String>
{
}
