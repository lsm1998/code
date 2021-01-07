package design.proxy;

public class TxDB
{
    public void beginTx(Object proxy)
    {
        System.out.println("为" + proxy + "开启事务");
    }

    public void endTx(Object proxy)
    {
        System.out.println("为" + proxy + "结束事务");
    }
}
