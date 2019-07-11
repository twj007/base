package com.es.resp;

import com.es.dto.Phone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ESRepository extends ElasticsearchRepository<Phone, String> {

    void deleteById(@Param("id")String id);

    Phone findByBrand(@Param("brand")String brand);

}
