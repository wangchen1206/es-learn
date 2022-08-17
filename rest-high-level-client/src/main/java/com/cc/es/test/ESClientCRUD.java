package com.cc.es.test;

import com.cc.es.test.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @ClassName ESClient
 * @Desc TODO
 * @Author DELL
 * @Date 2022/7/24
 * @Version 1.0
 **/
public class ESClientCRUD {
    public static void main(String[] args) throws IOException {
        //创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        batchDelete(client);
        //关闭客户端对象
        client.close();

    }

    private static void batchDelete(RestHighLevelClient client) throws IOException {
        //批量删除
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new DeleteRequest().index("user").id("1001"));
        bulkRequest.add(new DeleteRequest().index("user").id("1002"));

        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.getItems());
        System.out.println(bulk.getTook());
    }

    private static void deleteDoc(RestHighLevelClient client) throws IOException {
        DeleteRequest user = new DeleteRequest().index("user").id("1001");
        DeleteResponse response = client.delete(user, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println(response.toString());
    }

    private static void getDoc(RestHighLevelClient client) throws IOException {
        //查看文档
        GetRequest user = new GetRequest().index("user").id("1001");
        GetResponse response = client.get(user, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println(response.getIndex());
        System.out.println(response.getType());
        System.out.println(response.getId());
        System.out.println(response.getSource());
    }

    private static void updateDoc(RestHighLevelClient client) throws IOException {
        //修改文档
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").id("1001");

        //设置请求主体
        updateRequest.doc(XContentType.JSON,"sex","女");
        //客户端发送请求对象，获取响应
        UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println(update.getIndex());
        System.out.println(update.getId());
        System.out.println(update.getResult());
    }

    /**
     * @Author wangchen
     * @Description 新增文档
     * @Date 2022/7/24 15:44
     * @Param [client]
     * @return void
     **/
    private static void addDoc(RestHighLevelClient client) throws IOException {
        //新增文档请求对象
        IndexRequest indexRequest = new IndexRequest();
        //设置索引及唯一id
        indexRequest.index("user").id("1003");
        //创建数据对象
        User user = new User();
        user.setName("cc3");
        user.setAge(30);
        user.setSex("男");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUser = objectMapper.writeValueAsString(user);

        //添加文档数据，格式为json格式
        indexRequest.source(jsonUser, XContentType.JSON);
        //客户端发送请求，获取响应
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println(index.getIndex());
        System.out.println(index.getId());
        System.out.println(index.getResult());
    }
}
