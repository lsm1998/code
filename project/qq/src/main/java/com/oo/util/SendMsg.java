package com.oo.util;

import java.io.Serializable;

import javax.swing.text.StyledDocument;

import com.oo.domain.User;

public class SendMsg implements Serializable
{
    // 密令
    public int cmd;
    // 发送者
    public User myInfo;
    // 接受者
    public User friendInfo;
    // 文档
    public StyledDocument doc;
    // 字节数组
    public byte b[];
    // 文件名称
    public String fileName;
}
