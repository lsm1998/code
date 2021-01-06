package com.lsm1998.es;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class A
{
    public static void main(String[] args) throws Exception
    {
        URL u=new URL("http://localhost:8080/a.txt");

        InputStream inputStream = u.openConnection().getInputStream();
        int len=0;
        byte[] b=new byte[1024];
        FileOutputStream fis=new FileOutputStream("12.txt");
        while ((len = inputStream.read(b)) > 0)
        {
            fis.write(b,0,len);
            System.out.println(new String(b,0,len));
        }
    }
}
