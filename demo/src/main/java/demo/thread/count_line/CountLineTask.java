package demo.thread.count_line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @作者：刘时明
 * @时间：2019/6/22-22:38
 * @作用：
 */
public class CountLineTask extends RecursiveTask<Integer>
{
    private int start;
    private int end;
    private List<File> fileList;
    private int size;

    public CountLineTask(int start,int end,List<File> fileList)
    {
        this.start=start;
        this.end=end;
        this.fileList=fileList;
        size=fileList.size();
    }

    @Override
    protected Integer compute()
    {
        if(end-start==1)
        {
            if(end==size-1)
            {
                return getCount(fileList.get(end-1));
            }else
            {
                return getCount(fileList.get(start));
            }
        }else
        {
            int mid=(start+end)/2;
            CountLineTask left=new CountLineTask(start,mid,fileList);
            CountLineTask right=new CountLineTask(mid,end,fileList);
            left.fork();
            right.fork();
            return left.join()+right.join();
        }
    }

    private static int getCount(File file)
    {
        return FileReaderHandle.countLineByFile(file);
    }
}
