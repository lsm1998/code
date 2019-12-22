/*
 * 作者：刘时明
 * 时间：2019/12/21-16:19
 * 作用：
 */
package com.test.dto;

import lombok.Data;

@Data
public class UserDto
{
    private Integer id;
    private String username;
    private String nickname;
    private String password;
}
