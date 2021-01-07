package design.factory.abstracts;

import design.factory.Car;
import design.factory.CarLog;
import design.factory.car.BWM;
import design.factory.car.Mazda;

public class BWMFactory implements AbstractFactory
{
    @Override
    public Car createCar(CarLog log)
    {
        return new BWM();
    }
}
