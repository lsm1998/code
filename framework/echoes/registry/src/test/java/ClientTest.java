/*
 * 作者：刘时明
 * 时间：2020/3/8-18:05
 * 作用：
 */

import com.lsm1998.net.echoes.registry.client.RegistryClient;

import java.io.IOException;

public class ClientTest
{
    public static void main(String[] args) throws IOException
    {
        RegistryClient client=new RegistryClient();
        client.registryApp("user-service","127.0.0.2",9000);
        //client.registryApp("user-service","127.0.0.2",9001);
        //client.registryApp("order-service","127.0.0.2",8000);
        System.out.println(client.queryApp("user-service"));
    }
}
