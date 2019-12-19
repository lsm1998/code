package com.lsm1998.tomcat.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnknownContentTypeException extends RuntimeException
{
    public UnknownContentTypeException(String message)
    {
        super(message);
    }
}
