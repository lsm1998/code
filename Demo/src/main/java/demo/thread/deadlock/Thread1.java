package demo.thread.deadlock;

/**
 * @作者：刘时明
 * @时间：2019/6/8-11:56
 * @说明：
 */
public class Thread1 extends Thread
{
    private Object o1;
    private Object o2;

    public Thread1(Object o1,Object o2)
    {
        this.o1=o1;
        this.o2=o2;
    }

    @Override
    public void run()
    {
        try
        {
            synchronized (o1)
            {
                Thread.sleep(100);
                synchronized (o2)
                {
                    Thread.sleep(100);
                }
            }
            System.out.println("运行结束");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
