package com.lsm1998.concurrent;

import org.junit.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Exchanger使用
 * <p>
 * Exchanger用于在两个线程之间高效的交换数据
 */
public class ExchangerTest
{
    @Test
    public void testExchanger() throws InterruptedException
    {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(new ExchangerProducer(exchanger)).start();
        new Thread(new ExchangerConsumer(exchanger)).start();

        TimeUnit.SECONDS.sleep(10);
    }

    static class ExchangerProducer implements Runnable
    {
        private final Exchanger<String> exchanger;

        public ExchangerProducer(Exchanger<String> exchanger)
        {
            this.exchanger = exchanger;
        }

        @Override
        public void run()
        {
            for (int i = 1; i < 5; i++)
            {
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                    String val = String.valueOf(i);
                    System.out.println("Producer 交换前:" + val);
                    val = exchanger.exchange(val);
                    System.out.println("Producer 交换后:" + val);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ExchangerConsumer implements Runnable
    {
        private final Exchanger<String> exchanger;

        public ExchangerConsumer(Exchanger<String> exchanger)
        {
            this.exchanger = exchanger;
        }

        @Override
        public void run()
        {
            while (true)
            {
                String val = "初始值";
                System.out.println("Consumer 交换前:" + val);
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                    val = exchanger.exchange(val);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Consumer 交换后:" + val);
            }
        }
    }
}
