import com.lsm1998.sharding.ShardingApplication;
import com.lsm1998.sharding.entity.TOrder;
import com.lsm1998.sharding.mapper.TOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes= ShardingApplication.class)
@RunWith(SpringRunner.class)
public class ShardingTest
{
    @Autowired
    private TOrderMapper orderMapper;

    @Test
    public void insert()
    {
        System.out.println("hello");
        TOrder order=new TOrder();
        order.setOrderId(System.currentTimeMillis());
        order.setUserId(1);
        order.setRemake("Remake");
        orderMapper.insert(order);
        System.out.println("orderMapper="+orderMapper);
    }

    @Test
    public void select()
    {
        List<TOrder> list = orderMapper.selectByMap(null);
        System.out.println(list);
    }
}
