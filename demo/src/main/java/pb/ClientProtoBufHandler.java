/*
 * 作者：刘时明
 * 时间：2020/3/17-21:48
 * 作用：
 */
package pb;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ClientProtoBufHandler extends ChannelInboundHandlerAdapter
{
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx)
    {
        executor.scheduleAtFixedRate(() ->
        {
            // 产生的pack类型
            int packType = new Random().nextInt(3);
            switch (TaskProtobufWrapper.PackType.forNumber(packType))
            {
                case LOGIN:
                    TaskProtobufWrapper.LoginPack loginPack = TaskProtobufWrapper.LoginPack.newBuilder().setUsername("张三[" + atomicInteger.getAndIncrement() + "]").setPassword("123456").build();
                    ctx.writeAndFlush(TaskProtobufWrapper.TaskProtocol.newBuilder().setPackType(TaskProtobufWrapper.PackType.LOGIN).setLoginPack(loginPack).build());
                    break;
                case CREATE_TASK:
                    TaskProtobufWrapper.CreateTaskPack createTaskPack = TaskProtobufWrapper.CreateTaskPack.newBuilder().setCreateTime(System.currentTimeMillis()).setTaskId("100" + atomicInteger.get()).setTaskName("任务编号" + atomicInteger.get()).build();
                    ctx.writeAndFlush(TaskProtobufWrapper.TaskProtocol.newBuilder().setPackType(TaskProtobufWrapper.PackType.CREATE_TASK).setCreateTaskPack(createTaskPack).build());
                    break;
                case DELETE_TASK:
                    TaskProtobufWrapper.DeleteTaskPack deleteTaskPack = TaskProtobufWrapper.DeleteTaskPack.newBuilder().addTaskId("1001").addTaskId("1002").build();
                    ctx.writeAndFlush(TaskProtobufWrapper.TaskProtocol.newBuilder().setPackType(TaskProtobufWrapper.PackType.DELETE_TASK).setDeleteTaskPack(deleteTaskPack).build());
                    break;
                default:
                    log.error("产生一个未知的包类型:[{}]", packType);
                    break;
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        ctx.close();
        log.error("发生异常", cause);
    }
}
