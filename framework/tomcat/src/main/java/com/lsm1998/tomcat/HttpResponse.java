package com.lsm1998.tomcat;


import java.io.IOException;
import java.io.OutputStream;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 封装HTTP响应
 *
 * @author nmyphp
 */
@Data
@Slf4j
public class HttpResponse
{
    private OutputStream outputStream;
    private StatusCode statusCode;
    private ContentType contentType;
    private String charsetName = "UTF-8";

    public HttpResponse(OutputStream outputStream, ContentType contentType)
    {
        this.outputStream = outputStream;
        this.contentType = contentType;
    }

    public HttpResponse(OutputStream outputStream, ContentType contentType, String charsetName)
    {
        this.outputStream = outputStream;
        this.contentType = contentType;
        this.charsetName = charsetName;
    }

    public void write(String content) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        builder.append(statusCode)
                .append("\n")
                .append(contentType)
                .append(";charset=")
                .append(charsetName)
                .append("\n\n");
        if (contentType == ContentType.TEXT)
        {
            builder.append("<html><body>")
                    .append(content)
                    .append("</body></html>");
        } else if (contentType == ContentType.JSON)
        {
            builder.append(content);
        }

        outputStream.write(builder.toString().getBytes());
        outputStream.flush();
        log.debug("Response: \n\t{}", builder.toString());
    }
}
