package design.factory.abstracts;

import design.factory.Car;
import design.factory.CarLog;

public interface AbstractFactory
{
    Car createCar(CarLog log);
}
