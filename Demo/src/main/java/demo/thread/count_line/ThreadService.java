package demo.thread.count_line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @作者：刘时明
 * @时间：2019/6/22-20:06
 * @作用：线程池服务类
 */
public class ThreadService
{
    // 线程池
    private final ExecutorService exec;

    public ThreadService(int count)
    {
        exec= Executors.newFixedThreadPool(count);
    }

    public int executor(File file)
    {
        FutureTask<Integer> task=new FutureTask(()-> FileReaderHandle.countLineByFile(file));
        exec.submit(task);
        try
        {
            return task.get();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public void shutdown()
    {
        exec.shutdown();
    }
}
