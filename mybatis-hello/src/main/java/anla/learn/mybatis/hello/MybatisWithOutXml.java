package anla.learn.mybatis.hello;

import anla.learn.mybatis.hello.dao.auto.UserMapper;
import anla.learn.mybatis.hello.model.auto.User;
import anla.learn.mybatis.hello.withoutxml.MyDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author anLA7856
 * @date 19-11-2 上午10:30
 * @description
 */
@Slf4j
public class MybatisWithOutXml {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("url", "jdbc:mysql://127.0.0.1/df");
        prop.setProperty("user", "root");
        prop.setProperty("password", "123456");

        MyDataSourceFactory myDataSourceFactory = new MyDataSourceFactory();
        myDataSourceFactory.setProperties(prop);

        DataSource dataSource = myDataSourceFactory.getDataSource();
        TransactionFactory transactionFactory =
                new JdbcTransactionFactory();
        Environment environment =
                new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(configuration);

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
