/*
 * 作者：刘时明
 * 时间：2020/3/8-23:25
 * 作用：
 */

import java.lang.reflect.Method;

public class Test
{
    public static void main(String[] args)
    {
        Class<?> c=Test.class;
        for (Method m:c.getMethods())
        {
            if(m.getName().equals("test"))
            {
                System.out.println(m.toGenericString());
                System.out.println(m.getAnnotatedReturnType());
                break;
            }
        }
    }

    public void test(String a)
    {}
}
