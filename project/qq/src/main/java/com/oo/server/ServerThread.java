package com.oo.server;

import com.google.gson.Gson;
import com.oo.domain.User;
import com.oo.mapper.UserMapper;
import com.oo.util.Cmd;
import com.oo.util.SendCmd;
import com.oo.yuyin.Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * 作者：刘时明
 * 日期：2018/9/26
 * 时间：16:28
 * 说明：服务器线程
 */
public class ServerThread extends Thread
{
    private Socket socket;
    private BufferedReader br;
    private UserMapper userMapper = App.context.getBean(UserMapper.class);

    public ServerThread(Socket socket)
    {
        this.socket = socket;
        try
        {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            String str = "ml_ip=" + socket.getInetAddress().toString() + "-" + socket.getPort();
            System.out.println("服务端发送数据：" + str);
            ps.println(str);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            String str;
            while ((str = getMsg()) != null)
            {
                System.out.println("接收消息：" + str);
                StringBuilder sb = new StringBuilder(str);
                if (str.startsWith("ml_ob="))
                {
                    sb.delete(0, 6);
                    Gson gson = new Gson();
                    User user = gson.fromJson(sb.toString(), User.class);
                    System.out.println("接收对象：" + user);
                    ServerUI.userMap.put(socket, user);
                    userMapper.changeFlag(ServerUI.userMap.get(socket).getAccNumber(), (byte) 1);
                    System.out.println("一个用户连接完成，当前人数：" + ServerUI.userMap.size());
                    ServerUI.flash();
                } else if (str.startsWith("ml_yy="))
                {
                    sb.delete(0, 6);
                    String temp = sb.toString();
                    for (Socket s : ServerUI.userMap.keySet())
                    {
                        User user = ServerUI.userMap.get(s);
                        if (temp.equals(user.getAccNumber() + ""))
                        {
                            PrintStream ps = new PrintStream(s.getOutputStream());
                            ps.println("ml_yy=" + temp);
                        }
                    }
                } else if (str.startsWith("ml_ym="))
                {
                    sb.delete(0, 6);
                    System.out.println("sb------------------->" + sb);
                    long acc = Integer.parseInt(sb.toString());
                    System.out.println("阻塞队列放行");
                    for (Socket s : ServerUI.userMap.keySet())
                    {
                        User u = ServerUI.userMap.get(s);
                        if (u.getAccNumber() == acc)
                        {
                            InputStream is=socket.getInputStream();
                            byte[] b=new byte[1024];
                            int len;
                            while ((len=is.read(b))>1)
                            {
                                s.getOutputStream().write(b,0,len);
                            }
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String getMsg()
    {
        try
        {
            return br.readLine();
        } catch (IOException e)
        {
            User user = ServerUI.userMap.get(socket);
            user.setPort(0);
            user.setIpAddr("");
            user.setFlag((byte) 0);
            userMapper.updateUser(user);
            ServerUI.userMap.remove(socket);
            System.out.println("一个用户退出，当前人数：" + ServerUI.userMap.size());
            ServerUI.flash();
            // 下线通知
            List<User> friendList = userMapper.getMyFriend(user.getAccNumber());
            SendCmd.sendAll(friendList, user, Cmd.CMD_OFFLINE);
        }
        return null;
    }
}
