/**
 * 作者：刘时明
 * 时间：2021/4/13
 */
package com.lsm1998.db.bigData;

import org.jsoup.Jsoup;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Chapter implements Serializable
{
    public String title;
    public String content;

    public Chapter(String title, String content)
    {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString()
    {
        return this.title + " : " + this.content.length();
    }

    public static Chapter fromBodyText(String text) throws UnsupportedEncodingException
    {
        var str = new String(text.getBytes(), "utf-8");
        var doc = Jsoup.parse(str);
        var title = doc.select("b").first().text();
        var content = doc.select("pre").first().text();
        return new Chapter(title, content);
    }
}
