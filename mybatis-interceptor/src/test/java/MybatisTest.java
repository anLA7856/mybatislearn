import anla.learn.mybatis.interceptor.dao.UserMapper;
import anla.learn.mybatis.interceptor.model.Department;
import anla.learn.mybatis.interceptor.model.User;
import anla.learn.mybatis.interceptor.model.UserLazyDepartment;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
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

    @Test
    public void testSelectByAnnotation(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int uid = 1;
        User user = mapper.getByAnnotationIndex(uid);
        log.info("user :{}", user);
    }

    @Test
    public void testInsertNewOne(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setEmail("test@test.test.test");
        user.setPassword("test password");
        user.setActived(0);
        user.setActivateCode("test activated code");
        user.setJoinTime("not time");
        user.setUsername("test username");
        user.setHeadUrl("no url");
        user.setPosition("test position");
        user.setSchool("test school");
        user.setJob("test job");
        user.setLikeCount(3);
        user.setPostCount(45);
        user.setScanCount(21);
        user.setFollowCount(1);
        user.setFollowerCount(12);
        user.setDescription("test desc");
        int result = mapper.addNewOne(user);
        log.info("user :{}", user);
    }

    @Test
    public void testLazySelect(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int id = 1;
        UserLazyDepartment userLazyDepartment = mapper.getByIndexLazy(id);
        log.info("user :{}", userLazyDepartment);
        log.info("user :{}", userLazyDepartment.getDepartment());
    }


    @Test
    public void testLazySelectModifyLazyLoadTriggerMethods(){
        SqlSession session = sqlSessionFactory.openSession(true);
        session.getConfiguration().getLazyLoadTriggerMethods().clear();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int id = 1;
        UserLazyDepartment userLazyDepartment = mapper.getByIndexLazy(id);
        log.info("user :{}", userLazyDepartment);
        log.info("user :{}", userLazyDepartment.getDepartment());
//        session.commit();
    }

    @Test
    public void testUpdate(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int id = 1;
        String desc = "123123";
        int count = mapper.updateById(id, desc);
        log.info("count:{}", count);
        session.commit();
    }
    @Test
    public void testDelete(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int id = 1;
        String desc = "123123";
        int count = mapper.deleteById(id);
        log.info("count:{}", count);
    }



    @Test
    public void testKeyGenerator(){
        SqlSession session = sqlSessionFactory.openSession(true);
        DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
        Department department = new Department();
        department.setName("自增部门");
        int count = mapper.insertWithGenertoorKey(department);
        log.info("count :{}", count);
        log.info("department:{}", department);
    }

    @Test
    public void testKeyGeneratorSelectKey(){
        SqlSession session = sqlSessionFactory.openSession(true);
        DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
        Department department = new Department();
        department.setName("自增部门 加 100 ？");
        int count = mapper.insertWithSelectKeyGenertoorKey(department);
        log.info("count :{}", count);
        log.info("department:{}", department);
    }


    @Test
    public void testJdbc3PrepareStatement(){
        try {
            String url = "jdbc:mysql://localhost:3306/df?serverTimezone=GMT";
            String sql = "INSERT INTO department(name) VALUES (?)";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "root", "123456");
            String[] columnNames = {"ids", "name"};
            PreparedStatement stmt = conn.prepareStatement(sql, columnNames);
            stmt.setString(1, "test jdbc3 ");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
                System.out.println("----------" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testPlugin() throws IOException {
        String resource = "mybatis-config-plugin.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Integer actived = 1;
        List<Integer> list = Arrays.asList(1, 2);
        List<User> user = mapper.listAllActivedUsers(actived, list);
        log.info("user :{}", user);
    }

}
