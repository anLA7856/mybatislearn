package anla.learn.mybatis.interceptor.model;

import lombok.Data;

/**
 *
 * ALTER TABLE `df`.`user`
 * ADD COLUMN `department_id` bigint(0) NULL COMMENT 'department id' AFTER `description`;
 *
 *
 *
 * create table department (
 *   id bigint primary key auto_increment,
 *   name varchar(20)
 * );
 * 用于测试懒加载
 * @author luoan
 * @version 1.0
 * @date 2019/11/15 17:08
 **/
@Data
public class Department {
    private Long id;
    private String name;
}
