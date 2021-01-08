package com.lsm1998.algorithm.base;

import org.junit.Test;

public class StringFindTest
{
    @Test
    public void stringFindTest()
    {
        System.out.println(StringFind.indexOf("hello", "lo"));

        System.out.println(StringFind.indexOf("hello", "lw"));

        System.out.println(StringFind.indexOf("hello", "h"));

        System.out.println(StringFind.indexOf("Arctic Code Vault Contributor", "ibuto"));

        System.out.println(StringFind.indexOf("Arctic Code Vault Contributor", "fuck"));
    }
}
