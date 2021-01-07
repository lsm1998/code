package design.factory.abstracts;

import design.factory.Car;
import design.factory.CarLog;
import design.factory.car.Mazda;

public class MazdaFactory implements AbstractFactory
{
    @Override
    public Car createCar(CarLog log)
    {
        return new Mazda();
    }
}
