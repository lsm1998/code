package com.github.nmyphp.mytomcat.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnkownContentTypeException extends RuntimeException {

    public UnkownContentTypeException(String message) {
        super(message);
    }
}
