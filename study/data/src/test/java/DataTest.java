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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    public void test() throws Exception {
        Connection connection = dataSource.getConnection();
//        String sql = "show tables";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getObject(1));
//        }
        System.out.println(connection);
    }

    @Test
    public void read() {
//        User user=new User();
//        user.setId(2L);
//        user.setName("value");
//        userService.saveUser(user);
        System.out.println(userService.getList());
    }
}
