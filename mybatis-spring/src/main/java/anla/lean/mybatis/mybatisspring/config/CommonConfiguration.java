package anla.lean.mybatis.mybatisspring.config;

import anla.lean.mybatis.mybatisspring.model.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author anLA7856
 * @date 20-1-12 下午10:42
 * @description
 */
@Service
public class CommonConfiguration {

    @Bean
    public Dog dog(){
        return new Dog();
    }
}
