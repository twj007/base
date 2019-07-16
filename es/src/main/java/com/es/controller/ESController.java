package com.es.controller;

import com.es.dto.Phone;
import com.es.dto.VIP;
import com.es.resp.ESRepository;
import com.es.resp.VIPRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.Version;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.mapper.Mapping;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private VIPRepository vipRepository;


    @GetMapping("/vip")
    public ResponseEntity searchVip(@RequestBody VIP vip){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.rangeQuery("birthday")
                .from(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vip.getBirthday()))
                .to(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        List<VIP> v = elasticsearchTemplate.queryForList(builder.build(), VIP.class);
        return ResponseEntity.ok(v);
    }

    @RequestMapping("/saveVIP")
    public ResponseEntity saveVIP(@RequestBody VIP vip){
        VIP v = vipRepository.save(vip);
        return ResponseEntity.ok(v);
    }

    @RequestMapping("/products/{brand}")
    public ResponseEntity searchByBrand(@PathVariable("brand")String brand){
        Phone phone = esRepository.findByBrand(brand);
        return ResponseEntity.ok(phone);
    }

    /***
     * 创建索引
     * @param index
     * @return
     */
    @RequestMapping("/index/add/{index}")
    public ResponseEntity createIndex(@PathVariable("index")String index){

        if(elasticsearchTemplate.createIndex(index)){
            return ResponseEntity.ok("索引创建成功");
        }else{
            return ResponseEntity.ok("创建索引失败");
        }
    }


    /***
     * 删除索引
     * @param index
     * @return
     */
    @RequestMapping("/index/delete/{index}")
    public ResponseEntity deleteIndex(@PathVariable("index")String index){
        if(elasticsearchTemplate.deleteIndex(index)){
            return ResponseEntity.ok("删除成功");
        }else{
            return ResponseEntity.ok("删除失败");
        }
    }

