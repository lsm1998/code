import com.lsm1998.echoes.common.net.EchoesServer;
import com.lsm1998.echoes.registry.RegistryStartBuild;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-06-24 10:05
 **/
public class Test
{
    public static void main(String[] args) throws Exception
    {
        EchoesServer start = new RegistryStartBuild().port(12200).build();
        start.start();
    }
}
