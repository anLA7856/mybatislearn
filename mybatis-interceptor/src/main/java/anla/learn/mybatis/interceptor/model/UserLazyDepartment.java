package anla.learn.mybatis.interceptor.model;

import lombok.Data;

/**
 *  懒加载封装对象
 * @author luoan
 * @version 1.0
 * @date 2019/11/15 17:18
 **/

public class UserLazyDepartment {
    private Integer uid;

    private String email;

    private String password;

    private Long departmentId;

    private Department department;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
