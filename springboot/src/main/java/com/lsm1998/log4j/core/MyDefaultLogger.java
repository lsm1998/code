package lsm1998.log4j.core;

import com.lsm1998.log4j.io.FileNameChange;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午11:55
 * @说明：
 */
public class MyDefaultLogger implements MyLogger
{
    public static MyDefaultLogger defaultLogger = new MyDefaultLogger();

    private MyDefaultLogger()
    {

    }

    public static MyDefaultLogger getInstance()
    {
        return defaultLogger;
    }

    @Override
    public void debug(String debug)
    {

        try (FileWriter fw = new FileWriter(FileNameChange.file, true);
             PrintWriter pw = new PrintWriter(fw))
        {
            pw.println("[DEBUG] info :" + debug);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void info(String info)
    {
        try (FileWriter fw = new FileWriter(FileNameChange.file, true);
             PrintWriter pw = new PrintWriter(fw))
        {
            pw.println("[INFO] info :" + info);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void error(String error)
    {
        try (FileWriter fw = new FileWriter(FileNameChange.file, true);
             PrintWriter pw = new PrintWriter(fw))
        {
            pw.println("[ERROR] info :" + error);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
