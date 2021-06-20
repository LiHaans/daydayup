/*
package com.study;

import com.alibaba.fastjson.JSON;
import com.study.bean.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

*/
/**
 * @Auther: lihang
 * @Date: 2021-05-11 22:14
 * @Description:
 *//*

public class ESTest {

    RestHighLevelClient client;

    String ip = "192.168.120.201";
    int port = 9200;

    @Before
    public void init(){
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(ip, port, "http")));
    }

    @After
    public void destroy() throws IOException {
        client.close();
    }

    // 创建索引
    @Test
    public void createIndex() throws IOException {
        // 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("user");
        // 发送请求，获取响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        // 响应状态
        System.out.println("操作状态：" + acknowledged);

    }

    // 查询索引
    @Test
    public void getIndex() throws IOException {
        // 查询索引 请求对象
        GetIndexRequest request = new GetIndexRequest("user");
        // 发送请求，获取响应
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        Map<String, List<AliasMetadata>> aliases = response.getAliases();
        Map<String, MappingMetadata> mappings = response.getMappings();
        Map<String, Settings> settings = response.getSettings();
        System.out.println("mappings : " + mappings);
        System.out.println("aliases : " + aliases);
        System.out.println("settings : " + settings);
    }

    // 删除索引
    @Test
    public void deleteIndex() throws IOException {
        // 删除索引 请求对象
        DeleteIndexRequest request = new DeleteIndexRequest("user");

        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();
        System.out.println("执行结果 : " + acknowledged);
    }

    // 创建文档
    @Test
    public void addDocument() throws IOException {
        // 新增文档 请求对象
        IndexRequest request = new IndexRequest();
        // 设置索引唯一标识符
        request.index("user").id("1001");
        // 创建数据对象
        User user = new User();
        user.setName("Tom");
        user.setAge(20);
        user.setSex("男");

        String json = JSON.toJSONString(user);

        request.source(json, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());

    }

    // 修改文档
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");

        request.doc(XContentType.JSON, "sex", "女");

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    // 查询文档
    @Test
    public void queryDocument() throws IOException {
        GetRequest request = new GetRequest().index("user").id("1004");

        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String source = JSON.toJSONString(response.getSource());
       System.out.println("source ： "+source);
        System.out.println(response.toString());
    }

    // 删除文档
    @Test
    public void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest().index("user").id("1001");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());
    }

    // 批量添加
    @Test
    public void batchCreateDocument() throws IOException {
        //  创建批量新增请求对象
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1003")
                .source(XContentType.JSON, "name","zhangsan"));

        request.add(new IndexRequest().index("user").id("1004")
                .source(XContentType.JSON, "name","zhangsan"));

        request.add(new IndexRequest().index("user").id("1005")
                .source(XContentType.JSON, "name","zhangsan"));
        // 客户端发送请求 获取响应对象
        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);

        BulkItemResponse[] items = responses.getItems();
        for (BulkItemResponse i : items) System.out.println(i.toString());

    }

    // 批量删除
    @Test
    public void batchDeleteDocument() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1003"));
        request.add(new DeleteRequest().index("user").id("1004"));
        request.add(new DeleteRequest().index("user").id("1005"));

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response.getTook());
    }


}
*/
