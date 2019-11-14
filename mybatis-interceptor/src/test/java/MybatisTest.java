import anla.learn.mybatis.interceptor.dao.UserMapper;
import anla.learn.mybatis.interceptor.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author anLA7856
 * @date 19-11-13 下午9:56
 * @description
 */
@Slf4j
public class MybatisTest {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory =
            new SqlSessionFactoryBuilder().build(inputStream);

    public MybatisTest() throws IOException {
    }

    @Test
    public void testSelectAndIf() {
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Integer actived = 1;
        List<Integer> list = Arrays.asList(1, 2);
        List<User> user = mapper.listAllActivedUsers(actived, list);
        log.info("user :{}", user);
    }

    public void insertNewOne(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setDescription("test desc");
        user.setSchool("test school");
        user.setActivateCode("test activated code");
        user.setActived(0);
        user.setEmail("test@test.test.test");
        user.setFollowCount(1);
        user.setFollowerCount(12);
        user.setHeadUrl("no url");
        user.setJob("test job");
        user.setJoinTime("not time");
        user.setLikeCount(3);
        user.setScanCount(21);
        mapper.addNewOne(user);
        log.info("user :{}", user);
    }
}
