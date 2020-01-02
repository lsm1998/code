/**
 * 作者：刘时明
 * 时间：2019/11/2-16:08
 * 作用：
 */
package com.lsm1998.chat.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class MD5Util
{
    public static final int SALT_LEN = 10;

    public static String md5(String str)
    {
        try
        {
            MessageDigest m;
            m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes(), 0, str.length());
            String md5Val = new BigInteger(1, m.digest()).toString(16);
            return md5Val;
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String salt()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, SALT_LEN);
    }

    public static String password(String password, String salt)
    {
        if (password.length() < 10)
        {
            return md5(password);
        }
        char[] charArr = new char[password.length() + salt.length()];
        int index = 0;
        for (int i = 0; i < password.length(); i++)
        {
            if (i % 2 == 0 && i <= 10)
            {
                charArr[index++] = salt.charAt(i);
            } else
            {
                charArr[index++] = password.charAt(i);
            }
        }
        return md5(new String(charArr));
    }
}
