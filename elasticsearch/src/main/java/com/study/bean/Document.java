package com.study.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Auther: lihang
 * @Date: 2021-05-27 17:03
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@org.springframework.data.elasticsearch.annotations.Document(indexName = "document", shards = 3, replicas = 1)
public class Document {

    @Id
    private String id;

    /**
     *	type : 字段数据类型
     *	analyzer : 分词器类型
     *	index : 是否索引(默认:true)
     *	Keyword : 短语,不进行分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String documentName;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String language;
}
