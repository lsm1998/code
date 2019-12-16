package demo.thread.comsumer;

/**
 * @作者：刘时明
 * @时间：2019/6/8-11:43
 * @说明：
 */
public class ConsumerThread extends Thread
{
    private Test test;

    public ConsumerThread(Test test)
    {
        this.test = test;
    }

    @Override
    public void run()
    {
        try
        {
            synchronized (test)
            {
                while (true)
                {
                    if(!test.flag)
                    {
                        Thread.sleep(100);
                        System.out.println("消费者消费一个。。。");
                        test.flag=!test.flag;
                        test.notify();
                    }else
                    {
                        test.wait();
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
