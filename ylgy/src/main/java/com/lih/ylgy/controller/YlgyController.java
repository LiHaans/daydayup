package com.lih.ylgy.controller;

import com.alibaba.fastjson.JSONObject;
import com.lih.ylgy.feign.YlgyFeign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@RestController
public class YlgyController {

    @Resource
    private YlgyFeign ylgyFeign;

    @GetMapping("ylgy/test")
    public JSONObject test() {
        JSONObject result = new JSONObject();

        result.put("success", true);
        result.put("msg", "成功");
        return result;
    }

    @GetMapping("ylgy/gameover")
    public JSONObject gameover(@RequestParam("t") String t, @RequestParam("count") Integer count) {
        JSONObject result = new JSONObject();
        if (count == null || count < 10 || StringUtils.isBlank(t)) {
            result.put("success", false);
            result.put("msg", "参数错误");
            return result;
        }

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int num = count / 10;
            if (i == 9) {
                if ((count % 10) == 0) {
                    num = count / 10;
                } else {
                    num = count / 10 + count % 10;
                }
            }
            new Thread(new Monitor(ylgyFeign, countDownLatch, t, num)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result.put("success", true);
        result.put("msg", "成功");
        return result;
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
class Monitor extends Thread {

    private YlgyFeign ylgyFeign;
    CountDownLatch countDownLatch;
    private String t;
    private Integer num;

    @Override
    public void run() {

        Integer success = 0;
        Integer count = 0;

        for (int i = 0; i < num; i++) {
            JSONObject gameover = null;
            try {
                count += 1;
                gameover = ylgyFeign.gameover(t, 1, 1, 0, 1, 1);
                if (gameover != null && gameover.getInteger("data") == 0) {
                    success += 1;
                }
                log.info("success: {}, count: {}, result: {}", success, count, gameover);

            } catch (Exception e) {
                log.error("error: {}", e.getMessage());
            }

        }

        countDownLatch.countDown();
    }

}

