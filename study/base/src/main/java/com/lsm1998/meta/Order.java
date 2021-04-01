package com.lsm1998.meta;

import org.junit.Test;

public class Order implements IOrder
{
    @Override
    public void pay()
    {
        System.out.println("pay invoke!!!");
    }

    @Test
    public void testPay() throws Exception
    {
        IOrder order = Aspect.getProxy(Order.class, "com.lsm1998.aop.TimeUsageAspect");
        order.pay();
    }
}
