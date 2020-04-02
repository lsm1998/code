package com.oo.server;

import com.oo.domain.User;
import com.oo.util.PathUtil;

import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：刘时明
 * 日期：2018/9/30
 * 时间：18:10
 * 说明：服务端程序
 */
public class ServerUI extends JFrame
{
    public static Map<Socket, User> userMap = new HashMap<>();
    private static JTextArea textArea;
    private JButton open, close;
    private ServerSocket ss;
    private JLabel flag;

    public ServerUI()
    {
        super("oo服务器");
        this.setDefaultCloseOperation(3);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        init();
        this.setVisible(true);
    }

    private void init()
    {
        JLabel up = new JLabel("oo服务端程序", new ImageIcon(PathUtil.RESOURCES +"\\img\\logo.png"), JLabel.CENTER);
        this.add(up, BorderLayout.NORTH);
        textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setForeground(Color.BLUE);
        textArea.append("服务器未开启");
        this.add(new JScrollPane(textArea));
        open = new JButton("开启");
        close = new JButton("关闭");
        flag = new JLabel("当前状态：" + (ss == null || ss.isClosed() ? "关闭" : "开启"));
        JPanel temp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        temp.add(open);
        temp.add(close);
        temp.add(flag);
        open.addActionListener(e -> open());
        close.addActionListener(e -> close());
        this.add(temp, BorderLayout.SOUTH);
    }

    private void open()
    {
        if (ss == null || ss.isClosed())
        {
            try
            {
                ss = new ServerSocket(8080);
                System.out.println("服务器已经就绪");
                new ServerAccept(ss).start();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            flag.setText("当前状态：开启");
            textArea.setText("");
            textArea.append("当前在线人数：" + userMap.size() + "人\n");
        } else
        {
            JOptionPane.showMessageDialog(null, "无需重复打开");
        }
    }

    private void close()
    {
        if (ss == null || ss.isClosed())
        {
            JOptionPane.showMessageDialog(null, "已经关闭了");
        } else
        {
            try
            {
                userMap.clear();
                ss.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            flag.setText("当前状态：关闭");
            textArea.setText("");
            textArea.append("服务器未开启");
        }
    }

    public static void main(String[] args)
    {
        new ServerUI();
    }

    class ServerAccept extends Thread
    {
        private ServerSocket ss;

        public ServerAccept(ServerSocket ss)
        {
            this.ss = ss;
        }

        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    Socket socket = ss.accept();
                    User user = new User();
                    user.setPort(socket.getPort());
                    System.out.println(socket.getPort());
                    user.setIpAddr(socket.getInetAddress().toString());
                    userMap.put(socket, user);
                    new ServerThread(socket).start();
                    System.out.println("一个客户端连入...当前人数:" + userMap.size());
                }
            } catch (Exception e)
            {
                System.out.println("服务器中途关闭，监听线程结束");
            }
        }
    }

    public static void flash()
    {
        textArea.setText("");
        if (userMap.size() == 0)
        {
            textArea.append("当前没有用户连接");
        } else
        {
            textArea.append("当前在线人数：" + userMap.size() + "人\n");
            for (Socket s : userMap.keySet())
            {
                User user = userMap.get(s);
                textArea.append("昵称：" + user.getNickName() + "，账号：" + user.getAccNumber() + "，连接信息：" + user.getIpAddr() + "-" + user.getPort() + "\n");
            }
        }
    }
}
