/*
 * 作者：刘时明
 * 时间：2019/12/21-23:47
 * 作用：
 */
package design.observer;

public class Market1 implements MyObserver
{
    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("这里是交易市场1，刚刚有位买家发布了一个信息：" + arg);
    }
}
