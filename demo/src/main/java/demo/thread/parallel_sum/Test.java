package demo.thread.parallel_sum;

import com.lsm1998.util.array.MyArrays;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * @作者：刘时明
 * @时间：2019/6/8-13:21
 * @说明：并行求和
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        int[] arr= MyArrays.getIntArrayByRandom(0,10,1000000);
        // Stream流的并行求和
        System.out.println(IntStream.of(arr).parallel().sum());

        // 使用线程池并行求和
        // 指定并行数量为4
        ForkJoinPool pool=new ForkJoinPool(4);
        Future<Integer> future=pool.submit(new SumThread(0,arr.length,arr,100));
        System.out.println(future.get());
        pool.shutdown();
    }
}
