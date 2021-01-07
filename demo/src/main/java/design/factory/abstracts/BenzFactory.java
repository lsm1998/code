package design.factory.abstracts;

import design.factory.Car;
import design.factory.CarLog;
import design.factory.car.Benz;

public class BenzFactory implements AbstractFactory
{
    @Override
    public Car createCar(CarLog log)
    {
        return new Benz();
    }
}
