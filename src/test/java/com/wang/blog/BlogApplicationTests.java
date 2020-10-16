package com.wang.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Scanner;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    RedisTemplate<String,Object> template;

    @Test
    public void Redis(){
        template.opsForValue().set("123","@#2");
    }


    @Test
    public void test(){
        int a,b;
        Scanner sc = new Scanner(System.in);
        a = sc.nextInt();
        b = sc.nextInt();
        float c;
        c = (float) (a / b);
    }

}
