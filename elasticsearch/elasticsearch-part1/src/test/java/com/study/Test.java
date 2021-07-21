package com.study;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;

/**
 * @Auther: lihang
 * @Date: 2021-07-16 17:27
 * @Description:
 */
public class Test {

    RestHighLevelClient client1;

    RestHighLevelClient client2;

    String ip1 = "10.150.10.226";
    int port1 = 29200;

    String ip2 = "10.170.130.105";
    int port2 = 19200;

    @Before
    public void init() {

        client1 = new RestHighLevelClient(RestClient.builder(new HttpHost(ip1, port1, "http")));
        client2 = new RestHighLevelClient(RestClient.builder(new HttpHost(ip2, port2, "http")));
    }


    @org.junit.Test
    public void test() throws Exception {
        String s = HttpClientUtil.doGet("http://" + ip1 + ":" + port1 + "/_cat/indices", null);
        String[] split = s.split("\\n");
        for (String str : split) {
            String index = str.split(" ")[2];
            System.out.println(index);

            String json = HttpClientUtil.doGet("http://" + ip1 + ":" + port1 + "/" + index + "/_mapping", null);
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONObject mappingJson = (JSONObject) jsonObject.get(index);

            String result = HttpClientUtil.doJsonPut("http://" + ip2 + ":" + port2 + "/" + index, mappingJson.toJSONString());
            System.out.println(result);
        }
    }
}
