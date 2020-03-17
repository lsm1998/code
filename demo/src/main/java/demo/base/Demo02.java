/*
 * 作者：刘时明
 * 时间：2020/3/9-16:07
 * 作用：
 */
package demo.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Demo02
{
    public static void main(String[] args) throws InterruptedException
    {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        queue.put("123");
        queue.put("123");
    }
}
