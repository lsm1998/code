package com.lsm1998.util.xml;

import com.lsm1998.util.file.MyFiles;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @作者：刘时明
 * @时间：2019/6/18-11:26
 * @作用：XML相关工具类
 */
public class MyXmlUtil
{
    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static Document parserByDom(File file) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        return document;
    }

    public static Document parserByDom(String xmlPath) throws ParserConfigurationException, SAXException, IOException
    {
        File file = new File(xmlPath);
        if (MyFiles.checkRead(file))
        {
            return parserByDom(file);
        }
        return null;
    }

    public static Document parserByDom(File file,Class c) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        return document;
    }
}
