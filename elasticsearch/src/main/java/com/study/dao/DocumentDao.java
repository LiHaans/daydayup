package com.study.dao;

import com.study.bean.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: lihang
 * @Date: 2021-05-27 17:06
 * @Description:
 */
@Repository
public interface DocumentDao extends ElasticsearchRepository<Document, Long> {
}
