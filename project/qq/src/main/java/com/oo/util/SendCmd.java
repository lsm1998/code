package com.oo.util;

import com.oo.domain.Msg;
import com.oo.domain.User;
import com.oo.mapper.MsgMapper;
import com.oo.mapper.UserMapper;
import com.oo.server.App;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import javax.swing.text.StyledDocument;

public class SendCmd
{
    private static MsgMapper msgMapper = App.context.getBean(MsgMapper.class);

    public static void send(SendMsg msg)
    {
        User user = App.context.getBean(UserMapper.class).getUserByAccNumber(msg.friendInfo.getAccNumber());
        try
        {
            System.out.println("开始send方法");
            //定义Socket
            DatagramSocket socket = new DatagramSocket();
            //创建一个字节数组输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //把对象输出到字节数组中
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(msg);
            oos.flush();
            //把要发送的数据转换为字节数组
            byte[] b = bos.toByteArray();
            //获取好友的IP地址
            if (user.getIpAddr() == null || user.getIpAddr().indexOf("/") < 0)
            {
                System.out.println("保存一个离线消息");
                Msg msg1 = new Msg();
                msg1.setContent(b);
                msg1.setFriendId(user.getAccNumber());
                msg1.setMyId(msg.myInfo.getAccNumber());
                msg1.setFlag((byte) 0);
                msgMapper.saveMsg(msg1);
                return;
            }
            InetAddress addr = InetAddress.getByName(user.getIpAddr().split("/")[1]);
            //获取好友的接收端口
            int port = user.getPort();
            //生成发送报
            DatagramPacket pack = new DatagramPacket(b, 0, b.length, addr, port);
            System.out.println("在线情况=" + user.getFlag());
            socket.send(pack);
            socket.close();
            oos.close();
        } catch (SocketException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //广播发送信息
    public static void sendAll(List<User> allInfo, User myInfo, int cmd)
    {
        for (User user : allInfo)
        {
            if (user.getFlag() == 1 && user.getAccNumber() != myInfo.getAccNumber())
            {
                SendMsg msg = new SendMsg();
                msg.cmd = cmd;
                msg.myInfo = myInfo;
                msg.friendInfo = user;
                send(msg);
            }
        }
    }

    //群聊
    public static void sendAll(List<User> allInfo, User myInfo, int cmd, StyledDocument doc)
    {
        for (User user : allInfo)
        {
            if (user == null)
                continue;
            if (user.getAccNumber() != myInfo.getAccNumber())
            {
                SendMsg msg = new SendMsg();
                msg.cmd = cmd;
                msg.myInfo = myInfo;
                msg.friendInfo = user;
                msg.doc = doc;
                send(msg);
            }
        }
    }
}