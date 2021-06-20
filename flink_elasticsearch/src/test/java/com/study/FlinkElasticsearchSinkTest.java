/*
package com.study;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.flink.streaming.connectors.elasticsearch7.RestClientFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @Auther: lihang
 * @Date: 2021-05-27 20:03
 * @Description:
 *//*

public class FlinkElasticsearchSinkTest implements ElasticsearchSinkFunction<String>, Serializable {
    private String host = "192.168.120.201";

    @Test
    public void test() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> source = env.socketTextStream("127.0.0.1", 9999);

        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost(host, 9200, "http"));

        //httpHosts.add(new HttpHost("10.2.3.1", 9200, "http"));
        // use a ElasticsearchSink.Builder to create an ElasticsearchSink

        ElasticsearchSink.Builder<String> esSinkBuilder = new ElasticsearchSink.Builder<>(httpHosts, new FlinkElasticsearchSinkTest());

// configuration for the bulk requests; this instructs the sink to emit after every element, otherwise they would be buffered         esSinkBuilder.setBulkFlushMaxActions(1);

// provide a RestClientFactory for custom configuration on the internally created REST client
        esSinkBuilder.setRestClientFactory(
                new RestClientFactoryImpl()
        );
        source.addSink(esSinkBuilder.build());

        env.execute("flink-es");
    }

    @Override
    public void process(String element, RuntimeContext ctx, RequestIndexer indexer) {
        indexer.add(createIndexRequest(element));
    }

    public IndexRequest createIndexRequest(String element) {
        Map<String, String> json = new HashMap<>();
        json.put("data", element);
        return Requests.indexRequest()
                .index("my-index")
                //.type("my-type")
                .source(json);
    }
}
class RestClientFactoryImpl implements RestClientFactory {
    @Override
    public void configureRestClientBuilder(RestClientBuilder restClientBuilder) {
        Header[] headers = new BasicHeader[]{new BasicHeader("Content-Type","application/json")};
        restClientBuilder.setDefaultHeaders(headers); //以数组的形式可以添加多个header
        restClientBuilder.setMaxRetryTimeoutMillis(90000);
    }
}
*/
