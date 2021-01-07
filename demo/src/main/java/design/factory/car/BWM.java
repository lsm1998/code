package design.factory.car;

import design.factory.Car;
import design.factory.CarLog;

public class BWM implements Car
{
    private final CarLog log;

    public BWM()
    {
        this.log = CarLog.BWM;
    }

    @Override
    public String getCarName()
    {
        return this.log.getValue();
    }
}
