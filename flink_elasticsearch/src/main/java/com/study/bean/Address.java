package com.golaxy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Auther: lihang
 * @Date: 2021-05-27 21:00
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    private Integer id;
    private String province;
    private String city;
}
