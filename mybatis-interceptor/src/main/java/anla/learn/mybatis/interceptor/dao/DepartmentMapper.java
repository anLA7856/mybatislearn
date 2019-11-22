package anla.learn.mybatis.interceptor.dao;

import anla.learn.mybatis.interceptor.model.Department;
import org.apache.ibatis.annotations.Param;

/**
 * @author luoan
 * @version 1.0
 * @date 2019/11/15 17:15
 **/
public interface DepartmentMapper {


    /**
     * 根据id获取某一个
     * @param id
     * @return
     */
    Department getByIndex(@Param("id") int id);

    /**
     * 使用 mybatis 自增主键创建。
     * @param department
     * @return
     */
    int insertWithGenertoorKey(@Param("department") Department department);
}
