package demo.net.echoes;

import com.lsm1998.util.net.bean.MsgData;

import java.io.*;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/8-14:10
 * @说明：
 */
public class ClientThread extends Thread
{
    private Socket socket;

    public ClientThread(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()))
        {
            Object o = null;
            while ((o = ois.readObject()) != null)
            {
                MsgData<byte[]> data = (MsgData<byte[]>) o;
                System.out.println("收到客户端消息：" + new String(data.getData()));
                MsgData<byte[]> result = new MsgData<>();
                String msg = "回声：" + new String(data.getData());
                result.setData(msg.getBytes());
                oos.writeObject(result);
            }
        } catch (Exception e)
        {
            System.out.println("客户端退出");
        }
    }
}
