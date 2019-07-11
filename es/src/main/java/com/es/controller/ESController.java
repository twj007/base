package com.es.controller;

import com.es.dto.Phone;
import com.es.resp.ESRepository;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/11
 **/
@RestController
@CrossOrigin
public class ESController {

    @Autowired
    private ESRepository esRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("/products/{brand}")
    public ResponseEntity searchByBrand(@PathVariable("brand")String brand){
        Phone phone = esRepository.findByBrand(brand);
        return ResponseEntity.ok(phone);
    }

    /***
     * 添加索引内容，或者更新索引内容，通过id识别
     * @param phone
     * @return
     */
    @RequestMapping("/save")
    public ResponseEntity save(@RequestBody Phone phone){

        Phone p = esRepository.save(phone);
        return ResponseEntity.ok(p);
    }


    @RequestMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable("id")String id){
        esRepository.deleteById(id);
        return ResponseEntity.ok("ok");
    }



    @RequestMapping("/search")
    public ResponseEntity search(@RequestParam(defaultValue = "5") int pageSize,
                                 @RequestParam(defaultValue = "0") int pageNum,
                                 @RequestParam(defaultValue = "1") int sort,
                                 String keyword){
        Pageable page = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        if(StringUtils.isEmpty(keyword)){
            builder.withQuery(QueryBuilders.matchAllQuery()).withPageable(page);
        }else {
            builder.withQuery(QueryBuilders.queryStringQuery(keyword)).withPageable(page);
        }
        switch(sort){
            case 1:
                builder.withSort(SortBuilders.fieldSort("brand").order(SortOrder.DESC));
                break;
            case 2:
                builder.withSort(SortBuilders.fieldSort("model").order(SortOrder.DESC));
                break;
            case 3:
                builder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
                break;
            default:
                builder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
                break;
        }
        SearchQuery query = builder.build();
        List<Phone> phones = elasticsearchTemplate.queryForList(query, Phone.class);
        return ResponseEntity.ok(phones);
    }

//    @RequestMapping("/search")
//    public ResponseEntity search(int pageNum, int pageSize, String keyword, String brand, int sort){
//        Pageable page = PageRequest.of(pageNum, pageSize);
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        builder.withPageable(page);
//        if(!StringUtils.isEmpty(brand)){
//            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//            queryBuilder.must(QueryBuilders.termQuery("brand", brand));
//            builder.withFilter(queryBuilder);
//        }
//        //关键词为空时，匹配所有
//        if(StringUtils.isEmpty(keyword)){
//            builder.withQuery(QueryBuilders.matchAllQuery());
//        }else{
//            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("brand", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(10)));
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("desc", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(5)));
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("price", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(2)));
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("model", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(2)));
//            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
//            filterFunctionBuilders.toArray(builders);
//            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
//                    .scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM)
//                    .setMinScore(2);
//            builder.withQuery(functionScoreQueryBuilder);
//        }
//        if(sort==1){
//            //按新品从新到旧
//            builder.withSort(SortBuilders.fieldSort("brand").order(SortOrder.DESC));
//        }else if(sort==2){
//            //按销量从高到低
//            builder.withSort(SortBuilders.fieldSort("model").order(SortOrder.DESC));
//        }else if(sort==3){
//            //按价格从低到高
//            builder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
//        }else if(sort==4){
//            //按价格从高到低
//            builder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
//        }else{
//            //按相关度
//            builder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
//        }
//        builder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
//        NativeSearchQuery searchQuery = builder.build();
//        return ResponseEntity.ok(searchQuery);
//    }

//    @RequestMapping("/products")
//    public Page<Phone> search(int pageNum, int pageSize){
//        Pageable pageable = PageRequest.of(pageNum, pageSize);
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        //分页
//        nativeSearchQueryBuilder.withPageable(pageable);
//        //过滤
//        if (brand != null || model != null) {
//            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//            if (brand != null) {
//                boolQueryBuilder.must(QueryBuilders.termQuery("brand", brand));
//            }
//            if (model != null) {
//                boolQueryBuilder.must(QueryBuilders.termQuery("model", model));
//            }
//            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
//        }
//        //搜索
//        if (StringUtils.isEmpty(keyword)) {
//            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
//        } else {
//            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(10)));
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("subTitle", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(5)));
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("keywords", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(2)));
//            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
//            filterFunctionBuilders.toArray(builders);
//            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
//                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
//                    .setMinScore(2);
//            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
//        }
//        //排序
//        if(sort==1){
//            //按新品从新到旧
//            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
//        }else if(sort==2){
//            //按销量从高到低
//            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("sale").order(SortOrder.DESC));
//        }else if(sort==3){
//            //按价格从低到高
//            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
//        }else if(sort==4){
//            //按价格从高到低
//            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
//        }else{
//            //按相关度
//            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
//        }
//        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
//        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
//        return esRepository.search(searchQuery);
//    }
}
