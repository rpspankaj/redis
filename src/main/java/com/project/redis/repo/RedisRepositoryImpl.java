package com.project.redis.repo;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String,String,Object> hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void add(final String redisKey , final String key ,final Object object) {
        hashOperations.put(redisKey, key , object);
    }

    public void delete(final String redisKey , final String key) {
        hashOperations.delete(redisKey, key);
    }

    public Object findObject(final String redisKey , final String key){
        return (Object) hashOperations.get(redisKey, key);
    }

    public Map<Object, Object> findAllObjects(){
        return null;//hashOperations.entries(KEY);
    }

	@Override
	public Map<Object, Object> findAllMovies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findByKey(String redisKey, String key) {
		 return (Object) hashOperations.get(redisKey, key);
	}

	


}