package com.project.redis.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.resource.DirContextDnsResolver;

public class ConnectToElastiCacheMaster {

    public static void main(String[] args) {

        // Syntax: redis://[password@]host[:port][/databaseNumber]

        DefaultClientResources clientResources = DefaultClientResources.builder() //
                .dnsResolver(new DirContextDnsResolver()) // Does not cache DNS lookups
                .build();

        RedisClient redisClient = RedisClient.create(clientResources, "redis://B2ys3pMbZkJJDJybD6RzSIDoeovweP11@redis-19017.c257.us-east-1-3.ec2.cloud.redislabs.com:19017/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }
    
}