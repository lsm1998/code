package demo.thread.comsumer_queue;

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
            while (true)
            {
                Thread.sleep(100);
                test.bq.take();
                System.out.println("消费者消费一个。。。");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
