package anla.lean.mybatis.mybatisspring.dao;

import anla.lean.mybatis.mybatisspring.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author luoan
 * @version 1.0
 * @date 2019/11/4 13:06
 **/
public interface UserMapper {

    /**
     * 获取所有
     * @return
     */
    @Select("select * from user")
    List<User> getAllUsers();

}
