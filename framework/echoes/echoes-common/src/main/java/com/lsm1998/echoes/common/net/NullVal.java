package com.lsm1998.echoes.common.net;

import java.io.Serializable;

public class NullVal implements Serializable
{
    public static final NullVal Instance = new NullVal();

    private NullVal()
    {
    }
}
