package demo.thread.copy;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @作者：刘时明
 * @时间：2019/6/8-12:36
 * @说明：
 */
public class CopyThread extends Thread
{
    // 起始位置
    private int start;
    // 拷贝长度
    private int len;
    private File source;
    private File target;

    public CopyThread(int start,int len,File source,File target)
    {
        this.len=len;
        this.start=start;
        this.source=source;
        this.target=target;
    }

    @Override
    public void run()
    {
        try(RandomAccessFile read=new RandomAccessFile(source,"r");
            RandomAccessFile write=new RandomAccessFile(target,"rw"))
        {
            read.seek(start);
            byte[] b=new byte[len];
            read.read(b);
            write.seek(start);
            write.write(b);
            System.out.println(Thread.currentThread().getName()+"运行完毕");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
