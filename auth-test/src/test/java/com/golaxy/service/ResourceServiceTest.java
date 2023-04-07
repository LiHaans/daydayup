package com.golaxy.service;

import com.golaxy.MainApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@Slf4j
public class ResourceServiceTest {
    @Resource
    ResourceService resourceService;


    @Test
    public void copyResourceTest() {
        resourceService.copyResource();
    }
}
