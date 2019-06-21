package com.lsm1998.ibatis.annotations;

import java.lang.annotation.*;

/**
 * @作者：刘时明
 * @时间：2019/5/27-18:14
 * @作用：
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface MyMapper
{
}
