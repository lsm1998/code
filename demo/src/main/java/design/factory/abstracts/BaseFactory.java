package design.factory.abstracts;

import design.factory.Car;
import design.factory.CarLog;

public class BaseFactory implements AbstractFactory
{
    private static final BaseFactory factory = new BaseFactory();

    private BaseFactory()
    {
    }

    public static BaseFactory getInstance()
    {
        return factory;
    }

    @Override
    public Car createCar(CarLog log)
    {
        AbstractFactory factory = null;
        if (log == CarLog.BWM)
        {
            factory = new BWMFactory();
        } else if (log == CarLog.Benz)
        {
            factory = new BenzFactory();
        } else if (log == CarLog.Mazda)
        {
            factory = new MazdaFactory();
        }
        if (factory != null)
        {
            return factory.createCar(log);
        }
        return null;
    }
}
