/*
 * 作者：刘时明
 * 时间：2020/3/8-15:13
 * 作用：
 */
package com.lsm1998.echoes.registry.bean;

import com.lsm1998.echoes.registry.client.RegistryType;
import lombok.Getter;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodReport that = (MethodReport) o;
        return Objects.equals(generic, that.generic) &&
                Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(generic, clazz);
    }
}
