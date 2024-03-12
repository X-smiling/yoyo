package com.example.yoyo.dto;

import lombok.Data;

/**
 * @author : hgj
 * @date : 2024/1/14 - 22:46
 * @desc :
 */
@Data
public class AdminDto {

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
    private String passwordNew;
}
