package com.es.resp;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;

import java.io.Serializable;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/11
 **/
public class HashKeyedRepository<T, ID extends Serializable> extends AbstractElasticsearchRepository<T, ID> {
    public HashKeyedRepository() {
        super();
    }

    public HashKeyedRepository(ElasticsearchEntityInformation<T, ID> metadata,
                               ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
    }

    public HashKeyedRepository(ElasticsearchOperations elasticsearchOperations) {
        super(elasticsearchOperations);
    }

    @Override
    protected String stringIdRepresentation(ID id) {
        return String.valueOf(id.hashCode());
    }
}
