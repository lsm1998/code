import com.google.gson.Gson;
import com.lsm1998.util.json.Json;
import com.lsm1998.util.json.JsonObject;
import com.lsm1998.util.json.MyJson;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;

public class JsonTest
{
    @Test
    public void jsonTest()
    {
        Gson gson = new Gson();
        System.out.println(gson.toJson((Object) null));

        MyJson myJson = new MyJson();
        System.out.println(myJson.toJson(Student.builder().age(11).name("lsm").build()));

        String json = "{\n" +
                "\"employees\": [\n" +
                "{ \"firstName\":\"Bill\" , \"lastName\":\"Gates\" },\n" +
                "{ \"firstName\":\"George\" , \"lastName\":\"Bush\" },\n" +
                "{ \"firstName\":\"Thomas\" , \"lastName\":\"Carter\" }\n" +
                "],\"id\":-10.5,\"success\":true\n" +
                "}";
        JsonObject jsonObject = Json.parseJsonObject(json);
        System.out.println(jsonObject);
    }

    @Data
    @Builder
    static class Student
    {
        private long id;
        private String name;
        private int age;
    }
}
