package cn.golaxy.init;

import cn.golaxy.service.HttpRequestService;
import cn.golaxy.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class Init implements CommandLineRunner {
    @Value("${request.type}")
    private String requestType;

    @Value("${request.appKey}")
    private String appKey;

    @Value("${request.url}")
    private String requestUrl;

    @Value("${response.url}")
    private String responseUrl;

    @Value("${response.tableName}")
    private String tableName;

    @Resource
    private HttpRequestService httpRequestService;

    @Resource
    private WebSocketService webSocketService;


    @Override
    public void run(String... args) throws Exception {
        if ("http".equals(requestType)) {
            httpRequestService.queryData(requestUrl, appKey, responseUrl, tableName);
        } else if ("websocket".equals(requestType)) {
            webSocketService.consumer(requestUrl, appKey, responseUrl, tableName);
        }
    }
}
