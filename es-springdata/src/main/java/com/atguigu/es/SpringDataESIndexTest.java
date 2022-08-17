package com.atguigu.es;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private ProductDao productDao;

    //创建索引并增加映射配置
    @Test
    public void createIndex(){
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex(){
        //创建索引，系统初始化会自动创建索引
        boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引 = " + flg);
    }

    @Test
    public void save(){
        Product product = new Product();
        product.setId(3L);
        product.setCategory("华为手机3");
        product.setPrice(2999.0);
        product.setImages("http://halo.onlysyj.cc");
        product.setTitle("华为手机3");
        productDao.save(product);
    }

    @Test
    public void update(){
        Product product = new Product();
        product.setId(2L);
        product.setCategory("华为手机");
        product.setPrice(2999.0);
        product.setImages("http://halo.onlysyj.cc");
        product.setTitle("华为手机111");
        productDao.save(product);
    }

    @Test
    public void findById(){
        Optional<Product> byId = productDao.findById(2l);
        System.out.println(byId.get());
    }

    @Test
    public void findAll(){
        Iterable<Product> all = productDao.findAll();
        for (Product product : all) {
            System.out.println(product);
        }
    }

    @Test
    public void delete(){
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }

    @Test
    public void saveAll(){
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i+3));
            product.setCategory("华为手机"+i);
            product.setPrice(2999.0+i);
            product.setImages("http://halo.onlysyj.cc");
            product.setTitle("华为手机"+i);
            products.add(product);
        }
        productDao.saveAll(products);
    }

    @Test
    public void findByPage(){
        Sort id = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0;
        int pageSize = 5;
        PageRequest p = PageRequest.of(currentPage, pageSize, id);
        Page<Product> all = productDao.findAll(p);
        for (Product product : all.getContent()) {
            System.out.println(product);
        }
    }

    @Test
    public void termQuery(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "华为手机7");
        Iterable<Product> search = productDao.search(termQueryBuilder);
        for (Product product : search) {
            System.out.println(product);
        }
    }

    @Test
    public void termQueryByPage(){
        Sort id = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0;
        int pageSize = 5;
        PageRequest p = PageRequest.of(currentPage, pageSize, id);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "华为");
        Iterable<Product> search = productDao.search(termQueryBuilder,p);
        for (Product product : search) {
            System.out.println(product);
        }
    }

}
