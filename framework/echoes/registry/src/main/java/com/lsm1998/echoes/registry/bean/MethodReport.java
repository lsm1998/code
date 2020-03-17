/*
 * 作者：刘时明
 * 时间：2020/3/8-15:13
 * 作用：
 */
package com.lsm1998.echoes.registry.bean;

import com.lsm1998.echoes.registry.client.RegistryType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MethodReport extends AbstractReport
{
    private String generic;
    private String clazz;

    public MethodReport(String generic, String clazz)
    {
        super(UUID.randomUUID().toString());
        this.generic = generic;
        this.clazz = clazz;
    }

    @Override
    public RegistryType type()
    {
        return RegistryType.METHOD;
    }

    @Override
    public String getAddress()
    {
        return null;
    }

    @Override
    public int getPort()
    {
        return 0;
    }
}
