import com.lsm1998.util.json.Json;
import com.lsm1998.util.json.JsonObject;

/**
 * @作者：刘时明
 * @时间：2019/5/24-17:10
 * @作用：
 */
public class Test
{
    public static void main(String[] args)
    {
        String json = "{}";
        JsonObject jsonObject = Json.parseJsonObject(json);

        System.out.println(jsonObject);
    }
}
