package lsm1998.log4j.config;

import com.lsm1998.log4j.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午10:28
 * @说明：
 */
public class MyLogConfig
{
    private static volatile MyLogConfig logConfig = null;
    private static Lock lock = new ReentrantLock();
    public static String logName = null;
    public static long logChange = 0;

    private MyLogConfig(Properties properties)
    {
        config(properties);
    }

    private void config(Properties properties)
    {
        if (properties.containsKey("log.name"))
        {
            logName = properties.getProperty("log.name");
        }
        if (properties.containsKey("log.src"))
        {
            logName = properties.getProperty("log.src") + logName;
        }

        if (properties.containsKey("log.change"))
        {
            String temp = properties.getProperty("log.change").replace(" ", "");
            if (temp.indexOf("${") < 0)
            {
                logChange = Long.parseLong(temp);
            } else
            {
                StringBuilder sb = new StringBuilder(temp);
                int index = -1;
                if ((index = sb.indexOf("${")) != -1)
                {
                    int last = sb.indexOf("}");
                    if (last == -1 || last < index)
                    {
                        System.err.println("配置项log.change表达式有错误");
                    } else
                    {
                        String str = sb.substring(index + 2, last);
                        List<Integer> list = new ArrayList<>();
                        for (String s : str.split("[*|+|\\-|/]"))
                        {
                            try
                            {
                                list.add(Integer.parseInt(s));
                            } catch (Exception e)
                            {
                                System.err.println("配置项log.change表达式有错误");
                                e.printStackTrace();
                            }
                        }
                        int sum = list.get(0);
                        for (int i = 1; i < list.size(); i++)
                        {
                            int i1 = sb.indexOf("+");
                            int i2 = sb.indexOf("-");
                            int i3 = sb.indexOf("*");
                            int i4 = sb.indexOf("/");
                            int min = LogUtil.getMinIndex(new int[]{i1, i2, i3, i4});
                            if (min == i1)
                            {
                                sum += list.get(i);
                            } else if (min == i2)
                            {
                                sum -= list.get(i);
                            } else if (min == i3)
                            {
                                sum *= list.get(i);
                            } else
                            {
                                sum /= list.get(i);
                            }
                            sb.delete(min, min + 1);
                        }
                        logChange = sum;
                    }
                }
            }
        }
    }

    public static MyLogConfig getInstance(Properties properties)
    {
        try
        {
            lock.lock();
            if (logConfig == null)
            {
                return new MyLogConfig(properties);
            }
        } finally
        {
            lock.unlock();
        }
        return logConfig;
    }
}
