package cn.golaxy.scheduler;

import cn.golaxy.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class TestScheduler {

    @Value("${spring.application.name}")
    private String name;

    //@Scheduled(fixedDelay= 1000 * 5)
    public void test() {

        String tmpDir = FileUtils.createTmpDir();
        System.out.println("tmpDir: " + tmpDir);
        System.out.println(name + " 执行");
    }
}
