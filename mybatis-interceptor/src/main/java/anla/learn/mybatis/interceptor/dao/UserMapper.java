package anla.learn.mybatis.interceptor.dao;

import anla.learn.mybatis.interceptor.model.User;
import anla.learn.mybatis.interceptor.model.UserLazyDepartment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 用于更新 description 和 school，以id为更新变量
     * @param user
     * @return
     */
    Integer updateByUser(@Param("user") User user);

    /**
     * 根据条件，获取查询
     * @param actived
     * @param list
     * @return
     */
    List<User> listAllActivedUsers(@Param("actived") Integer actived, @Param("list") List<Integer> list);

    /**
     * 增加记录
     * @param user
     * @return
     */
    Integer addNewOne(@Param("user") User user);

    /**
     * 通过uid获取一个
     * @param uid
     * @return
     */
    @Select("select * from user where uid = #{uid}")
    User getByAnnotationIndex(int uid);

    /**
     * 懒加载
     * @param id
     * @return
     */
    UserLazyDepartment getByIndexLazy(@Param("id") int id);
}
