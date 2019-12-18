

/**
 * 作者：易可
 * 日期：2018/9/29
 * 时间：9:25
 * 说明：
 */
package com.oo.ui;

import com.oo.domain.User;
import com.oo.mapper.UserMapper;
import com.oo.server.App;
import com.oo.util.ImgUtil;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.*;

public class MyInfoUI
{
    private User user;
    private JTextField textName;
    private JComboBox<Character> textSex;
    private JTextArea textArea;
    private JComboBox<Object> textD;
    private JComboBox<Object> textDay;
    private JComboBox<Object> textM;
    private UserMapper userMapper = App.context.getBean(UserMapper.class);
    private JPasswordField passwordField;
    private MainUI mainUI;

    public MyInfoUI(User user, MainUI mainUI)
    {
        this.user = user;
        this.mainUI = mainUI;
        JFrame w = new JFrame();
        w.setTitle("编辑资料");
        w.setSize(330, 550);
        w.setLocationRelativeTo(null);
        JLabel zc = new JLabel(new ImageIcon("src\\main\\resources\\img\\9.png"));

        //JPanel zc = new JPanel();
        zc.setLayout(null);
        JLabel labName = new JLabel("昵  称  ");
        labName.setFont(new Font("幼圆", 0, 12));
        labName.setBounds(20, 5, 100, 30);
        textName = new JTextField(20);
        textName.setBounds(80, 5, 110, 30);
        zc.add(labName);
        textName.setText(user.getNickName());
        zc.add(textName);

        JLabel labSex = new JLabel("性  别  ");
        labSex.setFont(new Font("幼圆", 0, 12));
        labSex.setBounds(20, 45, 100, 30);
        textSex = new JComboBox();
        if (user.getSex() == '女')
        {
            textSex.addItem('女');
            textSex.addItem('男');
        } else
        {
            textSex.addItem('男');
            textSex.addItem('女');
        }
        System.out.println(user.getSex());
        textSex.setFont(new Font("幼圆", 0, 12));
        textSex.setBounds(80, 45, 110, 30);
        zc.add(labSex);
        zc.add(textSex);

        JLabel labDay = new JLabel("生  日  ");
        labDay.setFont(new Font("幼圆", 0, 12));
        labDay.setBounds(20, 85, 100, 30);
        zc.add(labDay);

        Object[] arr1 = new Object[101];
        for (int i = 1; i < 100; i++)
        {
            arr1[i] = i + 1950;
        }
        arr1[0] = "无";
        textDay = new JComboBox(arr1);
        textDay.setFont(new Font("幼圆", 0, 12));
        textDay.setBounds(80, 85, 60, 30);
        zc.add(textDay);
        Object[] arr2 = new Object[13];
        for (int i = 1; i < 13; i++)
        {
            arr2[i] = i;
        }
        arr2[0] = "无";
        textM = new JComboBox(arr2);
        textM.setFont(new Font("幼圆", 0, 12));
        textM.setBounds(145, 85, 40, 30);
        zc.add(textM);
        Object[] arr3 = new Object[32];
        for (int i = 1; i < 32; i++)
        {
            arr3[i] = i;
        }
        arr3[0] = "无";
        textD = new JComboBox(arr3);


        textD.setFont(new Font("幼圆", 0, 12));
        textD.setBounds(185, 85, 40, 30);
        zc.add(textD);
        zc.setBounds(300, 500, 200, 80);
        JLabel labgxqm = new JLabel("个 性 签 名");
        labgxqm.setFont(new Font("幼圆", 0, 12));
        textArea = new JTextArea(10, 50);
        if (user.getAutoGraph() != null)
            textArea.setText(user.getAutoGraph());
        textArea.setFont(new Font("幼圆", 0, 12));
        labgxqm.setBounds(20, 125, 100, 30);
        textArea.setBounds(101, 125, 180, 90);
        zc.add(labgxqm);
        zc.add(textArea);
        String str = user.getBirthDay();
        if (str != null && str.split("-").length == 3)
        {
            int i1 = Integer.parseInt(str.split("-")[0]);
            int i2 = Integer.parseInt(str.split("-")[1]);
            int i3 = Integer.parseInt(str.split("-")[2]);
            textDay.setSelectedIndex(i1 - 1950);
            textM.setSelectedIndex(i2);
            textD.setSelectedIndex(i3);
        }
        JLabel labtx = new JLabel("修 改 头 像");
        labtx.setFont(new Font("幼圆", 0, 12));
        labtx.setBounds(20, 245, 100, 30);
        zc.add(labtx);

        ImageIcon icon1 = new ImageIcon(user.getHead_img());
        JButton tx1 = new JButton(icon1);
        tx1.setIcon(icon1);
        tx1.setBounds(101, 265, 60, 60);
        zc.add(tx1);
        JLabel passlbl = new JLabel("密码");
        passwordField = new JPasswordField();

        passlbl.setBounds(31, 370, 50, 30);
        passwordField.setBounds(121, 370, 100, 30);
        passwordField.setText(user.getPassWord());
        zc.add(passlbl);
        zc.add(passwordField);

        JButton tx2 = new JButton("上传");
        tx2.addActionListener(e ->
        {
            JFileChooser dlg = new JFileChooser();
            dlg.setDialogType(JFileChooser.OPEN_DIALOG);
            dlg.setFileSelectionMode(JFileChooser.FILES_ONLY);
            dlg.setDialogTitle("更换皮肤");
            if (dlg.showOpenDialog(w) == JFileChooser.APPROVE_OPTION)
            {
                File file = dlg.getSelectedFile();
                String path = file.getPath();
                int index = path.lastIndexOf(".");
                String type = path.substring(index);
                switch (type)
                {
                    case ".jpg":
                    case ".png":
                    case ".jpeg":
                        ImgUtil.changeImg(path, "temp" + type, 60);
                        File file1 = new File("temp" + type);
                        try (FileInputStream fis = new FileInputStream(file1))
                        {
                            byte[] b = new byte[(int) file1.length()];
                            fis.read(b);
                            user.setHead_img(b);
                            tx1.setIcon(new ImageIcon(user.getHead_img()));
                            tx1.repaint();
                        } catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "只支持jpg、png、jpeg类型");
                        break;
                }
            }
        });
        tx2.setBounds(170, 265, 60, 30);
        zc.add(tx2);

