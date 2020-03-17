/*
 * 作者：刘时明
 * 时间：2020/3/8-15:12
 * 作用：
 */
package com.lsm1998.echoes.registry.bean;

import com.lsm1998.echoes.registry.client.RegistryType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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
}
