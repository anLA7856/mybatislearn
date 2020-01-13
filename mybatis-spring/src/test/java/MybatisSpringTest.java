import anla.lean.mybatis.mybatisspring.config.*;
import anla.lean.mybatis.mybatisspring.dao.UserMapper;
import anla.lean.mybatis.mybatisspring.model.User;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author luoan
 * @version 1.0
 * @date 2020/1/11 17:04
 **/
@Slf4j
public class MybatisSpringTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setupContext() {
        applicationContext = new AnnotationConfigApplicationContext();

        setupSqlSessionFactory();

        // assume support for autowiring fields is added by MapperScannerConfigurer
        // via
        // org.springframework.context.annotation.ClassPathBeanDefinitionScanner.includeAnnotationConfig
    }


    @AfterEach
    void assertNoMapperClass() {
        try {
            // concrete classes should always be ignored by MapperScannerPostProcessor
            assertBeanNotLoaded("mapperClass");

            // no method interfaces should be ignored too
            assertBeanNotLoaded("package-info");
            // assertBeanNotLoaded("annotatedMapperZeroMethods"); // as of 1.1.0 mappers
            // with no methods are loaded
        } finally {
            applicationContext.close();
        }
    }

    private void assertBeanNotLoaded(String name) {
        try {
            applicationContext.getBean(name);
            fail("Spring bean should not be defined for class " + name);
        } catch (NoSuchBeanDefinitionException nsbde) {
            // success
        }
    }

    private void setupSqlSessionFactory() {
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(SqlSessionFactoryBean.class);
        definition.getPropertyValues().add("dataSource",  getDruidDataSource());
//        definition.getPropertyValues().add("dataSource", getDruidDataSource());
        applicationContext.registerBeanDefinition("sqlSessionFactory", definition);
    }

    /**
     * 期间有个很玄幻问题，就是一致无法通过 dataSource.getConnection 获取连接
     * 发现是 mysql 版本问题，即 mysql8 的版本，需要用mysql8的connector，不能用mysql 5 的链接。
     * @return
     */
    private Object getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1/df?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setMaxWait(2000);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    private void startContext() {
        applicationContext.refresh();
        applicationContext.start();

        // this will throw an exception if the beans cannot be found
        applicationContext.getBean("sqlSessionFactory");
    }


    // 内部类
    @Configuration
    @MapperScan("anla.lean.mybatis.mybatisspring.dao")
    public static class AppConfigWithPackageScan {
    }

    // 以下包含两个测试用例
    // 1. MapperScan
    // 2. 多种类型bean 注册。

    @Test
    void testInterfaceScan() {
        applicationContext.register(AppConfigWithPackageScan.class,
                DogBeanPostProcessor.class,
                CommonConfiguration.class,
                ApplicationListenerConfiguration.class,
                BeanFactoryPostProcessorConfiguration.class,
                BeanDefinitionRegistryPostProcessorConfiguration.class);
        // 这样方式，就可以将 BeanFactoryPostProcessor 放到  PostProcessorRegistrationDelegate 中执行
        applicationContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessorConfiguration());
        applicationContext.addBeanFactoryPostProcessor(new BeanDefinitionRegistryPostProcessorConfiguration());

        startContext();

        // all interfaces with methods should be loaded
        applicationContext.getBean("userMapper");

        UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);
        List<User> list =  userMapper.getAllUsers();
        System.out.println("list, " + list);
        log.info("list:{}", list);
    }


}
