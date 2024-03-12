package com.example.yoyo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName user
 */

@Data
public class UserDto implements Serializable {
    /**
     * "用户id"
     */
    private Integer id;

    /**
     * "用户名"
     */
    private String passwordNew;
    private Integer type;

    /**
     * "用户密码，加密后的密码"
     */
    private String password;

    /**
     * "用户的真实姓名，可以作为默认收货人"
     */
    private String name;

    /**
     * "用户手机号，可以作为默认收货人手机号"
     */
    private String phone;

    /**
     * "用户住址，可以作为默认收货地址"
     */
    private String address;




}