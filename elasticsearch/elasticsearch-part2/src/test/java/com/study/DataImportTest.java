package com.golaxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lihang
 * @Date: 2021-07-15 17:05
 * @Description:
 */
public class DataImportTest {

    RestHighLevelClient client1;

    RestHighLevelClient client2;

    String ip1 = "10.150.10.226";
    int port1 = 29200;

    String ip2 = "10.170.130.105";
    int port2 = 19200;

    @Before
    public void init(){

        client1 = new RestHighLevelClient(RestClient.builder(new HttpHost(ip1, port1, "http")));
        client2 = new RestHighLevelClient(RestClient.builder(new HttpHost(ip2, port2, "http")));
    }

    @Test
    public void getIndices() throws IOException {
        IndexRequest request = new IndexRequest(
                "posts",
                "doc",
                "1");
        IndexResponse index = client1.index(request);



    }

    @Test
    public void insertIndex(){

    }
}
