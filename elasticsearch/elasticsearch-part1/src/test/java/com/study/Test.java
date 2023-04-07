package com.golaxy;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;

import java.io.IOException;
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
    public void test3() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolBuilder = new BoolQueryBuilder();
        boolBuilder.must(QueryBuilders.matchQuery("xsdqztmc","在读"));
        boolBuilder.must(QueryBuilders.existsQuery("xbmc"));
        boolBuilder.mustNot(QueryBuilders.matchQuery("xbmc",""));
        builder.query(boolBuilder);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("xbmcTerms").field("xbmc")
                .subAggregation(AggregationBuilders.terms("xbdmTerms").field("xbdm"));

        builder.aggregation(aggregationBuilder);

        builder.size(0);

        SearchRequest request = new SearchRequest("dwd_jw_o_xsjbxx").source(builder);

        SearchResponse response = client2.search(request, RequestOptions.DEFAULT);

        Terms terms = response.getAggregations().get("xbmcTerms");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();


        for (Terms.Bucket bucket : buckets) {
            String key = (String)bucket.getKey();
            Terms xbdmTerms = bucket.getAggregations().get("xbdmTerms");
            for (Terms.Bucket b : xbdmTerms.getBuckets()) {
                String dmKey = (String) b.getKey();
                long count = b.getDocCount();
                System.out.println("dm:"+key+", mc:"+dmKey+", sl:"+count);
            }
        }

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
        String s = HttpClientUtil.doGet("http://" + ip1 + ":" + port1 + "/_cat/indices", null);
        String[] split = s.split("\\n");
        for (String str : split) {
            String index = str.split(" ")[2];
            SearchRequest request = new SearchRequest();
            request.indices(index);

            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.matchAllQuery());

            builder.from(0);
            builder.size(10);

            request.source(builder);

            SearchResponse response = client1.search(request);

            System.out.println(response.toString());

            SearchHits hits = response.getHits();
            for (SearchHit h : hits.getHits()) System.out.println(h.toString());


        }
    }

}
