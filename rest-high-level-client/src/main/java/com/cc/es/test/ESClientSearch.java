package com.cc.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @ClassName ESClient
 * @Desc TODO
 * @Author DELL
 * @Date 2022/7/24
 * @Version 1.0
 **/
public class ESClientSearch {
    public static void main(String[] args) throws IOException {
        //创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        //查询所有数据
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        System.out.println(search.getTook());
        for (SearchHit hit:hits) {
            System.out.println(hit.getSourceAsString());
        }
        //关闭客户端对象
        client.close();

    }


}
