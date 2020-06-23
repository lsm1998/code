package demo.thread.comsumer;

/**
 * @作者：刘时明
 * @时间：2019/6/8-11:40
 * @说明：使用wait + notify实现生产者消费者
 */
public class Test
{
    public volatile boolean flag=false;

    public static void main(String[] args)
    {
        Test t=new Test();

        ConsumerThread t1=new ConsumerThread(t);

        ProducerThread t2=new ProducerThread(t);
        t1.start();
        t2.start();
    }
}
