package anla.lean.mybatis.mybatisspring.model;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

/**
 * @author anLA7856
 * @date 20-1-12 下午10:32
 * @description
 */
@Data
public class Dog implements Serializable {
    private String name;

    public String wangwang(){
        return "Hi I am wangwang";
    }

    public String afterWangwang(){
        return "It's a Wangwang that ?";
    }


    /**
     * 用于测试Spring AOP
     * @return
     */
    public String hello(){
        return "hello";
    }
}
