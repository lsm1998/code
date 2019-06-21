package demo.thread.comsumer_queue;

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
        this.test = test;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Thread.sleep(200);
                test.bq.put(1);
                System.out.println("生产者生成一个。。。");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
