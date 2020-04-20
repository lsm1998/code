/*
 * 作者：刘时明
 * 时间：2020/3/8-15:12
 * 作用：
 */
package com.lsm1998.net.echoes.registry.bean;

import com.lsm1998.net.echoes.registry.client.RegistryType;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
public class AppReport extends AbstractReport
{
    private String address;
    private int port;

    public AppReport(String address, int port)
    {
        super(UUID.randomUUID().toString());
        this.address = address;
        this.port = port;
    }

    @Override
    public RegistryType type()
    {
        return RegistryType.APPLICATION;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppReport appReport = (AppReport) o;
        return port == appReport.port && Objects.equals(address, appReport.address);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(address, port);
    }
}
