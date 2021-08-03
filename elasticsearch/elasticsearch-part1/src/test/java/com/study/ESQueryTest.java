/*
package com.study;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

*/
/**
 * @Auther: lihang
 * @Date: 2021-05-21 19:25
 * @Description:
 *//*

public class ESQueryTest {

    RestHighLevelClient client;

    String ip = "192.168.120.201";
    int port = 9200;

    // @Before
    public void init(){
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(ip, port, "http")));
    }

    // @After
    public void destroy() throws IOException {
        client.close();
    }

    @Test
    public void getMethodName(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        String methodName = stackTrace[1].getMethodName();
        System.out.println(methodName);
    }

    // 查询所有索引数据
    @Test
    public void search() throws IOException {
        // 创建请求体对象
        SearchRequest request = new SearchRequest();
        request.indices("user");

        // 构建查询的请求体
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 查询所有数据
        builder.query(QueryBuilders.matchAllQuery());
        request.source(builder);

        SearchResponse response = client.search(request);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());

    }

    // term查询，查询条件为关键字
    @Test
    public void termQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("name", "zhangsan"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());

    }

    // 分页查询
    @Test
    public void pageQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        builder.from(0);
        builder.size(2);

        request.source(builder);

        SearchResponse response = client.search(request);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());

    }

    // 数据排序
    @Test
    public void sortQuery() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        // 排序
        builder.sort("_id", SortOrder.DESC);
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());

    }

    // 过滤字段
    @Test
    public void filterQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        // 查询过滤字段
        String[] excludes = {"name"};
        String[] includes = {};

        builder.fetchSource(includes, excludes);
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());

    }

    // bool查询
    @Test
    public void boolQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        boolQueryBuilder.must(QueryBuilders.matchQuery("nickname","zhangsan1"));
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("nickname", "wangwu"));
        builder.query(boolQueryBuilder);

        request.indices("student");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());

    }

    // 范围查询
    @Test
    public void rangeQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("nickname");
        rangeQueryBuilder.gt("aaaaaa");
        rangeQueryBuilder.lt("zzz");
        builder.query(rangeQueryBuilder);

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());
    }

    // 模糊查询
    @Test
    public void fuzzinessQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("student");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.query(QueryBuilders.fuzzyQuery("nickname","zhangsan").fuzziness(Fuzziness.ONE));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

        SearchHits hits = response.getHits();
        for (SearchHit h : hits.getHits()) System.out.println(h.toString());
    }

    // 高亮查询
    @Test
    public void highLightQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 构建查询 高亮查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");

        builder.query(termQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");

        builder.highlighter(highlightBuilder);
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        for (SearchHit hit : hits) System.out.println(hit.toString());


    }

    // 聚合查询
    @Test
    public void aggregationQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询最大年龄
        builder.aggregation(AggregationBuilders.max("maxAge").field("age"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }

        System.out.println(JSON.toJSONString(response.getAggregations()));

    }

    // 分组统计
    @Test
    public void groupByQuery() throws IOException {
        SearchRequest request = new SearchRequest().indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.aggregation(AggregationBuilders.terms("age_group_by").field("age"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(JSON.toJSONString(response.getAggregations()));

    }



}
*/
