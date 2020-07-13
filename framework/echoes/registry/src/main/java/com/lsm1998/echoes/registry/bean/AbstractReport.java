/*
 * 作者：刘时明
 * 时间：2020/3/8-15:14
 * 作用：
 */
package com.lsm1998.echoes.registry.bean;

import com.lsm1998.echoes.registry.client.RegistryType;

public abstract class AbstractReport implements ReportData
{
    private String uid;

    public AbstractReport(String uid)
    {
        this.uid = uid;
    }

    @Override
    public String getType()
    {
        switch (type())
        {
            case METHOD:
                return "method";
            case APPLICATION:
                return "app";
        }
        return null;
    }

    @Override
    public String getId()
    {
        return uid;
    }

    public abstract RegistryType type();
}
