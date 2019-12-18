package com.oo.server;

import com.google.gson.Gson;
import com.oo.domain.User;
import com.oo.mapper.UserMapper;
import com.oo.util.Cmd;
import com.oo.util.SendCmd;
import com.oo.util.SendMsg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * 作者：刘时明
 * 日期：2018/9/27
 * 时间：14:38
 * 说明：
 */
public class ClientThread extends Thread
{
    private Socket socket;
    private BufferedReader br;
    private User user;
    private UserMapper userMapper = App.context.getBean(UserMapper.class);

    public ClientThread(Socket socket, User user)
    {
        this.socket = socket;
        this.user = user;
        try
        {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        String msg;
        try
        {
            while ((msg = br.readLine()) != null)
            {
                System.out.println("客户端接收数据：" + msg);
                StringBuilder sb = new StringBuilder(msg);
                if (msg.startsWith("ml_ip="))
                {
                    sb.delete(0, 6);
                    user.setIpAddr(sb.toString().split("-")[0]);
                    user.setPort(Integer.parseInt(sb.toString().split("-")[1]));
                    user.setFlag((byte) 1);
                    System.out.println("当前user=" + user);
                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(user, User.class);
                    ps.println("ml_ob=" + jsonStr);
                    UserMapper userMapper = App.context.getBean(UserMapper.class);
                    userMapper.updateUser(user);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
