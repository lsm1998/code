package com.oo.ui;

import com.oo.domain.User;
import com.oo.mapper.UserMapper;
import com.oo.server.App;
import com.oo.util.Cmd;
import com.oo.util.SendCmd;
import com.oo.util.SendMsg;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者：刘时明
 * 日期：2018/10/3
 * 时间：12:21
 * 说明：
 */
public class ChatUI extends JFrame implements ActionListener, ItemListener
{
    private JLabel title;
    JTextPane txtReceive, txtSend;
    private JButton btnSend, btnClose;
    private JButton btnShake, btnFile, btnColor, btnFace;
    private JComboBox cbFont, cbSize;
    private User myInfo, friendInfo;
    private List<User> allList;
    private String sFont[] = {"宋体", "黑体", "楷体", "隶书"};
    private String sSize[] = {"8", "10", "12", "14", "16", "18", "24", "28", "32", "36", "72"};
    private Font font;
    private boolean isAll;
    private JTextArea jTextArea;
    private UserMapper userMapper = App.context.getBean(UserMapper.class);

    public ChatUI(User myInfo, User friendInfo, List<User> allList, boolean isAll)
    {
        String str;
        if (isAll)
        {
            str = myInfo.getNickName() + "的群聊...";
            System.out.println(myInfo);
            setIconImage(new ImageIcon(myInfo.getHead_img()).getImage());
            title = new JLabel(str, new ImageIcon(myInfo.getHead_img()), JLabel.LEFT);
        } else
        {
            str = myInfo.getNickName() + "(" + myInfo.getAccNumber() + ")和";
            str += friendInfo.getNickName() + "(" + friendInfo.getAccNumber() + ")正在聊天...";
            setIconImage(new ImageIcon(friendInfo.getHead_img()).getImage());
            title = new JLabel(str, new ImageIcon(friendInfo.getHead_img()), JLabel.LEFT);
        }
        this.myInfo = myInfo;
        this.friendInfo = friendInfo;
        this.allList = allList;
        this.isAll = isAll;
        setTitle(str);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);
        JLabel titlebg = new JLabel(new ImageIcon("src\\main\\resources\\img\\2.jpg"));
        titlebg.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlebg.add(title);
        add(titlebg, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 1, 1));
        txtReceive = new JTextPane();
        txtReceive.setEditable(false);
        centerPanel.add(new JScrollPane(txtReceive));
        JPanel sendPanel = new JPanel(new BorderLayout());
        JLabel btnPanel = new JLabel(new ImageIcon("src\\main\\resources\\img\\11.jpg"));
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        cbFont = new JComboBox(sFont);
        cbSize = new JComboBox(sSize);
        cbFont.addItemListener(this);
        cbSize.addItemListener(this);
        btnShake = new JButton(new ImageIcon("src\\main\\resources\\img\\zd.png"));
        //设置按钮的大小与图片一致
        btnShake.setMargin(new Insets(0, 0, 0, 0));
        btnFile = new JButton("文件");
        btnColor = new JButton("颜色");
        btnFace = new JButton(new ImageIcon("src\\main\\resources\\img\\sendFace.png"));
        btnFace.setMargin(new Insets(0, 0, 0, 0));
        // 群聊不允许发送抖动
        btnShake.addActionListener(this);
        btnFile.addActionListener(this);
        btnColor.addActionListener(this);
        btnFace.addActionListener(this);
        btnPanel.add(cbFont);
        btnPanel.add(cbSize);
        btnPanel.add(btnColor);
        if (!isAll)
        {
            btnPanel.add(btnShake);
            JButton yy = new JButton("语音");
            btnPanel.add(yy);
            yy.addActionListener(e ->
            {
                try
                {
                    if(friendInfo.getFlag()!=1)
                    {
                        JOptionPane.showMessageDialog(null,"对方已经离线！");
                        return;
                    }
                    SendMsg sendMsg=new SendMsg();
                    sendMsg.myInfo=myInfo;
                    sendMsg.friendInfo=friendInfo;
                    sendMsg.cmd=Cmd.CMD_YY;
                    SendCmd.send(sendMsg);
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            });
        }
        btnPanel.add(btnFace);
        btnPanel.add(btnFile);
        sendPanel.add(btnPanel, BorderLayout.NORTH);
        txtSend = new JTextPane();
        sendPanel.add(txtSend, BorderLayout.CENTER);
        btnSend = new JButton("发送(S)");
        btnSend.setMnemonic('S');
        btnClose = new JButton("关闭(X)");
        btnClose.setMnemonic('X');
        btnSend.addActionListener(this);
        btnClose.addActionListener(this);
        JLabel bottombg = new JLabel(new ImageIcon("src\\main\\resources\\img\\11.jpg"));
        bottombg.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottombg.add(btnSend);
        bottombg.add(btnClose);
        sendPanel.add(bottombg, BorderLayout.SOUTH);
        centerPanel.add(new JScrollPane(sendPanel));
        add(centerPanel);
        if (!isAll)
        {
            JLabel lblboy = new JLabel(new ImageIcon("src\\main\\resources\\img\\6.jpg"));
            add(lblboy, BorderLayout.EAST);
        } else
        {
            jTextArea = new JTextArea(10, 12);
            jTextArea.setEnabled(false);
            flash();
            add(jTextArea, BorderLayout.EAST);
        }
        setSize(700, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //把发送框的内容提交到接收框，同时清除发送框的内容
    public void appendView(String name, StyledDocument xx) throws BadLocationException
    {
        //获取接收框的文档（内容）
        StyledDocument vdoc = txtReceive.getStyledDocument();
        // 格式化时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(date);
        SimpleAttributeSet as = new SimpleAttributeSet();
        String s = name + "    " + time + "\n";
//		saveRecord(name,s);//保存聊天记录
        vdoc.insertString(vdoc.getLength(), s, as);
        int end = 0;
        while (end < xx.getLength())
        {
            // 获得一个元素
            Element e0 = xx.getCharacterElement(end);
            //获取对应的风格
            SimpleAttributeSet as1 = new SimpleAttributeSet();
            StyleConstants.setForeground(as1, StyleConstants.getForeground(e0.getAttributes()));
            StyleConstants.setFontSize(as1, StyleConstants.getFontSize(e0.getAttributes()));
            StyleConstants.setFontFamily(as1, StyleConstants.getFontFamily(e0.getAttributes()));
            //获取该元素的内容
            s = e0.getDocument().getText(end, e0.getEndOffset() - end);
            // 将元素加到浏览窗中
            if ("icon".equals(e0.getName()))
            {
                vdoc.insertString(vdoc.getLength(), s, e0.getAttributes());
            } else
            {
                vdoc.insertString(vdoc.getLength(), s, as1);
//				saveRecord(name,s+"\n");//保存聊天记录
            }
            end = e0.getEndOffset();
        }
        // 输入一个换行
        vdoc.insertString(vdoc.getLength(), "\n", as);
        // 设置显示视图加字符的位置与文档结尾，以便视图滚动
        txtReceive.setCaretPosition(vdoc.getLength());
    }

    public void shake()
    {
        //抖动窗口
        int x = this.getLocation().x;
        int y = this.getLocation().y;
        for (int i = 0; i < 20; i++)
        {
            if (i % 2 == 0)
            {
                this.setLocation(x + 2, y + 2);
            } else
            {
                this.setLocation(x - 2, y - 2);
            }
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnShake)
        {
            //发送消息
            SendMsg msg = new SendMsg();
            msg.cmd = Cmd.CMD_SHAKE;
            msg.myInfo = myInfo;
            msg.friendInfo = friendInfo;
            SendCmd.send(msg);
            shake();
        } else if (e.getSource() == btnColor)
        {
            JColorChooser colordlg = new JColorChooser();
            Color color = colordlg.showDialog(this, "请选择字体颜色", Color.BLACK);
            txtSend.setForeground(color);
        } else if (e.getSource() == btnFace)
        {
            //打开表情窗口，选择表情图标
            int x, y;
            x = this.getLocation().x + 220;
            y = this.getLocation().y - 28;
            new BqUI(this, x, y);
        } else if (e.getSource() == btnFile)
        {
            FileDialog dlg = new FileDialog(this, "请选择要发送的文件(64K以下)", FileDialog.LOAD);
            dlg.setVisible(true);
            String filename = dlg.getDirectory() + dlg.getFile();
            if (filename == null)
                return;
            try
            {
                FileInputStream fis = new FileInputStream(filename);
                int size = fis.available();
                byte b[] = new byte[size];
                fis.read(b);
                SendMsg msg = new SendMsg();
                msg.cmd = Cmd.CMD_FILE;
                msg.myInfo = myInfo;
                msg.friendInfo = friendInfo;
                msg.b = b;
                msg.fileName = dlg.getFile();
                SendCmd.send(msg);
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        } else if (e.getSource() == btnSend)
        {
            if (txtSend.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请输入你要发送的内容。");
                return;
            }
            try
            {
                appendView(myInfo.getNickName(), txtSend.getStyledDocument());
                if (this.isAll)
                {
                    SendCmd.sendAll(allList, myInfo, Cmd.CMD_ALL, txtSend.getStyledDocument());
                } else
                {
                    SendMsg msg = new SendMsg();
                    msg.cmd = Cmd.CMD_SEND;
                    msg.myInfo = myInfo;
                    msg.friendInfo = friendInfo;
                    msg.doc = txtSend.getStyledDocument();
                    SendCmd.send(msg);
                }
                txtSend.setText("");
            } catch (BadLocationException e1)
            {
                e1.printStackTrace();
            }
        } else if (e.getSource() == btnClose)
        {
            dispose();
        }
    }

    public void setFont()
    {
        String sf = sFont[cbFont.getSelectedIndex()];
        String size = sSize[cbSize.getSelectedIndex()];
        font = new Font(sf, Font.PLAIN, Integer.parseInt(size));
        //设置发送文本框的字体
        txtSend.setFont(font);
    }

    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() == cbFont)
        {
            setFont();
        } else if (e.getSource() == cbSize)
        {
            setFont();
        }
    }

    public void flash()
    {
        jTextArea.setText("");
        allList = userMapper.getAll();
        int size = allList.size();
        for (int i = 0; i < allList.size(); i++)
        {
            User u = allList.get(i);
            if (u.getFlag() != 1 || u.getAccNumber() == myInfo.getAccNumber())
            {
                allList.set(i, null);
                size--;
            }
        }
        jTextArea.append("当前在线人数：" + size + "\n");
        jTextArea.append("群聊列表\n");
        for (User u : allList)
        {
            if (u != null)
                jTextArea.append(u.getNickName() + "(" + u.getAccNumber() + ")\n");
        }
        add(jTextArea, BorderLayout.EAST);
    }
}
