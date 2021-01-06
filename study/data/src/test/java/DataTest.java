import com.lsm1998.data.DataApplication;
import com.lsm1998.data.domain.User;
import com.lsm1998.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-07-13 09:04
 **/
@SpringBootTest(classes = DataApplication.class)
@RunWith(SpringRunner.class)
public class DataTest {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("slaveDataSource")
    private DataSource dataSource;

    @Test
    public void test() throws Exception
    {
        read();
        write();
    }

    @Test
    public void read()
    {
        System.out.println(userService.getList());
    }

    @Test
    public void write(){
        User user = new User();
        user.setId(null);
        user.setName("fuck");
        userService.saveUser(user);
    }
}
