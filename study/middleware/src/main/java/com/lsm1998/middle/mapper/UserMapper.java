/**
 * 作者：刘时明
 * 时间：2021/2/24
 */
package com.lsm1998.middle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsm1998.middle.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>
{
    List<User> findAll();
}
