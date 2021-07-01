package com.study.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: lihang
 * @Date: 2021-07-01 14:33
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;  // 姓名
    private int salary; // 薪资
    private int age; // 年龄
    private String sex; //性别
    private String area;  // 地区

}
