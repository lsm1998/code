package com.oo.ui;

import com.oo.util.PathUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 作者：刘时明
 * 日期：2018/10/8
 * 时间：13:35
 * 说明：
 */
public class ooUI extends JFrame
{
    public ooUI()
    {
        setUndecorated(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(PathUtil.RESOURCES +"/img/tb.png");
        setIconImage(image);
        setSize(400, 300);
        setLocationRelativeTo(null);
        JLabel label = new JLabel(new ImageIcon(PathUtil.RESOURCES +"\\img\\11.jpg"));
        add(label);
        setVisible(true);
        try
        {
            Thread.sleep(1000 * 8);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        dispose();
    }
}
