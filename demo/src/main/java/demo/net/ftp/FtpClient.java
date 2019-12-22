/*
 * 作者：刘时明
 * 时间：2019/12/21-17:04
 * 作用：
 */
package demo.net.ftp;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpClient;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FtpClient
{
    public static void main(String[] args) throws IOException
    {
        TcpClient client = new TcpClient("127.0.0.1", 8888);

        client.start(data -> {
            if(data.getCode()<0)
            {
                System.err.println("服务端响应为失败,"+data.getData());
            }else
            {
                System.out.println("接受来自服务端的消息：" +  data.getData());
            }
        });

        RandomAccessFile accessFile=new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\1.jpg","rw");
        int seq=0;
        while (true)
        {
            byte[] b=new byte[10*1024];
            int count=accessFile.read(b);
            if(count==-1)
            {
                break;
            }
            MsgData<FileData> data=new MsgData<>();
            data.setCode(1);
            FileData fileData=new FileData();
            fileData.setMaxSize(10*1024);
            fileData.setDist("C:\\Users\\Administrator\\Desktop\\2.jpg");
            fileData.setSeq(seq);
            fileData.setSize(count);
            fileData.setData(b);
            data.setData(fileData);
            client.send(data);
            seq++;
        }
    }
}
