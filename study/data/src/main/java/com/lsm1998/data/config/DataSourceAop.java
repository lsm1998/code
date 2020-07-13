package com.lsm1998.data.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop
{
    /**
     * 使用从库查询
     */
    @Pointcut("@annotation(com.lsm1998.data.config.Slave) " +
            "|| execution(* com.lsm1998.data..service..*.select*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.list*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.query*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.find*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.get*(..))")
    public void readPointcut()
    {

    }

    /**
     * 使用主库插入更新
     */
    @Pointcut("@annotation(com.lsm1998.data.config.Master) " +
            "|| execution(* com.lsm1998.data..service..*.login(..)) " +
            "|| execution(* com.lsm1998.data..service..*.insert*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.add*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.update*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.edit*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.delete*(..)) " +
            "|| execution(* com.lsm1998.data..service..*.remove*(..))")
    public void writePointcut()
    {

    }

    @Before("readPointcut()")
    public void read()
    {
        System.out.println("切换至读库");
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write()
    {
        System.out.println("切换至写库");
        DBContextHolder.master();
    }
}
