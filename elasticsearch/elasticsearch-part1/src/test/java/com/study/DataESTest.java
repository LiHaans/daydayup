package com.golaxy;


import com.alibaba.fastjson.JSON;
import com.golaxy.bean.Document;
import com.golaxy.dao.DocumentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: lihang
 * @Date: 2021-05-27 16:53
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataESTest {


    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private DocumentDao dao;

    // 创建索引
    @Test
    public void createIndex(){

    }

    // 删除索引
    @Test
    public void deleteIndex(){
        boolean result = template.deleteIndex(Document.class);
        System.out.println(result);
    }

    // 查询索引
    @Test
    public void getIndex(){
        Map<String, Object> mapping = template.getMapping(Document.class);
        System.out.println(JSON.toJSONString(mapping));
    }

    // 添加文档
    @Test
    public void addDocument(){
        Document document = new Document();
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        document.setId(id);
        document.setCategory("JAVA");
        document.setLanguage("English");
        document.setPrice(200.00);
        document.setDocumentName("Spring Cloud实战");
        dao.save(document);
    }
}
