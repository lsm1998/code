package demo.thread.parallel_sum;

import java.util.concurrent.RecursiveTask;

/**
 * @作者：刘时明
 * @时间：2019/6/8-13:44
 * @说明：并行子任务类，RecursiveTask代表有返回值，RecursiveAction代表无返回值
 */
public class SumThread extends RecursiveTask<Integer>
{
    private int start;
    private int end;
    private int[] arr;
    private final int TASK_LEN;

    public SumThread(int start, int end, int[] arr,int TASK_LEN)
    {
        this.start = start;
        this.end = end;
        this.arr = arr;
        this.TASK_LEN=TASK_LEN;
    }

    @Override
    protected Integer compute()
    {
        int sum=0;
        if(end-start<TASK_LEN)
        {
            for (int i=start;i<end;i++)
            {
                sum+=arr[i];
            }
            return sum;
        }else
        {
            int mid=(start+end)/2;
            SumThread left=new SumThread(start,mid,arr,TASK_LEN);
            SumThread right=new SumThread(mid,end,arr,TASK_LEN);
            left.fork();
            right.fork();
            return left.join()+right.join();
        }
    }
}
