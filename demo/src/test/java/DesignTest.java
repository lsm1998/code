import design.command.AddCommand;
import design.command.TargetArray;
import design.factory.CarFactory;
import design.factory.CarLog;
import design.factory.abstracts.AbstractFactory;
import design.factory.abstracts.BaseFactory;
import design.observer.Market1;
import design.observer.Market2;
import design.observer.MyPhone;
import design.proxy.MyDB;
import design.proxy.MyDBImpl;
import design.proxy.ProxyFactory;
import org.junit.jupiter.api.Test;

public class DesignTest
{
    /**
     * 工厂
     */
    @Test
    public void factoryTest()
    {
        CarFactory factory = CarFactory.getInstance();
        System.out.println(factory.getCar(CarLog.Mazda).getCarName());
        System.out.println(factory.getCar(CarLog.Benz).getCarName());
    }

    /**
     * 抽象工厂
     */
    @Test
    public void abstractFactoryTest()
    {
        AbstractFactory factory = BaseFactory.getInstance();
        System.out.println(factory.createCar(CarLog.Mazda).getCarName());
        System.out.println(factory.createCar(CarLog.Benz).getCarName());
    }

    /**
     * 观察者
     */
    @Test
    public void observerTest()
    {
        // 创建我的二手手机对象，它是被观察者
        MyPhone phone = new MyPhone();

        // 创建所有交易市场对象，他们是观察者
        Market1 m1 = new Market1();
        Market2 m2 = new Market2();

        // 注册观察者
        phone.registerObserver(m1);
        phone.registerObserver(m2);

        // 发布出售信息
        phone.release();

        // 假设此时手机已经在市场2被抢购了，此时需要在市场1发布信息
        System.out.println("--------------------");
        phone.removeObserver(m2);

        // 发布售罄消息
        phone.remove();
        phone.registerObserver(m2);
    }

    /**
     * 代理模式
     */
    @Test
    public void proxyTest()
    {
        MyDB db = new MyDBImpl();
        MyDB proxyDB = (MyDB) ProxyFactory.getProxy(db);
        proxyDB.insert("lsm");
    }

    /**
     * 命令模式
     */
    @Test
    public void commandTest()
    {
        TargetArray array = new TargetArray();
        Integer sum = array.arrMethod(new Integer[]{1, 2, 3, 4, 5, 6}, new AddCommand());
        System.out.println(sum);
        Integer result = array.arrMethod(new Integer[]{1, 2, 3, 4, 5, 6}, target ->
        {
            int result1 = 1;
            for (Integer temp : target
            )
            {
                result1 *= temp;
            }
            return result1;
        });
        System.out.println(result);
    }
}
