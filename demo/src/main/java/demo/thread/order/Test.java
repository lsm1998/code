package demo.thread.order;

/**
 * @作者：刘时明
 * @时间：2019/6/8-12:05
 * @说明：多线程顺序执行
 */
public class Test
{
    public volatile int count;

    public static void main(String[] args)
    {
        Test test=new Test();
        OrderThread t1=new OrderThread(0,3,'a',test);
        OrderThread t2=new OrderThread(1,3,'b',test);
        OrderThread t3=new OrderThread(2,3,'c',test);
        t1.start();
        t2.start();
        t3.start();
    }
}
