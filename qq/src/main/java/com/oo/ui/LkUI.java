package com.oo.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 作者：刘时明
 * 日期：2018/9/30 0030
 * 时间：23:04
 * 说明：
 */
public class LkUI extends JFrame
{
    private Icon icon;
    private Icon icon1;
    private Icon icon2;
    private Icon icon3;

    public LkUI()
    {
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        icon = new ImageIcon("src\\main\\resources\\img\\按钮背景.png");
        icon1 = new ImageIcon("src\\main\\resources\\img\\log.png");
        icon2 = new ImageIcon("src\\main\\resources\\img\\1.jpg");
        icon3 = new ImageIcon("src\\main\\resources\\img\\3.png");
        JPanel jp = new JPanel();
        jp.setBounds(0, 0, 900, 600);
        jp.setLayout(null);
        JLabel lb = new JLabel("查找");
        JLabel tp = new JLabel(icon2);
        JLabel tp2 = new JLabel(icon3);
        JLabel log = new JLabel(icon1);
        JLabel lb2 = new JLabel("找人");
        JLabel lb3 = new JLabel("性别：");
        JLabel lb4 = new JLabel("年龄");
        JComboBox cb = new JComboBox();
        JComboBox cb2 = new JComboBox();
        JTextField t = new JTextField();
        JButton jb = new JButton(icon);
        String[] strings=new String[100];
        for (int i=0;i<strings.length;i++)
        {
            strings[i]="haha"+i;
        }
        JList ja = new JList(strings);
        tp.setBounds(0, 0, 900, 240);
        tp2.setBounds(0, 130, 900, 110);
        ja.setBounds(0, 240, 900, 360);
        t.setBounds(30, 100, 800, 30);
        t.setOpaque(false);
        cb.addItem("男");
        cb.addItem("女");
        cb2.addItem("0-18");
        cb2.addItem("19-25");
        cb2.addItem("26-45");
        cb2.addItem("45以上");
        cb.setBackground(Color.white);
        cb2.setBackground(Color.white);
        lb.setFont(new Font("黑体", Font.BOLD, 15));
        lb.setForeground(Color.white);
        t.setFont(new Font("仿宋", Font.BOLD, 20));
        lb2.setFont(new Font("黑体", Font.BOLD, 36));
        lb3.setFont(new Font("黑体", Font.BOLD, 15));
        lb4.setFont(new Font("黑体", Font.BOLD, 15));
        cb.setBounds(140, 150, 60, 35);
        cb2.setBounds(440, 150, 90, 35);
        lb.setBounds(20, 0, 50, 20);
        lb2.setBounds(410, 30, 100, 50);
        lb3.setBounds(90, 145, 70, 50);
        lb4.setBounds(390, 145, 70, 50);
        jb.setBounds(640, 146, 117, 40);
        log.setBounds(0, 0, 20, 20);
        tp.add(t);
        tp.add(lb);
        tp.add(lb2);
        tp.add(log);
        tp.add(cb);
        tp.add(cb2);
        tp.add(lb3);
        tp.add(lb4);
        tp.add(jb);
        jp.add(tp);
        jp.add(ja);
        this.add(jp);
    }
}