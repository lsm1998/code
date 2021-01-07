package design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkInvocationHandler implements InvocationHandler
{
    // 需要被代理的对象
    private Object object;

    public void setObj(Object object)
    {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        TxDB txDB = new TxDB();
        txDB.beginTx(object);
        Object result = method.invoke(object, args);
        txDB.endTx(object);
        return result;
    }
}
