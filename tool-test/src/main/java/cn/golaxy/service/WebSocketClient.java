package cn.golaxy.service;



import cn.golaxy.feign.HttpFeignClient;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.net.URI;
import java.util.Arrays;


@Slf4j
@ClientEndpoint
public class WebSocketClient {

    private HttpFeignClient httpFeignClient;

    private String url;

    private String tableName;

    public WebSocketClient() {
    }

    public WebSocketClient(String url, String tableName, HttpFeignClient httpFeignClient) {
        this.url = url;
        this.tableName = tableName;
        this.httpFeignClient = httpFeignClient;
    }



    @OnOpen
    public void onOpen(Session session) {

        session.setMaxIdleTimeout(Long.MAX_VALUE / 10);
        session.setMaxBinaryMessageBufferSize(Integer.MAX_VALUE / 10);
        session.setMaxTextMessageBufferSize(Integer.MAX_VALUE / 10);
        log.info("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(byte[] str) {

       /* System.out.println("----->byte:" + str.length);
        onClose();*/
    }

    @OnMessage
    public void onMessage(Session session, String str) {
        try {
            //数据获取
            JSONObject data = JSONObject.parseObject(str);
            log.info("接收到数据: {}", data);
            JSONObject result = httpFeignClient.sendData(URI.create(url), tableName, Arrays.asList(data));

            log.info("发送数据: {}", result);
        } catch (Exception e) {
            log.error("发送数据异常: {}", e.getMessage());
        }

    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }


    @OnClose
    public void onClose() {
        log.info("关闭了链接。。。。。。。。。。");
    }


    public HttpFeignClient getHttpFeignClient() {
        return httpFeignClient;
    }

    public void setHttpFeignClient(HttpFeignClient httpFeignClient) {
        this.httpFeignClient = httpFeignClient;
    }
}
