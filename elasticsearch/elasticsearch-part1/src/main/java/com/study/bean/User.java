package com.golaxy.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: lihang
 * @Date: 2021-05-12 21:20
 * @Description:
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;
    private Integer age;
    private String sex;

}
