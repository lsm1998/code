package demo.thread.comsumer;

/**
 * @作者：刘时明
 * @时间：2019/6/8-11:39
 * @说明：生产者线程
 */
public class ProducerThread extends Thread
{
    private Test test;

    public ProducerThread(Test test)
    {
        this.test=test;
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
                    if(test.flag)
                    {
                        Thread.sleep(200);
                        System.out.println("生产者生成一个。。。");
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
