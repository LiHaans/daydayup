package com.study.distributedlock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.distributedlock.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
}
