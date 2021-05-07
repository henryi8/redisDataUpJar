package com.example.demo;

import com.example.demo.redis.SpringUtil;
import com.example.demo.redis.service.RedisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Set;

@ComponentScan("com.example.demo.redis")
@SpringBootApplication
public class DemoApplication {


//    @Autowired
//    private static RedisService redisService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        try{
            RedisService redisService = (RedisService) SpringUtil.getBean("redisService");
            Set<String> keys = (Set<String>) redisService.getKeys("test:code:*");
            Set<String> key2 = (Set<String>) redisService.getKeys("serial:num:*");
            Set<String> key3 = (Set<String>) redisService.getKeys("jth:*");
            Set<String> key4 = (Set<String>) redisService.getKeys("bml:*");
            keys.addAll(key2);
            keys.addAll(key3);
            keys.addAll(key4);
            System.out.println("所有key大小：：："+keys.size());
            Integer success = 0;
            Integer fail = 0;

            for (String key:keys){
                Boolean persist = redisService.persist(key);
                System.out.println(key+"，去除过期时间,"+persist);
                if(persist){
                    success = success +1;
                }else {
                    fail = fail +1;
                }
            }
            System.out.println("所有key，成功去除:"+success+",,,失败："+fail);
        }catch (Exception e){
            System.out.println("出错：：：："+e.getMessage());
        }

//        System.out.printf(o.toString());
    }

}
