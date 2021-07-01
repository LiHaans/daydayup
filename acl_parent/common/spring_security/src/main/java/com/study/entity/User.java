package com.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: lihang
 * @Date: 2021-06-29 21:17
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;
    private String nickName;
    private String salt;
    private String token;
}
