package com.oo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.oo.domain.User;
import com.oo.mapper.UserMapper;
import com.oo.server.App;
import com.oo.server.Client;
import com.oo.util.PathUtil;

/**
 * 作者：刘时明
 * 日期：2018/9/24
 * 时间：23:16
 * 说明：登录页面
 */
public class LoginUI extends JFrame implements MouseListener, ActionListener, ItemListener, MouseMotionListener
{
    private JLabel lblMin, lblClose, lblHead, lblReg, lblForgetPwd;
    private JButton btnLogin;
    private JPasswordField txtPassword;
    private JComboBox<Object> userNamecb;
    private JCheckBox cbpwd;
    private HashMap<String, User> user = null;
    private JLabel bg, cbAutoLogin;
    private UserMapper userMapper = App.context.getBean(UserMapper.class);

    public LoginUI()
    {
        setUndecorated(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(PathUtil.RESOURCES + "/img/tb.png");
        setIconImage(image);
        bg = new JLabel(new ImageIcon(PathUtil.RESOURCES + "/img/loginUI3.png"));
        bg.setLayout(null);
        bg.addMouseMotionListener(this);
        add(bg);
        lblMin = new JLabel("-");
        lblMin.setForeground(Color.WHITE);
        lblMin.setFont(new Font("黑体", Font.BOLD, 20));
        lblClose = new JLabel("x");
        lblClose.setForeground(Color.WHITE);
        lblClose.setFont(new Font("黑体", Font.BOLD, 25));
        lblMin.setBounds(379, 0, 20, 26);
        lblClose.setBounds(405, -2, 20, 20);
        lblMin.addMouseListener(this);
        lblClose.addMouseListener(this);
        bg.add(lblMin);
        bg.add(lblClose);
        lblHead = new JLabel(new ImageIcon(PathUtil.RESOURCES + "/img/tx.png"));
        lblReg = new JLabel("注册账号");
        lblForgetPwd = new JLabel("忘记密码");
        userNamecb = new JComboBox<>();
        txtPassword = new JPasswordField();
        btnLogin = new JButton(new ImageIcon(PathUtil.RESOURCES + "/img/LoginButton.png"));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        cbpwd = new JCheckBox("记住密码");
        cbAutoLogin = new JLabel("清除记录");

        userNamecb.setBounds(105, 160, 220, 35);
        userNamecb.setEditable(true);
        userNamecb.setToolTipText("账号");
        txtPassword.setBounds(105, 195, 220, 35);
        txtPassword.setToolTipText("密码");

        lblReg.setBounds(12, 290, 80, 30);

        JLabel code = new JLabel(new ImageIcon("src\\main\\resources\\img\\acount1.png"));
        code.setBounds(85, 170, 15, 15);
        bg.add(code);
        JLabel pass = new JLabel(new ImageIcon("src\\main\\resources\\img\\password.png"));
        pass.setBounds(85, 205, 15, 15);
        bg.add(pass);

        lblForgetPwd.setBounds(290, 235, 80, 30);
        lblHead.setBounds(182, 76, 65, 65);
        btnLogin.setBounds(110, 270, 200, 30);
        btnLogin.setBackground(Color.LIGHT_GRAY);
        cbpwd.setBounds(100, 235, 80, 30);
        cbAutoLogin.setBounds(200, 235, 80, 30);

        cbpwd.setOpaque(false);
        cbAutoLogin.setOpaque(false);
        btnLogin.setOpaque(false);

        bg.add(userNamecb);
        bg.add(txtPassword);
        bg.add(lblReg);
        bg.add(lblForgetPwd);
        bg.add(lblHead);
        bg.add(btnLogin);
        bg.add(cbpwd);
        bg.add(cbAutoLogin);

        lblReg.addMouseListener(this);
        lblForgetPwd.addMouseListener(this);
        cbAutoLogin.addMouseListener(this);
        cbpwd.addActionListener(this);
        btnLogin.addActionListener(this);
        userNamecb.addItemListener(this);

        readFile();
        setSize(430, 330);
        setLocationRelativeTo(null);
    }

    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource() == lblMin)
        {
            this.setState(JFrame.HIDE_ON_CLOSE);
        } else if (e.getSource() == lblClose)
        {
            System.exit(0);
        } else if (e.getSource() == lblReg)
        {
            // 注册页面
            new RegisterUI().setVisible(true);
        } else if (e.getSource() == cbAutoLogin)
        {
            File file = new File("users.dat");
            if (file.length() > 0)
                file.delete();
            user.clear();
            txtPassword.setText("");
            userNamecb.setSelectedItem("");
            JOptionPane.showMessageDialog(null, "清楚记录");
        }
    }

    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    public void mouseExited(MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e)
    {
        x = this.getX();
        y = this.getY();
    }

    public void mouseReleased(MouseEvent e)
    {

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnLogin)
        {
            String userName;
            char[] c = txtPassword.getPassword();
            String pwd = new String(c);
            if (userNamecb.getSelectedItem() == null)
            {
                JOptionPane.showMessageDialog(this, "请输入用户名");
                return;
            }
            if (pwd.equals(""))
            {
                JOptionPane.showMessageDialog(this, "请输入登录密码");
                return;
            }
            userName = userNamecb.getSelectedItem().toString();
            User temp = new User();
            temp.setAccNumber(Long.parseLong(userName));
            temp.setPassWord(pwd);
            User user = userMapper.login(temp);
            if (user == null)
            {
                JOptionPane.showMessageDialog(this, "登录失败,账号或登录密错误!");
                return;
            } else
            {
                if (cbpwd.isSelected())
                {
                    save(user);
                }
                this.setVisible(false);
                this.dispose();
                // 主页面
                System.out.println("登录成功");
                new Client(user);
            }
        } else if (e.getSource() == cbpwd)
        {
            JOptionPane.showMessageDialog(null, "记住密码");
        }
    }

    public void save(User users)
    {
        HashMap<String, User> user;
        File file = new File("users.dat");
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
                user = new HashMap<>();
            } else
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                user = (HashMap<String, User>) ois.readObject();
                fis.close();
                ois.close();
            }
            user.put(users.getAccNumber() + "", users);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"));
            oos.writeObject(user);
            oos.flush();
            oos.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void readFile()
    {
        try
        {
            File file = new File("users.dat");
            if (!file.exists())
            {
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (HashMap<String, User>) ois.readObject();
            Set<String> set = user.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext())
            {
                userNamecb.addItem(it.next());
            }
            ois.close();
            fis.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() == userNamecb)
        {
            if (!userNamecb.getSelectedItem().toString().equals("") && user != null)
            {
                String username = userNamecb.getSelectedItem().toString();
                User users = user.get(username);
                if (users != null)
                {
                    txtPassword.setText(users.getPassWord());
                }
            }
        }
    }

    int x, y;

    public void mouseDragged(MouseEvent e)
    {
        Point p = this.getLocationOnScreen();
        this.setLocation(e.getX() + p.x, e.getY() + p.y);
    }

    public void mouseMoved(MouseEvent e)
    {

    }
}
