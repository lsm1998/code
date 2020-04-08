/*
 * 作者：刘时明
 * 时间：2020/4/9-0:20
 * 作用：
 */
package com.lsm1998.tomcat.servlet;

import java.io.IOException;

/**
 * ServletRequest顶层接口
 */
public interface ServletRequest
{
    Object getAttribute(String var1);

    String getCharacterEncoding();

    int getContentLength();

    String getContentType();

    ServletInputStream getInputStream() throws IOException;

    String getParameter(String var1);
}
