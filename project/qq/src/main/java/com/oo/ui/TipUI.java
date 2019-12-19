package com.oo.ui;

import com.oo.domain.User;

import javax.swing.*;
import java.awt.*;

/**
 * 作者：刘时明
 * 日期：2018/10/3
 * 时间：13:11
 * 说明：
 */
public class TipUI extends JFrame
{
    JLabel myLabel;

    public TipUI(User myInfo)
    {
        setUndecorated(true);
        getContentPane().setBackground(Color.PINK);
        String str = myInfo.getNickName() + "(" + myInfo.getAccNumber() + ")";
        if (myInfo.getFlag() == 1)
        {
            str += "上线了";
        } else
        {
            str += "下线了";
        }
        myLabel = new JLabel(str, new ImageIcon(myInfo.getHead_img()), JLabel.RIGHT);

        add(myLabel);
        setAlwaysOnTop(true);
        setSize(200, 100);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = toolkit.getScreenSize().width - 200;
        int height = toolkit.getScreenSize().height;
        setVisible(true);
        for (int i = 0; i < 100; i++)
        {
            setLocation(width, height - i);
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        for (int i = 100; i > 0; i--)
        {
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        dispose();
    }
}
