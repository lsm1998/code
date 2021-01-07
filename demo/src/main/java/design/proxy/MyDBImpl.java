package design.proxy;

import java.util.List;

public class MyDBImpl implements MyDB
{
    @Override
    public void insert(Object o)
    {
        System.out.println("写入数据：" + o);
    }

    @Override
    public List<Object> select()
    {
        System.out.println("查询数据");
        return List.of(1, 2, 3, 4);
    }
}
