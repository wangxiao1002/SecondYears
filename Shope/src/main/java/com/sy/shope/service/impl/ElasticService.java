package com.sy.shope.service.impl;

import com.sy.shope.entity.Good;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@Service
public class ElasticService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostConstruct
    public void  initService () {
        elasticsearchRestTemplate.indexOps(Good.class).create();
    }

    /**
     * 查询且高亮
     * @param keyWord
     * @param type
     * @param curPage
     * @param pageSize
     * @return 集合
     */
    public List<Good> queryAndHeight(String keyWord,String type, int curPage, int pageSize) {
        String preTags = "<span style=\"color:#F56C6C\">";
        String postTags = "</span>";

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        //and
                        .must(QueryBuilders.multiMatchQuery(keyWord, "spuNameZh", "spuNameEn","spuDesc"))
                ).withHighlightBuilder(new HighlightBuilder()
                        .field("spuNameZh").field("spuNameEn").field("spuDesc")
                        .preTags(preTags).postTags(postTags))
                .withPageable(PageRequest.of(curPage - 1, pageSize))
                .build();

        SearchHits<Good> contents  = elasticsearchRestTemplate.search(query,Good.class);
        List<SearchHit<Good>> searchHits = contents.getSearchHits();
        if (searchHits.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<Good> goodList = searchHits.stream()
                .map(good ->{
                    Map<String,List<String>> fields = good.getHighlightFields();
                    if (!CollectionUtils.isEmpty(fields.get("spuNameZh"))) {
                        good.getContent().setSpuNameZh(fields.get("spuNameZh").get(0));
                    }
                    if (!CollectionUtils.isEmpty(fields.get("spuNameEn"))) {
                        good.getContent().setSpuNameZh(fields.get("spuNameEn").get(0));
                    }
                    if (!CollectionUtils.isEmpty(fields.get("spuDesc"))) {
                        good.getContent().setSpuNameZh(fields.get("spuDesc").get(0));
                    }
                    return good.getContent();
                }).collect(Collectors.toList());
        return goodList;
    }


    public void  addGood (Good good) {
        elasticsearchRestTemplate.save(good);
    }
}
