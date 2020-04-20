/*
 * 作者：刘时明
 * 时间：2019/12/21-17:04
 * 作用：
 */
package com.lsm1998.io.ftp;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpClient;

import java.io.File;
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
        File file=new File("C:\\Users\\Administrator\\Desktop\\error.png");
        if(!file.exists())
        {
            System.err.println("文件不存在");
            return;
        }
        RandomAccessFile accessFile=new RandomAccessFile(file,"r");
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
            fileData.setMaxSize(b.length);
            fileData.setTotal(file.length());
            fileData.setDist("C:\\Users\\Administrator\\Desktop\\error-copy.png");
            fileData.setSeq(seq);
            fileData.setSize(count);
            fileData.setData(b);
            data.setData(fileData);
            client.send(data);
            seq++;
        }
    }
}
