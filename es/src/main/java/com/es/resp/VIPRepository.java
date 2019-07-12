package com.es.resp;

import com.es.dto.VIP;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/12
 **/
public interface VIPRepository extends ElasticsearchRepository<VIP, String> {


}
