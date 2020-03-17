/*
 * 作者：刘时明
 * 时间：2020/3/17-21:18
 * 作用：
 */
package pb;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class ServerProtoBufHandler extends SimpleChannelInboundHandler<TaskProtobufWrapper.TaskProtocol>
{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TaskProtobufWrapper.TaskProtocol taskProtocol) throws Exception
    {
        switch (taskProtocol.getPackType())
        {
            case LOGIN:
                log.info("接收到一个登录类型的pack:[{}]", taskProtocol.getLoginPack().getUsername() + " : " + taskProtocol.getLoginPack().getPassword());
                break;
            case CREATE_TASK:
                log.info("接收到一个创建任务类型的pack:[{}]", taskProtocol.getCreateTaskPack().getTaskId() + " : " + taskProtocol.getCreateTaskPack().getTaskName());
                break;
            case DELETE_TASK:
                log.info("接收到一个删除任务类型的pack:[{}]", Arrays.toString(taskProtocol.getDeleteTaskPack().getTaskIdList().toArray()));
                break;
            default:
                log.error("接收到一个未知类型的pack:[{}]", taskProtocol.getPackType());
                break;
        }
    }
}
