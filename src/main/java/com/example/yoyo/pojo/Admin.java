package com.example.yoyo.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName admin
 */
@Data
public class Admin implements Serializable {
    /**
     * "管理员id"
     */
    private Integer id;

    /**
     * "用户名"
     */
    private String username;

    /**
     * "密码"
     */
    private String password;

    private static final long serialVersionUID = 1L;
}