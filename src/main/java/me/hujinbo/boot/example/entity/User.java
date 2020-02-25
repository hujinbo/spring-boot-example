package me.hujinbo.boot.example.entity;

import lombok.Data;

/**
 * User实体类
 *
 * @author hujinbo
 */
@Data
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;
}
