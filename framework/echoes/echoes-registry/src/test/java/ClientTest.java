import com.lsm1998.echoes.registry.client.RegistryClient;

public class ClientTest
{
    public static void main(String[] args) throws Exception
    {
        RegistryClient client = new RegistryClient("127.0.0.1", 12221);

        client.registry("demo",123);

        client.registry("demo",125);

        // Thread.sleep(1000 * 2);

        System.out.println(client.get("demo"));
    }
}
