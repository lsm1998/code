package design.factory.car;

import design.factory.Car;
import design.factory.CarLog;

public class Mazda implements Car
{
    private final CarLog log;

    public Mazda()
    {
        this.log = CarLog.Mazda;
    }

    @Override
    public String getCarName()
    {
        return this.log.getValue();
    }
}
