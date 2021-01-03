import com.lsm1998.echoes.registry.RegistryStart;
import com.lsm1998.echoes.registry.RegistryStartBuild;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-06-24 10:05
 **/
public class RegistryTest
{
    public static void main(String[] args) throws Exception
    {
        RegistryStart start = new RegistryStartBuild().port(12221).build();
        start.start();
    }
}
