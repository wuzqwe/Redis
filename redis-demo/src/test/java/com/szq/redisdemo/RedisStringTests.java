package com.szq.redisdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szq.redisdemo.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
public class RedisStringTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void test() {
        //写入一条String数据
        stringRedisTemplate.opsForValue().set("name","ew哥");
        //获取string数据
        Object name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("name ="+name);
    }

    private static final ObjectMapper mapper=new ObjectMapper();

    @Test
    void testSaveUser() throws JsonProcessingException {
        //创建对象
        User user=new User("虎哥",21);
        //手动序列化
        String json = mapper.writeValueAsString(user);
        //写入数据
        stringRedisTemplate.opsForValue().set("user:200",json);
        //获取数据
       String jsonUesr= stringRedisTemplate.opsForValue().get("user:200");
       //手动反序列化
        User user1 = mapper.readValue(jsonUesr, User.class);

        System.out.println("user1="+user1);
    }

    @Test
    void testHash() {
        stringRedisTemplate.opsForHash().put("user:400","name","虎哥");
        stringRedisTemplate.opsForHash().put("user:400","age","21");
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:400");
        System.out.println("entries"+entries);
    }
}
