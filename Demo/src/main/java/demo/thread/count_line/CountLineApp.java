package demo.thread.count_line;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @作者：刘时明
 * @时间：2019/6/22-19:56
 * @作用：多线程统计一个目录内java源码行数
 */
public class CountLineApp
{
    public static void main(String[] args)
    {
        // 使用普通线程池
        FileReaderHandle.start("C:\\Users\\lsm\\IdeaProjects");

        // 使用并行池 CountLineTask
        ForkJoinPool pool=new ForkJoinPool(4);
        List<File> fileList=FileReaderHandle.getAllJavaFile("C:\\Users\\lsm\\IdeaProjects");
        System.out.println("文件数量："+fileList.size());
        Future<Integer> future=pool.submit(new CountLineTask(0,fileList.size(),fileList));
        try
        {
            System.out.println("统计数量："+future.get());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