        JButton bc = new JButton("保存");
        bc.setFont(new Font("幼圆", 0, 12));
        bc.setBackground(Color.PINK);
        bc.setForeground(Color.WHITE);
        bc.setBounds(31, 445, 100, 30);
        zc.add(bc);
        bc.addActionListener(e -> save());
        JButton gb = new JButton("关闭");
        gb.addActionListener(e -> w.dispose());
        gb.setFont(new Font("幼圆", 0, 12));
        gb.setBackground(Color.PINK);
        gb.setForeground(Color.WHITE);
        gb.setBounds(171, 445, 100, 30);

        zc.add(gb);
        w.add(zc);
        w.setVisible(true);
    }

    private void save()
    {
        user.setNickName(textName.getText().trim());
        user.setSex(textSex.getItemAt(textSex.getSelectedIndex()));
        user.setAutoGraph(textArea.getText());
        String str = textDay.getItemAt(textDay.getSelectedIndex()) + "-" + textM.getItemAt(textM.getSelectedIndex()) + "-" + textD.getItemAt(textD.getSelectedIndex());
        try
        {
            int age = 2018 - Integer.parseInt(textDay.getItemAt(textDay.getSelectedIndex()).toString());
            user.setAge((byte) age);
            user.setBirthDay(str);
        }catch (Exception e)
        {
            System.out.println("缺少出生信息");
            JOptionPane.showMessageDialog(null,"请早日完善资料");
        }
        user.setPassWord(new String(passwordField.getPassword()));
        userMapper.updateUser(user);
        JOptionPane.showMessageDialog(null, "信息修改成功！");
        mainUI.lblmyInfo.setIcon(new ImageIcon(user.getHead_img()));
        mainUI.lblmyInfo.setText(user.getNickName() + "[" + user.getAutoGraph() + "]");
    }
}
