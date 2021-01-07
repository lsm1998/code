package design.proxy;

import java.util.List;

public interface MyDB
{
    void insert(Object o);

    List<Object> select();
}
