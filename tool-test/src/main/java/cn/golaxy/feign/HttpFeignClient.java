package cn.golaxy.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * @author lihang
 */
@FeignClient(value = "HttpApi", url = "EMPTY")
public interface HttpFeignClient {

    /**
     *  post请求
     * @param uri
     * @param dto
     * @return
     */
    @PostMapping
    JSONObject postMethodRequest(URI uri, @RequestBody JSONObject dto);

    /**
     *  get请求
     * @param uri
     * @param dto
     * @return
     */
    @GetMapping
    JSONObject getMethodRequest(URI uri, @RequestParam JSONObject dto);


    @PostMapping
    JSONObject sendData(URI uri, @RequestParam(value = "tableName") String tableName, @RequestBody List<JSONObject> dto);
}
