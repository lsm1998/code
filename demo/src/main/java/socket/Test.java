/*
 * 作者：刘时明
 * 时间：2020/3/11-9:44
 * 作用：
 */
package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class Test
{
    private static final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception
    {
        /*
         * 向服务器端发送数据
         */
        // 1.定义服务器的地址、端口号、数据
        InetAddress address = InetAddress.getByName("119.29.117.244");
        int port = 9092;
        byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // 2.创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        // 3.创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket();

        new Thread(() ->
        {
            while (true)
            {
                int temp = count.get();
                try
                {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("speed in 5 seconds is " + (count.get() - temp) / 5.0);
            }
        }).start();
        while (true)
        {
            socket.send(packet);
            count.addAndGet(1);
        }
    }
}
