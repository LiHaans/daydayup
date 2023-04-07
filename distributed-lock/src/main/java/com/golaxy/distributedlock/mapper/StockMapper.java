package com.golaxy.distributedlock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.golaxy.distributedlock.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
}
