package com.lih.ylgy.feign;

import com.alibaba.fastjson.JSONObject;
import com.lih.ylgy.config.SSLConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.ylgy.name}", url = "${feign.ylgy.url}", configuration = SSLConfiguration.class)
public interface YlgyFeign {

    @GetMapping(value = "/sheep/v1/game/game_over",headers = {
            "Accept-Encoding:gzip,compress,br,deflate", "User-Agent:Mozilla/5.0 (iPhone; CPU iPhone OS 15_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.26(0x18001a34) NetType/WIFI Language/zh_CN"
    })
    JSONObject gameover(@RequestHeader("t") String t,
            @RequestParam("rank_score") Integer rankScore,
                               @RequestParam("rank_state") Integer rankState,
                               @RequestParam("rank_time") Integer rankTime,
                               @RequestParam("rank_role") Integer rankRole,
                               @RequestParam("skin") Integer skin
                               );
}
