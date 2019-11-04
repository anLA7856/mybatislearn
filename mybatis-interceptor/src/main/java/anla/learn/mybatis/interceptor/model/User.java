package anla.learn.mybatis.interceptor.model;

import lombok.Data;

/**
 * @author luoan
 * @version 1.0
 * @date 2019/11/4 13:07
 **/
@Data
public class User {
    private Integer uid;

    private String email;

    private String password;

    private Integer actived;

    private String activateCode;

    private String joinTime;

    private String username;

    private String description;

    private String headUrl;

    private String position;

    private String school;

    private String job;

    private Integer likeCount;

    private Integer postCount;

    private Integer scanCount;

    private Integer followCount;

    private Integer followerCount;


}