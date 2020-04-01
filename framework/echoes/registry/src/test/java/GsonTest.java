import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lsm1998.echoes.registry.bean.AppReport;

import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 15:05
 **/
public class GsonTest {

    public static void main(String[] args) {
        Gson gson=new Gson();
        // [{"address":"127.0.0.2","port":9000,"uid":"6ae7301f-58f0-4d45-a84e-73a2a626c349"},{"address":"127.0.0.2","port":9001,"uid":"c66ecba2-e08e-417c-b61c-3fcfa732e65b"},{"address":"127.0.0.2","port":9000,"uid":"f1f397cd-0436-41d3-b781-10556d581555"},{"address":"127.0.0.2","port":9001,"uid":"26486b2b-1555-44e6-a43e-ae8ff9b604b4"},{"address":"127.0.0.2","port":9000,"uid":"30caf302-4e8a-42f0-b9be-16beb5aa8761"},{"address":"127.0.0.2","port":9001,"uid":"80f80c22-5e91-4285-9ab8-8d8d07a310d4"},{"address":"127.0.0.2","port":9000,"uid":"cd277771-8787-4beb-8a24-9556616ed206"},{"address":"127.0.0.2","port":9001,"uid":"101c96af-6261-4295-8b0e-bece45a400c3"},{"address":"127.0.0.2","port":9000,"uid":"7224df37-7d85-4176-b413-d28110405ca4"},{"address":"127.0.0.2","port":9001,"uid":"36f9566c-a996-4bb2-9444-0ad4f61560a9"},{"address":"127.0.0.2","port":9000,"uid":"58f799f9-3639-493a-98e2-a2bdc0d50365"},{"address":"127.0.0.2","port":9001,"uid":"105c32f5-224f-4eaf-9b49-8ddef2d02f07"}]
        String str="[{\"address\":\"127.0.0.2\",\"port\":9000,\"uid\":\"6ae7301f-58f0-4d45-a84e-73a2a626c349\"},{\"address\":\"127.0.0.2\",\"port\":9001,\"uid\":\"c66ecba2-e08e-417c-b61c-3fcfa732e65b\"},{\"address\":\"127.0.0.2\",\"port\":9000,\"uid\":\"f1f397cd-0436-41d3-b781-10556d581555\"},{\"address\":\"127.0.0.2\",\"port\":9001,\"uid\":\"26486b2b-1555-44e6-a43e-ae8ff9b604b4\"},{\"address\":\"127.0.0.2\",\"port\":9000,\"uid\":\"30caf302-4e8a-42f0-b9be-16beb5aa8761\"},{\"address\":\"127.0.0.2\",\"port\":9001,\"uid\":\"80f80c22-5e91-4285-9ab8-8d8d07a310d4\"}]";
        List<AppReport> list = gson.fromJson(str, new TypeToken<List<AppReport>>() {}.getType());
        System.out.println(list);
    }
}
