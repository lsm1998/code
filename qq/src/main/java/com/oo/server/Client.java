package com.oo.server;

import com.oo.domain.User;
import com.oo.ui.MainUI;
import com.oo.ui.ooUI;

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 作者：刘时明
 * 日期：2018/9/26
 * 时间：16:40
 * 说明：
 */
public class Client
{
    private User user;

    public Client(User user)
    {
        this.user = user;
        try
        {
            Socket s = new Socket(InetAddress.getLocalHost(), 8080);
            new ClientThread(s, user).start();
            File file = new File("temp.oo");
            if (!file.exists())
            {
                new ooUI();
                file.createNewFile();
            }
            new MainUI(user, s).setVisible(true);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
