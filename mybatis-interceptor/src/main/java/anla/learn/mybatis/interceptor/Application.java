package anla.learn.mybatis.interceptor;

import anla.learn.mybatis.interceptor.dao.UserMapper;
import anla.learn.mybatis.interceptor.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author luoan
 * @version 1.0
 * @date 2019/11/4 12:59
 **/
@Slf4j
public class Application {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
//        try (SqlSession session = sqlSessionFactory.openSession()) {
//            User user = session.selectOne(
//                    "anla.learn.mybatis.interceptor.dao.UserMapper.getByIndex", 1);
//            log.info("user :{}", user);
//        }
//
//        try (SqlSession session = sqlSessionFactory.openSession()) {
//            UserMapper mapper = session.getMapper(UserMapper.class);
//            User user = mapper.getByIndex(1);
//            log.info("user :{}", user);
//        }

//        try (SqlSession session = sqlSessionFactory.openSession()) {
//            UserMapper mapper = session.getMapper(UserMapper.class);
//            User user = mapper.getByIndex(1);
//            log.info("user :{}", user);
//            Integer size = mapper.updateByUser(user);
//            User userNew = mapper.getByIndex(1);
//            log.info("user :{}", userNew);
//        }


//        try (SqlSession session = sqlSessionFactory.openSession()) {
//            UserMapper mapper = session.getMapper(UserMapper.class);
//            User user = mapper.getByIndex(11);
//            log.info("user :{}", user);
//            Integer size = mapper.updateByUser(user);
//            User userNew = mapper.getByIndex(1);
//            log.info("user :{}", userNew);
//        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getByIndex(11);
            log.info("user :{}", user);
        }
    }
}