//    /**
//     * @deprecated
//     * 创建类型 mapping值暂时没弄，方法没用
//     * @param index
//     * @param type
//     * @return
//     */
//    @RequestMapping("/type/{index}/{type}")
//    public ResponseEntity createType(@PathVariable("index")String index, @PathVariable("type")String type){
//        //Mapping mapping = new Mapping(Version.V_5_5_1, );
//        if(elasticsearchTemplate.indexExists(index)){
//            if(!elasticsearchTemplate.typeExists(index, type)){
//                if(elasticsearchTemplate.putMapping(index, type, null)){
//                    return ResponseEntity.ok("创建type成功");
//                }else{
//                    return ResponseEntity.ok("创建type失败");
//                }
//            }else{
//                return ResponseEntity.ok("类型已存在");
//            }
//        }else{
//            return ResponseEntity.ok("索引不存在");
//        }
//
//    }




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


    /***
     * 根据全文分词器匹配查询
     * @param pageSize
     * @param pageNum
     * @param sort
     * @param keyword
     * @return
     */
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
        //过滤查询 过滤掉价格小于1000的数据
        //builder.withFilter(QueryBuilders.boolQuery().mustNot(QueryBuilders.rangeQuery("price").lt(1000f)));
        // 过滤出 品牌为华为，价格大于2000的数据
        //builder.withFilter(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("brand", "华为")).filter(QueryBuilders.rangeQuery("price").gt(2000)));
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


    @RequestMapping("/highlight")
    public ResponseEntity highlight(@RequestParam(defaultValue = "5") int pageSize,
                                 @RequestParam(defaultValue = "0") int pageNum,
                                 @RequestParam(defaultValue = "1") int sort,
                                 String keyword){
        Pageable page = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        if(StringUtils.isEmpty(keyword)){
            builder.withQuery(QueryBuilders.matchAllQuery()).withPageable(page);
        }else {
            builder.withQuery(QueryBuilders.matchPhraseQuery("brand", keyword))
                    .withHighlightFields(new HighlightBuilder.Field("brand"))
                    .withPageable(page);
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
        return ResponseEntity.ok( elasticsearchTemplate.query(query, response -> {

            //直接返回es原封结果到前端
            return response.toString();
        }));
    }


    /***
     * 单单获取统计个数
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param keyword
     * @return
     */
    @RequestMapping("/group/single")
    public ResponseEntity single(@RequestParam(defaultValue = "0")int pageNum,
                                @RequestParam(defaultValue = "5")int pageSize,
                                @RequestParam(defaultValue = "1")int sort,
                                String keyword){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        Pageable page = PageRequest.of(pageNum, pageSize);
        builder.withPageable(page);
        if(StringUtils.isEmpty(keyword)){
            builder.withQuery(QueryBuilders.matchAllQuery());
        }else{
            builder.withQuery(QueryBuilders.multiMatchQuery(keyword, "brand", "model"));
        }
        builder.addAggregation(AggregationBuilders.terms("brand").field("brand"));
        Aggregations aggregations = elasticsearchTemplate.query(builder.build(),
                new ResultsExtractor<Aggregations>() {
                    @Override
                    public Aggregations extract(SearchResponse response) {
                        return response.getAggregations();
                    }
                });

        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("brand");
        Map<String, Long> map = new HashMap<>();
        for (Terms.Bucket actionTypeBucket : modelTerms.getBuckets()) {
            //actionTypeBucket.getKey().toString()聚合字段的相应名称,actionTypeBucket.getDocCount()相应聚合结果
            map.put(actionTypeBucket.getKey().toString(),
                    actionTypeBucket.getDocCount());
        }

        return ResponseEntity.ok(map);
    }



    /***
     * 统计 某个品牌下 的平均价格，并根据价格排序 (关键字必须是精确的)
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param keyword
     * @return
     */
    @RequestMapping("/group")
    public ResponseEntity group(@RequestParam(defaultValue = "0")int pageNum,
                                @RequestParam(defaultValue = "5")int pageSize,
                                @RequestParam(defaultValue = "1")int sort,
                                String keyword){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        Pageable page = PageRequest.of(pageNum, pageSize);
        builder.withPageable(page);
        if(StringUtils.isEmpty(keyword)){
            builder.withQuery(QueryBuilders.matchAllQuery());
        }else{
            builder.withQuery(QueryBuilders.multiMatchQuery(keyword, "brand", "model"));
        }
        builder.addAggregation(AggregationBuilders.terms("brand_count").field("brand").order(Terms.Order.aggregation("price", true))
                .subAggregation(AggregationBuilders.avg("price").field("price")));
        return ResponseEntity.ok(elasticsearchTemplate.query(builder.build(), response -> {

            //直接返回es原封结果到前端
            return response.toString();
        }));
    }

    /***
     * 先分区再分组求和平均
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param keyword
     * @return
     */
    @RequestMapping("/group/range")
    public ResponseEntity range(@RequestParam(defaultValue = "0")int pageNum,
                                @RequestParam(defaultValue = "5")int pageSize,
                                @RequestParam(defaultValue = "1")int sort,
                                String keyword){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        Pageable page = PageRequest.of(pageNum, pageSize);
        builder.withPageable(page);
        if(StringUtils.isEmpty(keyword)){
            builder.withQuery(QueryBuilders.matchAllQuery());
        }else{
            builder.withQuery(QueryBuilders.multiMatchQuery(keyword, "brand", "model"));
        }
        builder.addAggregation(AggregationBuilders.range("range_by_price").field("price")
                                .addRange(0, 1001)
                                .addRange(1001, 2001)
                                .addRange(2001, 10001)
                                .subAggregation(
                                        AggregationBuilders.terms("brand_count")
                                                .field("brand")
                                                .order(Terms.Order.aggregation("price", false))
                                                .subAggregation(
                                                        AggregationBuilders
                                                                .avg("price")
                                                                .field("price"))
                                                ));
        return ResponseEntity.ok(elasticsearchTemplate.query(builder.build(), response -> {

            //直接返回es原封结果到前端
            return response.toString();
        }));
    }



    /***
     * 在设计索引中type的mapping时，必须要带上自定义的score属性，不然会查不出任何评分结果
     * @param pageNum
     * @param pageSize
     * @param
     * @param keyword
     * @return
     */
    @RequestMapping("/recommend")
    public ResponseEntity recommend(@RequestParam(defaultValue = "0")int pageNum,
                                    @RequestParam(defaultValue = "5")int pageSize,
                                    String keyword){
        Pageable page = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        if(StringUtils.isEmpty(keyword)){
            builder.withQuery(QueryBuilders.matchAllQuery());
        }else{
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> functionBuilders = new ArrayList<>();
            functionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("brand", keyword), ScoreFunctionBuilders.weightFactorFunction(1)));
            functionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("model", keyword), ScoreFunctionBuilders.weightFactorFunction(7)));
            functionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("desc", keyword), ScoreFunctionBuilders.weightFactorFunction(3)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[functionBuilders.size()];
            functionBuilders.toArray(filterFunctionBuilders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(filterFunctionBuilders).scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM).setMinScore(2f);
            builder.withQuery(functionScoreQueryBuilder);
        }
        builder.withPageable(page);
        //List<Phone> phones = elasticsearchTemplate.queryForList(builder.build(), Phone.class);
        return ResponseEntity.ok(elasticsearchTemplate.query(builder.build(), response -> {
            //直接返回es原封结果到前端
            return response.toString();
        }));
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
