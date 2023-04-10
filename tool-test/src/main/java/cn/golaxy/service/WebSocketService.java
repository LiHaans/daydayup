package cn.golaxy.service;

import cn.golaxy.extend.MyContainerProvider;
import cn.golaxy.extend.MyWsWebSocketContainer;
import cn.golaxy.feign.HttpFeignClient;
import cn.golaxy.util.http.IdleConnectionMonitorThread;
import cn.golaxy.util.http.manager.HttpClientManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.Session;
import java.net.URI;

@Service
@Slf4j
public class WebSocketService {

    @Resource
    private HttpFeignClient httpFeignClient;


    public void queryData(String url, String appKey, String responseUrl, String tableName) throws Exception {
        String requestUrl = String.format("%s?appKey=%s", url, appKey);
        log.info("访问的url:"+url);
        while (true) {
            WebSocketClient1 client = new WebSocketClient1(responseUrl, tableName, httpFeignClient);
            client.connect(requestUrl);

            while (client.getSession().isOpen()) {
                sleep(1000 * 5);
            }

            if(client.getSession()!=null){
                try{
                    client.getSession().close();
                    continue;
                }catch (Exception e){

                }
            }
        }
    }

    public void consumer(String url, String appKey, String responseUrl, String tableName) {
        HttpClientManager httpClientManager = new HttpClientManager();
        httpClientManager.initHttpclientPool();
        IdleConnectionMonitorThread icmt = new IdleConnectionMonitorThread(httpClientManager.getPoolConnManager());
        icmt.start();


        String requestUrl = String.format("%s?appKey=%s", url, appKey);

        while (true) {

            MyWsWebSocketContainer container = MyContainerProvider.getWebSocketContainer();
            Session session =null;
            try{
                log.info("访问的url:"+url);
                ClientEndpointConfig ce= ClientEndpointConfig.Builder.create().build();
                session=container.connectToServer(new WebSocketClient(responseUrl, tableName, httpFeignClient),ce, URI.create(requestUrl));

            }catch (Exception e){
                e.printStackTrace();
                sleep(30000);
                continue;
            }
            while (session.isOpen()) {
                sleep(30000);
            }
            if(session!=null){
                try{
                    session.close();
                    continue;
                }catch (Exception e){

                }
            }
            sleep(30000);
        }
    }

    private static void sleep(long sleepTime){
        try{
            Thread.sleep(sleepTime);
        }catch (Exception e){

        }
    }
}
