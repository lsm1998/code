package com.lsm1998.util.json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;

public class JsonObject extends HashMap<String, Object>
{
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("{");
        boolean f = false;
        for (Entry<String, Object> entry : this.entrySet())
        {
            if (f) sb.append(',');
            sb.append(toJsonString(entry.getKey())).append(':').append(toJsonString(entry.getValue()));
            f = true;
        }
        return sb.append('}').toString();
    }


    public static String toJsonString(Object object)
    {
        if (object == null)
        {
            return "null";
        }
        if (object.getClass().isArray())
        {
            JsonArray jsonArray = new JsonArray();
            for (int i = 0; i < Array.getLength(object); i++)
            {
                jsonArray.add(Array.get(object, i));
            }
            return jsonArray.toString();
        }
        if (object instanceof Collection)
        {
            JsonArray jsonArray = new JsonArray();
            jsonArray.addAll((Collection<?>) object);
            return jsonArray.toString();
        }
        if (object instanceof String)
        {
            //给字符串加上双引号，字符串内有双引号的要用斜杠转义
            return "\"" + ((String) object).replace("\"", "\\\"") + "\"";
        }
        return object.toString();
    }
}
