package com.oo.ui;

import com.oo.domain.Group;
import com.oo.domain.User;
import com.oo.mapper.GroupMapper;
import com.oo.mapper.UserMapper;
import com.oo.server.App;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.*;

public class RegisterUI extends JFrame
{
    private Icon icon;
    private TextField t;
    private JPasswordField t2;
    private JPasswordField t3;
    UserMapper userMapper = App.context.getBean(UserMapper.class);
    GroupMapper groupMapper=App.context.getBean(GroupMapper.class);

    public RegisterUI()
    {
        icon = new ImageIcon("src\\main\\resources\\img\\头像1.jpg");
        this.setSize(560, 620);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        JPanel p = new JPanel();
        JLabel blb = new JLabel(icon);
        p.setLayout(null);
        p.setBackground(Color.WHITE);
        JLabel lb = new JLabel("欢迎注册OO");
        JLabel lb2 = new JLabel("每一天，乐在沟通。");
        JLabel lb3 = new JLabel("呢称：");
        JLabel lb4 = new JLabel("密码：");
        JLabel lb5 = new JLabel("确认密码：");
        t = new TextField();
        t2 = new JPasswordField();
        t3 = new JPasswordField();
        t.setBackground(Color.PINK);
        t2.setBackground(Color.PINK);
        t3.setBackground(Color.PINK);
        JButton jb = new JButton("立即注册");
        jb.addActionListener(e -> Register());
        lb.setFont(new Font("黑体", Font.BOLD, 42));
        lb2.setFont(new Font("黑体", Font.PLAIN, 30));
        lb3.setFont(new Font("黑体", Font.BOLD, 23));
        lb4.setFont(new Font("黑体", Font.BOLD, 23));
        lb5.setFont(new Font("黑体", Font.BOLD, 23));
        jb.setFont(new Font("黑体", Font.BOLD, 23));
        jb.setForeground(Color.WHITE);
        jb.setBackground(Color.pink);
        lb3.setForeground(Color.black);
        lb4.setForeground(Color.black);
        lb5.setForeground(Color.black);
        t.setFont(new Font("Calibri", Font.PLAIN, 32));
        t2.setFont(new Font("Calibri", Font.PLAIN, 32));
        t3.setFont(new Font("Calibri", Font.PLAIN, 32));
        lb.setBounds(5, 20, 300, 50);
        lb2.setBounds(20, 80, 300, 50);
        lb3.setBounds(50, 175, 80, 50);
        lb4.setBounds(50, 270, 80, 50);
        lb5.setBounds(20, 365, 120, 50);
        t.setBounds(150, 175, 350, 45);
        t2.setBounds(150, 270, 350, 45);
        t3.setBounds(150, 365, 350, 45);
        jb.setBounds(80, 470, 400, 50);
        blb.setBounds(0, 0, 560, 620);
        blb.add(lb2);
        blb.add(lb);
        blb.add(lb3);
        blb.add(lb4);
        blb.add(lb5);
        blb.add(t);
        blb.add(t2);
        blb.add(t3);
        blb.add(jb);
        p.add(blb);
        this.add(p);
    }

    private void Register()
    {
        String pass1 = new String(t2.getPassword());
        String pass2 = new String(t3.getPassword());
        String nickName = t.getText();
        if ("".equals(pass1.trim()) || "".equals(pass2.trim()) || "".equals(nickName.trim()))
        {
            JOptionPane.showMessageDialog(null, "请将内容输入完整");
        } else
        {
            if (pass1.equals(pass2))
            {
                User user = new User();
                File file=new File("src\\main\\resources\\img\\defaultHeadimg.png");
                try(FileInputStream fis=new FileInputStream(file))
                {
                    byte[] b=new byte[(int)file.length()];
                    fis.read(b);
                    user.setHead_img(b);
                }catch (Exception e)
                {
                    System.out.println();
                }
                user.setNickName(nickName);
                user.setAutoGraph("暂无签名");
                user.setPassWord(pass1);
                long accNumber = userMapper.getAccNumber() + 10000;
                user.setAccNumber(accNumber);
                userMapper.saveUser(user);
                Group group=new Group();
                group.setAccNumber(user.getAccNumber());
                group.setGroupName("好友");
                groupMapper.saveGroup(group);
                JOptionPane.showMessageDialog(null, "注册成功，你的账号是：" + accNumber);
                t.setText("");
            } else
            {
                JOptionPane.showMessageDialog(null, "两次密码输入不一致");
            }
            t2.setText("");
            t3.setText("");
        }
    }
}
