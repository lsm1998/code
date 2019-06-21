package demo.thread.comsumer_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @作者：刘时明
 * @时间：2019/6/8-11:49
 * @说明：使用阻塞队列实现生成者消费者
 */
public class Test
{
    public BlockingQueue<Integer> bq=new ArrayBlockingQueue(1);

    public static void main(String[] args)
    {
        Test t=new Test();
        ConsumerThread t1=new ConsumerThread(t);
        ProducerThread t2=new ProducerThread(t);
        t1.start();
        t2.start();
    }
}
