package com.cc.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @ClassName ESClient
 * @Desc TODO
 * @Author DELL
 * @Date 2022/7/24
 * @Version 1.0
 **/
public class ESClient {
    public static void main(String[] args) throws IOException {
        //创建客户端对象
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        //关闭客户端对象
        restHighLevelClient.close();

    }
}
