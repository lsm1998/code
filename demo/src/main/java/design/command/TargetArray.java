package design.command;

public class TargetArray
{
    public <E> E arrMethod(E[] target, Command<E> cmd)
    {
        // 直接调用命令接口的方法返回
        return cmd.resultMethod(target);
    }
}
