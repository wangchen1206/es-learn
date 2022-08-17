package com.cc.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

/**
 * @ClassName ESClient
 * @Desc TODO
 * @Author DELL
 * @Date 2022/7/24
 * @Version 1.0
 **/
public class ESClientCreatIndex {
    public static void main(String[] args) throws IOException {
        //创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        CreateIndexRequest user = new CreateIndexRequest("user");
        CreateIndexResponse createIndexResponse = client.indices().create(user, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());

        //关闭客户端对象
        client.close();

    }
}
