package com.lsm1998.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsm1998.data.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>
{

}
