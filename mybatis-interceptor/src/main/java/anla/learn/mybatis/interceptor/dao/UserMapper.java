package anla.learn.mybatis.interceptor.dao;

import anla.learn.mybatis.interceptor.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author luoan
 * @version 1.0
 * @date 2019/11/4 13:06
 **/
public interface UserMapper {
    /**
     * 通过id获取
     * @param id
     * @return
     */
    User getByIndex(@Param("id") Integer id);
}
