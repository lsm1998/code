package com.lsm1998.echoes.common.domain;

import java.util.HashMap;

public class AjaxData extends HashMap<String, Object>
{
    public static final int ECHOES_CODE_OK = 200;

    public static final int ECHOES_CODE_CLIENT_ERR = 400;

    public static final int ECHOES_CODE_SERVER_ERR = 500;

    public AjaxData(int cap)
    {
        super(cap);
    }

    public static AjaxData error()
    {
        return of(ECHOES_CODE_SERVER_ERR, null);
    }

    public static AjaxData success(Object data)
    {
        return of(ECHOES_CODE_OK, null, data);
    }

    public static AjaxData success()
    {
        return of(ECHOES_CODE_OK, null);
    }

    public static AjaxData of(int code, String msg, Object data)
    {
        AjaxData result = new AjaxData(4);
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    public static AjaxData of(int code, String msg)
    {
        return of(code, msg, null);
    }
}
