/*
 * 作者：刘时明
 * 时间：2019/12/21-16:37
 * 作用：BIO FTP服务端
 */
package demo.net.ftp;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpServer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class FtpServer
{
    public static final int MAX_LEN=1024*10;
    private static Set<Socket> sockets = new HashSet<>();

    public static void main(String[] args) throws IOException
    {
        TcpServer server = new TcpServer("127.0.0.1", 8888);
        server.start((data, oos, socket) ->
        {
            if (!sockets.contains(socket))
            {
                sockets.add(socket);
                System.out.println("一个客户端连入，当前在线=" + sockets.size());
            }
            MsgData<String> result = new MsgData<>();
            switch (data.getCode())
            {
                case 1:
                    result.setCode(3);
                    // 文件上传
                    FileData fileData= (FileData) data.getData();
                    if(fileData.getData().length>MAX_LEN)
                    {
                        result.setCode(-1);
                        result.setData("你上传的包超过了最大限制，拒绝接受");
                        break;
                    }
                    if (fileUpLoad(fileData))
                    {
                        result.setData("服务器接受你的第"+((FileData) data.getData()).getSeq()+"次上传");
                    }else
                    {
                        result.setCode(-1);
                        result.setData("接受失败");
                    }
                    break;
                case 2:
                    // 文件下载
                    break;
                default:
                    break;
            }
            try
            {
                oos.writeObject(result);
            } catch (IOException e)
            {
                sockets.remove(socket);
                System.out.println("客户端已经退出，当前在线=" + sockets.size());
            }
        });
    }

    private static boolean fileUpLoad(FileData fileData)
    {
        try(RandomAccessFile accessFile=new RandomAccessFile(fileData.getDist(),"rw"))
        {
            accessFile.seek(fileData.getSeq()*fileData.getMaxSize());
            accessFile.write(fileData.getData(),0,fileData.getSize());
            return true;
        }catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
