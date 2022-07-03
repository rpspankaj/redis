package com.project.redis.repo;

import java.util.Map;

public interface RedisRepository {

    Map<Object, Object> findAllMovies();

    public void add(final String redisKey , final String key ,final Object Object);

    void delete(final String redisKey , final String key);

    Object findByKey(final String redisKey , final String key);

}