import com.lsm1998.mp.MpApplication;
import com.lsm1998.mp.domain.User;
import com.lsm1998.mp.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 作者：刘时明
 * 时间：2021/2/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MpApplication.class)
public class MpTest
{
    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAll()
    {
        List<User> userList = userMapper.findAll();
        System.out.println(userList);
    }
}
