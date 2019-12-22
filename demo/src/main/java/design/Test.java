/*
 * 作者：刘时明
 * 时间：2019/12/21-23:53
 * 作用：
 */
package design;

public class Test
{
    public static void main(String[] args)
    {
        // 创建我的二手手机对象，它是被观察者
        MyPhone phone=new MyPhone();

        // 创建所有交易市场对象，他们是观察者
        Market1 m1=new Market1();
        Market2 m2=new Market2();

        // 注册观察者
        phone.registerObserver(m1);
        phone.registerObserver(m2);

        // 发布出售信息
        phone.release();

        // 假设此时手机已经在市场2被抢购了，此时需要在市场1、3发布信息
        System.out.println("--------------------");
        phone.removeObserver(m2);
        phone.remove();
        phone.registerObserver(m2);
    }
}
