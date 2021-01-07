/*
 * 作者：刘时明
 * 时间：2019/12/21-23:49
 * 作用：
 */
package design.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable
{
    // 存储对应的观察者
    List<MyObserver> observerList = new ArrayList<>();

    // 注册一个观察者
    public void registerObserver(MyObserver o)
    {
        observerList.add(o);
    }

    // 删除一个观察者
    public void removeObserver(MyObserver o)
    {
        observerList.remove(o);
    }

    // 被观察者对其对应观察者的一次更新通知
    public void notifyObservers(Object value)
    {
        for (MyObserver o : observerList)
        {
            o.update(this, value);
        }
    }
}
