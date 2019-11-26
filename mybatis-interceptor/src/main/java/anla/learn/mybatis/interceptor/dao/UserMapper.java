package anla.learn.mybatis.interceptor.dao;

import anla.learn.mybatis.interceptor.model.User;
import anla.learn.mybatis.interceptor.model.UserLazyDepartment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
     * 获取所有
     * @return
     */
    @Select("select * from user")
    List<User> getAllUsers();

    /**
     * 懒加载
     * @param id
     * @return
     */
    UserLazyDepartment getByIndexLazy(@Param("id") int id);

    /**
     * 更新
     * @param id
     * @param desc
     * @return
     */
    @Update("update user set description = #{desc} where uid=#{id}")
    int updateById(@Param("id") int id,@Param("desc") String desc);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from user where uid = #{id}")
    int deleteById(@Param("id") int id);

    /**
     * 不用处理pageNum， 和 pageSize，而 pageHelper 会处理
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<User> getPageUsers(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
