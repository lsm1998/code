package demo.thread.deadlock;

/**
 * @作者：刘时明
 * @时间：2019/6/8-11:56
 * @说明：死锁案例
 */
public class Test
{
    public static void main(String[] args)
    {
        Object o1=new Object();
        Object o2=new Object();
        Thread1 t1=new Thread1(o1,o2);
        Thread2 t2=new Thread2(o1,o2);
        t1.start();
        t2.start();
    }
}
