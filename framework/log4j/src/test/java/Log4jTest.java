import com.lsm1998.log4j.Logger;
import com.lsm1998.log4j.LoggerFactory;

/**
 * 作者：刘时明
 * 时间：2021/1/19
 */

public class Log4jTest
{
    public static void main(String[] args)
    {
        Logger logger = LoggerFactory.getLogger(Log4jTest.class);
        logger.debug("hello");
    }
}
