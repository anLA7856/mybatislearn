import anla.learn.mybatis.interceptor.dao.UserMapper;
import anla.learn.mybatis.interceptor.model.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author anLA7856
 * @date 19-11-13 下午9:56
 * @description
 */
@Slf4j
public class MybatisPageHelperTest {
    String resource = "mybatis-config-pagehelper.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory =
            new SqlSessionFactoryBuilder().build(inputStream);

    public MybatisPageHelperTest() throws IOException {
    }

    /**
     * RowBounds方式的调用
     */
    @Test
    public void testRowBounds() {
        SqlSession session = sqlSessionFactory.openSession();
        List<User> list = session.selectList("anla.learn.mybatis.interceptor.dao.UserMapper.getAllUsers", null, new RowBounds(0, 10));
        log.info("user :{}", list);
    }

    /**
     * Mapper接口方式的调用，推荐这种使用方式。
     */
    @Test
    public void testStartPage(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        PageHelper.startPage(1, 10);
        List<User> list = mapper.getAllUsers();
        log.info("user :{}", list);
    }

    /**
     * Mapper接口方式的调用，推荐这种使用方式。
     */
    @Test
    public void testOffsetPage(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        PageHelper.offsetPage(1, 10);
        List<User> list = mapper.getAllUsers();
        log.info("user :{}", list);
    }

    /**
     * 存在以下 Mapper 接口方法，你不需要在 xml 处理后两个参数
     * 注意要配置 supportMethodsArguments
     */
    @Test
    public void testParamSelect(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int pageNum = 1;
        int pageSize = 10;
        List<User> list = mapper.getPageUsers(pageNum, pageSize);
        log.info("user :{}", list);
    }

    @Test
    public void testISelect(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Page<User> page = PageHelper.startPage(1, 10).doSelectPage(() -> mapper.getAllUsers());
        log.info("user :{}", page);
    }

    @Test
    public void testCount(){
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        long total = PageHelper.count(() -> mapper.getAllUsers());
        log.info("total :{}", total);
    }

}
