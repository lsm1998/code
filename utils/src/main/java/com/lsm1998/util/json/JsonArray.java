package com.lsm1998.util.json;

import java.util.ArrayList;

public class JsonArray extends ArrayList<Object>
{
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        boolean f = false;
        for (Object object : this)
        {
            if (f) sb.append(',');
            sb.append(JsonObject.toJsonString(object));
            f = true;
        }
        return sb.append(']').toString();
    }
}
