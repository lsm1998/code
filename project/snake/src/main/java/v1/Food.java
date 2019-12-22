package v1;

public class Food
{
    Snake snake;
    int foodx;
    int foody;
    int foodSize = 25;

    public Food()
    {
        addFood();
    }

    public void addFood()
    {
        foodx = (int) (Math.random() * 32) * 25;
        foody = (int) (Math.random() * 24) * 25;
    }

}
