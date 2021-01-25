package com.lsm1998.util.json;

public class JsonException extends RuntimeException
{
    public JsonException()
    {
    }

    public JsonException(String message)
    {
        super(message);
    }

    public JsonException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
