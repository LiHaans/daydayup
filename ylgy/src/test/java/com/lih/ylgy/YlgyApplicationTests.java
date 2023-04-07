package com.lih.ylgy;


import com.alibaba.fastjson.JSONObject;
import com.lih.ylgy.feign.YlgyFeign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YlgyApplication.class)
@Slf4j
public class YlgyApplicationTests {

    @Resource
    private YlgyFeign ylgyFeign;

    @Test
    public void test() {

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Monitor(ylgyFeign, countDownLatch)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
class Monitor extends Thread {

    private YlgyFeign ylgyFeign;
    CountDownLatch countDownLatch;
    @Override
    public void run() {


        String t = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQ0MDg3MDYsIm5iZiI6MTY2MzMwNjUwNiwiaWF0IjoxNjYzMzA0NzA2LCJqdGkiOiJDTTpjYXRfbWF0Y2g6bHQxMjM0NTYiLCJvcGVuX2lkIjoiIiwidWlkIjo4OTk3MDI5NSwiZGVidWciOiIiLCJsYW5nIjoiIn0.NYetKRi827bwguSmZH0uUk0jEWocqgD1upyUoBpFtmc";

        Integer success = 0;
        Integer count = 0;

        for (int i = 0; i < 1000; i++) {
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
