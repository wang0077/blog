package com.wang.blog.component.bloomFilter;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wangsiyuan
 */
@Component
@ConditionalOnBean(RedisTemplate.class)
public class BloomFilterServer {


    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param key 键
     * @param value 值
     * @param <T> 添加记录到BloomFilter
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value){
        Preconditions.checkArgument(bloomFilterHelper != null,"bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        System.out.println(offset.length);
        for (int i : offset){
            redisTemplate.opsForValue().setBit(key,i,true);
        }
    }

    /**
     * @param key 键
     * @param value 值
     * @param <T> 通过键值查询BloomFilter是否存在
     * @return 返回是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper,String key,T value){
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for(int i : offset){
            Boolean bit = redisTemplate.opsForValue().getBit(key, i);
            if(bit != null && !bit){
                return false;
            }
        }
        return true;
    }
}
