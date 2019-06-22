package demo.thread.count_line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @作者：刘时明
 * @时间：2019/6/22-19:57
 * @作用：文件读取处理类
 */
public class FileReaderHandle
{
    // 线程池大小取10
    private final static int POOL_SIZE=10;
    // java文件后缀
    private final static String JAVA_SUFFIX=".java";
    // 线程池服务对象
    private final static ThreadService service=new ThreadService(POOL_SIZE);
    // 总行数
    private static volatile int count=0;
    // 同步锁
    private static final Lock lock=new ReentrantLock();
    // 统计的文件数量
    public static volatile int file_sum=0;

    public static void start(String directory)
    {
        File file=new File(directory);
        if(file.isDirectory())
        {
            handle(file);
            service.shutdown();
            System.out.println("最终执行完毕，count="+count);
            System.out.println("文件数量:"+file_sum);
        }else
        {
            throw new RuntimeException("请指定一个目录");
        }
    }

    public static List<File> getAllJavaFile(String directory)
    {
        File file=new File(directory);
        if(file.isDirectory())
        {
            List<File> fileList=new ArrayList<>();
            handle(file,fileList);
            return fileList;
        }else
        {
            throw new RuntimeException("请指定一个目录");
        }
    }

    private static void handle(File file,List<File> fileList)
    {
        if(file.isDirectory())
        {
            File[] files=file.listFiles(e->e.isDirectory()||e.getName().endsWith(JAVA_SUFFIX));
            for (File f:files)
            {
                handle(f,fileList);
            }
        }else
        {
            fileList.add(file);
        }
    }

    private static void handle(File file)
    {
        if(file.isDirectory())
        {
            File[] files=file.listFiles(e->e.isDirectory()||e.getName().endsWith(JAVA_SUFFIX));
            for (File f:files)
            {
                handle(f);
            }
        }else
        {
            try
            {
                lock.lock();
                count+= service.executor(file);
                file_sum++;
            }finally
            {
                lock.unlock();
            }
        }
    }

    public static int countLineByFile(File file)
    {
        int count=0;
        try(BufferedReader br=new BufferedReader(new FileReader(file)))
        {
            String str;
            while ((str=br.readLine())!=null)
            {
                if(!isNoteOrBlank(str.trim()))
                {
                    count++;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public static boolean isNoteOrBlank(String str)
    {
        return str.equals("")||str.startsWith("/")||str.startsWith("*");
    }
}
