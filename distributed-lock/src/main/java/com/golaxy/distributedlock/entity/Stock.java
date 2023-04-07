package com.golaxy.distributedlock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_stock")
public class Stock {

    @TableId("id")
    private Integer id;

    private String productCode;

    private String stockCode;

    private Long count;
}
