package cn.golaxy.service;

import cn.golaxy.feign.HttpFeignClient;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.net.URI;
import java.util.Arrays;

@Slf4j
@ClientEndpoint
public class WebSocketClient1 {

    private HttpFeignClient httpFeignClient;

    private String url;

    private String tableName;

    private Session session;

    public WebSocketClient1() {
    }

    public WebSocketClient1(String url, String tableName, HttpFeignClient httpFeignClient) {
        this.url = url;
        this.tableName = tableName;
        this.httpFeignClient = httpFeignClient;
    }


    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        log.info("Connected to WebSocket server: {}", session.getRequestURI().toString());
        session.setMaxIdleTimeout(Long.MAX_VALUE / 10);
        session.setMaxBinaryMessageBufferSize(Integer.MAX_VALUE / 10);
        session.setMaxTextMessageBufferSize(Integer.MAX_VALUE / 10);
        log.info("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            //数据获取
            JSONObject data = JSONObject.parseObject(message);
            log.info("接收到数据: {}", data);
            JSONObject result = httpFeignClient.sendData(URI.create(url), tableName, Arrays.asList(data));

            log.info("发送数据: {}", result);
        } catch (Exception e) {
            log.error("发送数据异常: {}", e.getMessage());
        }
    }

    @OnError
    public void onError(Throwable throwable) {
        log.error("onError: {}", throwable.getMessage());
    }

    @OnClose
    public void onClose(CloseReason reason) {
        log.info("Disconnected from WebSocket server: {}", reason.getReasonPhrase());
    }

    public void connect(String uri) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, new URI(uri));
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            WebSocketClient1 client = new WebSocketClient1();
            client.connect("ws://10.208.63.15/bdg-api/bdg-service/ws/kafka_test_0406_1?appKey=pGagSeVF");

            while (client.session.isOpen()) {
                Thread.sleep(1000 * 5);
            }
        }
    }

}
