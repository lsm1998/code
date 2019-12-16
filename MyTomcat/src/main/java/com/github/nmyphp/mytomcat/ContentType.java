package com.github.nmyphp.mytomcat;

import com.github.nmyphp.mytomcat.exception.UnkownContentTypeException;

public enum ContentType {
    TEXT("text/html"),
    JSON("application/json");

    public static final String ANY_TYPE = "*/*";
    private String type;

    ContentType(String type) {
        this.type = type;
    }

    public static ContentType match(String originType) {
        String[] types = originType.split(",");
        if (types.length > 0) {
            for (String type : types) {
                if (type.startsWith(ANY_TYPE)) {
                    return TEXT;
                }
                for (ContentType contentType : ContentType.values()) {
                    if (contentType.type.equalsIgnoreCase(type)) {
                        return contentType;
                    }
                }
            }
        }
        throw new UnkownContentTypeException("Unkown content-type:" + originType);
    }

    @Override
    public String toString() {
        return "Content-Type: " + type;
    }
}
