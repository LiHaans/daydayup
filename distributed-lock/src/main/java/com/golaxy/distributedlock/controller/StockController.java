package com.golaxy.distributedlock.controller;

import com.golaxy.distributedlock.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StockController {

    @Resource
    private StockService stockService;

    @GetMapping("check/lock")
    public String stock() {

        stockService.lock();
        return "success";
    }
}