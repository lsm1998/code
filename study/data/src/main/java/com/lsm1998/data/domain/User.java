package com.lsm1998.data.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@TableName("user")
public class User
{
    @TableId(type = AUTO)
    private Long id;
    private String name;
}
