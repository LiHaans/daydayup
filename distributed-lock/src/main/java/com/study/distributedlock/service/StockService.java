package com.study.distributedlock.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.distributedlock.entity.Stock;
import com.study.distributedlock.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class StockService extends ServiceImpl<StockMapper, Stock> {

    ReentrantLock lock = new ReentrantLock();

    @Resource
    private StockMapper stockMapper;


    public void lock() {

        lock.lock();

        try {
            // 1. 查询库存
            Stock stock = stockMapper.selectById(1);

            if (stock != null && stock.getCount() >= 1) {
                // 2. 减库存
                stock.setCount(stock.getCount() - 1);

                stockMapper.updateById(stock);
                log.info("库存余量: {}", stock.getCount());
            } else {
                log.info("库存不足: {}", stock == null ? "null" : stock.getCount());
            }
        } finally {
            lock.unlock();
        }


    }
}
