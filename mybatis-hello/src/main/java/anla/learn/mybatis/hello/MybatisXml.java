package anla.learn.mybatis.hello;

import anla.learn.mybatis.hello.dao.auto.UserMapper;
import anla.learn.mybatis.hello.model.auto.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author anLA7856
 * @date 19-11-2 上午10:08
 * @description
 */
public class MybatisXml {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            User user = session.selectOne(
                    "anla.learn.mybatis.hello.dao.auto.UserMapper.selectUser", 1L);
            System.out.println(user);
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUser(1L);
            System.out.println(user);
        }
    }
}
