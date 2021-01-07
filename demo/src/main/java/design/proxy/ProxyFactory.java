package design.proxy;

import java.lang.reflect.Proxy;

public class ProxyFactory
{
    public static Object getProxy(Object target)
    {
        JdkInvocationHandler handler = new JdkInvocationHandler();
        // 设置代理对象
        handler.setObj(target);
        // 传入的参数分别是：当前类加载器，target类的父接口Class对象数组，JDK动态代理的处理类
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }
}
