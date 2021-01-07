/*
 * 作者：刘时明
 * 时间：2019/12/21-23:52
 * 作用：
 */
package design.observer;

public class MyPhone extends Observable
{
    public void release()
    {
        notifyObservers("发布了出售信息，欢迎大家购买");
    }

    public void remove()
    {
        notifyObservers("手机已经卖出了，谢谢大家支持");
    }
}
