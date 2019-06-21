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

        MsgData<String> data=new MsgData<>();
        data.msg="你好啊";

        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(data);

        ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
        MsgData<String> result=(MsgData<String>)ois.readObject();
        System.out.println("收到服务器消息："+result.msg);
    }
}
