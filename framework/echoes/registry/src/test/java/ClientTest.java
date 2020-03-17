/*
 * 作者：刘时明
 * 时间：2020/3/8-18:05
 * 作用：
 */

import com.lsm1998.echoes.registry.client.RegistryClient;

import java.io.IOException;

public class ClientTest
{
    public static void main(String[] args) throws IOException
    {
        RegistryClient client=new RegistryClient();
        client.registryApp("haha","123",11);
        client.registryApp("haha","123",22);

        System.out.println(client.queryApp("haha"));
    }
}
