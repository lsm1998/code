package demo.net.echoes;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/8-14:06
 * @说明：基于BIO实现的回声服务器
 */
public class Server
{
    public static void main(String[] args) throws Exception
    {
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 8888);
        ServerSocket server=new ServerSocket();
        server.bind(isa);
        System.out.println("服务器已经启动");
        while (true)
        {
            Socket socket=server.accept();
            new ClientThread(socket).start();
        }
    }
}
