package lsm1998.log4j.factory;

import com.lsm1998.log4j.config.MyLogConfig;
import com.lsm1998.log4j.core.MyDefaultLogger;
import com.lsm1998.log4j.core.MyLogger;
import com.lsm1998.log4j.io.FileNameChange;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午10:27
 * @说明：
 */
public class MyLoggerFactory
{
    private static Lock lock = new ReentrantLock();
    public static volatile boolean isLoad = false;

    public static MyLogger getLogger()
    {
        if (!isLoad)
        {
            try
            {
                lock.lock();
                Properties properties = new Properties();
                try
                {
                    properties.load(new FileInputStream("src/main/resources/log4j.properties"));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                MyLogConfig.getInstance(properties);
                new FileNameChange(MyLogConfig.logName);
            }finally
            {
                lock.unlock();
            }
        }
        return MyDefaultLogger.getInstance();
    }

    public static MyLogger getLogger(Properties properties)
    {
        if (!isLoad)
        {
            try
            {
                lock.lock();
                MyLogConfig.getInstance(properties);
                new FileNameChange(MyLogConfig.logName);
            }finally
            {
                lock.unlock();
            }
        }
        return MyDefaultLogger.getInstance();
    }
}
