package com.golaxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;

import java.io.IOException;
import java.net.BindException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @org.junit.Test
    public void test1() throws Exception {
        int page;
        int size = 1000;
        String s = HttpClientUtil.doGet("http://" + ip1 + ":" + port1 + "/_cat/indices", null);
        String[] split = s.split("\\n");
        for (String str : split) {
            page = 1;
            String index = str.split(" ")[2];

            System.out.println("===="+ index);

            copyData(index, page, size);
        }
    }

    @org.junit.Test
    public void test2() throws Exception {
        int page;
        int size = 500;
        String s1 = HttpClientUtil.doGet("http://" + ip2 + ":" + port2 + "/_cat/indices", null);
        String s2 = HttpClientUtil.doGet("http://" + ip1 + ":" + port1 + "/_cat/indices", null);
        String[] split = s1.split("\\n");


        Map<String,String> map = new HashMap<>();
        String[] split1 = s2.split("\\n");
        for (String s : split1) {
            map.put(s.split("\\s+")[2],s);
        }

        for (String str : split) {

            String index = str.split(" ")[2];
            page = 1;
            String[] item = str.split("\\s+");
            Integer value1 = Integer.valueOf(item[6]);
            Integer value2 = Integer.valueOf(item[7]);

            String ss = map.get(index);
            Integer v1 = Integer.valueOf(ss.split("\\s+")[6]);
            Integer v2 = Integer.valueOf(ss.split("\\s+")[7]);

            int target = value1 + value2;
            int source = v1 + v2;
            if (target >= source || target >= 5000) continue;


            //System.out.println("===="+ str);
            copyData(index, page, size);

        }
    }

    @org.junit.Test
    public void test3(){
        String str = "yellow open dws_jw_xyzxsfb                 MBYmjLjKRNqqLxtGWQBHTw 5 1     0    0   1.2kb   1.2kb";
        String trim = str.trim();
        String[] split = trim.split("\\s+");
        System.out.println(split[6]+", "+split[7]);


    }

    @org.junit.Test
    public void insert() throws IOException {
        int page = 1;
        int size = 1000;
        copyData("dwd_jw_fw_kkxx", page, size);
    }

    public void copyData(String index, int page, int size) throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices(index);
        boolean flag = true;
        while (flag){
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.matchAllQuery());
            builder.from((page-1)*size);
            builder.size(size);

            request.source(builder);
            SearchResponse response = client1.search(request);

            SearchHits hits = response.getHits();

            long total = hits.getTotalHits();
            if (hits == null || total <=0) {
                flag = false;
                continue;
            }
            BulkRequest bulk = new BulkRequest();
            for (SearchHit h : hits.getHits()) {
                String id = h.getId();
                String json = h.getSourceAsString();
                Map<String, String> map = (Map<String, String>)JSON.parse(json);
                bulk.add(new IndexRequest().index(index).id(id).type(index)
                        .source(map));
            }
            // 客户端发送请求 获取响应对象
            BulkResponse responses = client2.bulk(bulk);
            //BulkItemResponse[] items = responses.getItems();
            //for (BulkItemResponse i : items) System.out.println(index + "=>" + i.toString());
            if (total <= page*size || page*size>10000) {
                flag = false;
                continue;
            }
            page++;
        }
    }

}
