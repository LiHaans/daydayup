package com.study.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther: lihang
 * @Date: 2021-05-27 21:02
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Long id;
    private String name;
    private Boolean gender;
    private Date birth;
    private Address address;
    private String description;
}
