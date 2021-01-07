package design.factory;

import design.factory.car.BWM;
import design.factory.car.Benz;
import design.factory.car.Mazda;

public class CarFactory
{
    private static final CarFactory factory = new CarFactory();

    private CarFactory()
    {
    }

    public static CarFactory getInstance()
    {
        return factory;
    }

    public Car getCar(CarLog log)
    {
        if (log == CarLog.Mazda)
        {
            return new Mazda();
        } else if (log == CarLog.BWM)
        {
            return new BWM();
        } else if (log == CarLog.Benz)
        {
            return new Benz();
        } else
        {
            throw new RuntimeException("没有名为：" + log.getValue() + "的汽车");
        }
    }
}
