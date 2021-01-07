package design.factory.car;

import design.factory.Car;
import design.factory.CarLog;

public class Benz implements Car
{
    private final CarLog log;

    public Benz()
    {
        this.log = CarLog.Benz;
    }

    @Override
    public String getCarName()
    {
        return this.log.getValue();
    }
}
