package com.lsm1998.concurrent;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Phaser使用
 */
public class PhaserTest
{
    private final Phaser phaser = new Phaser();

    private final ExecutorService service = Executors.newFixedThreadPool(10);

    class Worker implements Runnable
    {
        @SneakyThrows
        @Override
        public void run()
        {
            phaser.register();
            while (true)
            {
                Thread.sleep(500);
                System.out.println("Worker!!!" + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
            }
        }
    }

    @Test
    public void testPhaser()
    {
        phaser.register();
        service.execute(new Worker());
        service.execute(new Worker());
        service.execute(new Worker());
        service.execute(new Worker());
        while (true)
        {
            phaser.arriveAndAwaitAdvance();
            System.out.println("阶段=" + phaser.getPhase());
        }
    }
}
