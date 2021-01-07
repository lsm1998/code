package com.lsm1998.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @program: code
 * @description: 实体类
 * @author: lsm
 * @create: 2020-04-09 11:23
 **/
@Data
@Builder
public class Hello
{
    private Long id;
    private String name;
}
