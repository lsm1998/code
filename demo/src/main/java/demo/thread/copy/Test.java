package demo.thread.copy;

import java.io.File;

/**
 * @作者：刘时明
 * @时间：2019/6/8-12:35
 * @说明：多线程拷贝
 */
public class Test
{
    private static final int TASK_SIZE=1024;

    public static void main(String[] args) throws Exception
    {
        File source=new File("C:\\Users\\lsm\\Desktop\\test.txt");
        File target=new File("C:\\Users\\lsm\\Desktop\\target.txt");
        if(target.exists())
        {
            target.delete();
        }
        target.createNewFile();
        long size= source.length();
        for (int i=0;i<size-TASK_SIZE;i+=TASK_SIZE)
        {
            CopyThread copy=new CopyThread(i,TASK_SIZE,source,target);
            copy.start();
        }
        if(size%TASK_SIZE!=0)
        {
            int start=(int)size-(int)size%TASK_SIZE;
            int len=(int)size%TASK_SIZE;
            CopyThread copy=new CopyThread(start,len,source,target);
            copy.start();
        }
    }
}
