package lsm1998.log4j.io;

import com.lsm1998.log4j.config.MyLogConfig;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午10:26
 * @说明：
 */
public class FileNameChange
{
    public static File file;

    public FileNameChange(String logName)
    {
        file = new File(getName(logName));
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
            }
            new FileThread(MyLogConfig.logChange, MyLogConfig.logName).start();
        } catch (IOException e)
        {
            System.err.println("创建文件" + logName + "出现错误");
        }
    }

    class FileThread extends Thread
    {
        private long n;
        private String name;

        public FileThread(long n, String name)
        {
            this.n = n;
            this.name = name;
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(n);
                    file = new File(FileNameChange.getName(name));
                    if (!file.exists())
                    {
                        file.createNewFile();
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getName(String name)
    {
        StringBuilder sb = new StringBuilder(name);
        int index;
        while ((index = sb.indexOf("${")) != -1)
        {
            if (sb.indexOf("}") == -1)
            {
                System.err.println("error");
                break;
            } else
            {
                int last = sb.indexOf("}");
                String temp = sb.substring(index + 2, last);
                sb.delete(index + 2, last);
                String time = new Timestamp(System.currentTimeMillis()).toString();
                for (int i = 0; i < temp.length(); i++)
                {
                    char c = temp.charAt(i);
                    switch (c)
                    {
                        case 'Y':
                            sb.append(time.substring(0, 4));
                            break;
                        case 'M':
                            sb.append(time.substring(5, 7));
                            break;
                        case 'D':
                            sb.append(time.substring(8, 10));
                            break;
                        case '-':
                            sb.append('-');
                            break;
                        default:
                            System.err.println("error char:" + c);
                            break;
                    }
                }
                sb.delete(index, index + 2);
                last = sb.indexOf("}");
                sb.delete(last, last + 1);
            }
        }
        return sb.toString();
    }
}
