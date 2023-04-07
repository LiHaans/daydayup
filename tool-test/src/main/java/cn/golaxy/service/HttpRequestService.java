package cn.golaxy.service;

import cn.golaxy.feign.HttpFeignClient;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HttpRequestService {

    @Value("${request.method:get}")
    private String requestMethod;

    @Resource
    private HttpFeignClient httpFeignClient;


    public void queryData(String requestUrl, String appKey, String responseUrl, String tableName) throws Exception {
        Integer pageNum = 1;
        Integer pageSize = 10;

        Integer index = 0;

        JSONObject dto = new JSONObject();
        dto.put("appKey", appKey);
        dto.put("pageNum", pageNum);
        dto.put("pageSize", pageSize);

        while (true) {
            JSONObject result = null;
            if ("post".equals(requestMethod)) {
                result = httpFeignClient.postMethodRequest(URI.create(requestUrl), dto);
            } else {
                result = httpFeignClient.getMethodRequest(URI.create(requestUrl), dto);
            }

            ArrayList<JSONObject> datas = new ArrayList<>();

            log.info("result: {}", result);

            if (result != null && result.getBoolean("success") && result.getJSONArray("data") != null && result.getJSONArray("data").size() > 0) {
                List<JSONObject> data = JSONObject.parseArray(result.getJSONArray("data").toJSONString(), JSONObject.class);

                if (index > 0) {
                    if (pageSize.equals(data.size())) {
                        log.info("获取到: {} 条数据", data.size() - index);
                        datas.addAll(data.subList(index, data.size()));

                        pageNum++;
                        index = 0;
                    } else {

                        if (data.size() > index) {
                            log.info("获取到: {} 条数据", data.size() - index);
                            datas.addAll(data.subList(index, data.size()));
                            index = data.size();
                        }

                    }
                    Thread.sleep(1000 * 10);

                } else {
                    if (pageSize.equals(data.size())) {
                        log.info("获取到: {} 条数据", data.size());

                        pageNum++;
                    } else {

                        log.info("获取到: {} 条数据", data.size());

                        index = data.size();
                        log.info("index: {}", index);
                    }

                    datas.addAll(data);
                }

                httpFeignClient.sendData(URI.create(responseUrl), tableName, data);

            } else {

                if (result != null && !result.getBoolean("success")) {
                    log.error("查询数据异常: {}", result.getString("message"));
                } else {
                    log.info("无新增数据...");
                }
                Thread.sleep(1000 * 60);
            }
        }
    }

}
