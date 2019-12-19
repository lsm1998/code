package demo.net.echoes;

import com.lsm1998.util.net.bean.MsgData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/8-14:17
 * @说明：
 */
public class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 8888);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        for (int i = 0; i < 10; i++)
        {
            MsgData<byte[]> data = new MsgData<>();
            data.setData("你好啊".getBytes());
            oos.writeObject(data);
            MsgData<byte[]> result = (MsgData<byte[]>) ois.readObject();
            System.out.println("收到服务器消息：" + new String(result.getData()));
        }
    }
}
