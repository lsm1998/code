package demo.thread.order;

/**
 * @作者：刘时明
 * @时间：2019/6/8-12:05
 * @说明：
 */
public class OrderThread extends Thread
{
    private final int num;
    private final int curr;
    private final char task;
    private Test test;

    public OrderThread(int num, int curr, char task, Test test)
    {
        this.curr = curr;
        this.num = num;
        this.task = task;
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
                    if (test.count % curr == num)
                    {
                        Thread.sleep(500);
                        System.out.println(task);
                        test.count++;
                        test.notifyAll();
                    }else
                    {
                        test.wait();
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
