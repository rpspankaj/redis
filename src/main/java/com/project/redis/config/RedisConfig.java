package com.project.redis.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.project.redis.queue.MessagePublisher;
import com.project.redis.queue.RedisMessagePublisher;
import com.project.redis.queue.RedisMessageSubscriber;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String redisHost;

	private @Value("${spring.redis.port}") int redisPort;
	private @Value("${spring.redis.password}") String redisPassword;

	private @Value("${spring.redis.timeout}") Duration redisCommandTimeout;

	private @Value("${spring.redis.custom.socket.timeout}") Duration socketTimeout;

	Logger logger = LoggerFactory.getLogger(RedisConfig.class);
	/**
	 * Redis configuration
	 *
	 * @return redisStandaloneConfiguration
	 */
	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisHost);
		redisStandaloneConfiguration.setPort(redisPort); 
	//	redisStandaloneConfiguration.setPassword(redisPassword);
		redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword)); 
		logger.info("redis host {}",redisHost); 
		logger.info("redis Port {}",redisPort); 
		logger.info("redis Password {}",redisPassword); 
		return redisStandaloneConfiguration;
	}
	
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration() {
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
		clusterConfiguration.clusterNode(redisHost, redisPort);
		clusterConfiguration.setPassword(RedisPassword.of(redisPassword)); 
		return clusterConfiguration;
	}

	/**
	 * Client Options Reject requests when redis is in disconnected state and Redis
	 * will retry to connect automatically when redis server is down
	 *
	 * @return client options
	 */
	@Bean
	public ClientOptions clientOptions() {
		return ClientOptions.builder().disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
				.autoReconnect(true).build();
	}


	@Bean
	LettuceConnectionFactory lettuceConnectionFactory() {
		logger.info("lettuceConnectionFactory created"); 
	//	final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(socketTimeout).build();
	//	final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().build();
				//.commandTimeout(redisCommandTimeout).clientOptions(clientOptions).build();
		final LettuceConnectionFactory lettuceConnectionFactory = new
			LettuceConnectionFactory(redisStandaloneConfiguration(), clientConfig);
			 lettuceConnectionFactory.setValidateConnection(true);
		logger.info("-------------lettuceConnectionFactory----------"); 
		return lettuceConnectionFactory; 

	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		logger.info("-------------redisTemplate----------"); 
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory());
	//	redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	
	  @Bean
	    MessageListenerAdapter messageListener() {
	        return new MessageListenerAdapter(new RedisMessageSubscriber());
	    }

	    @Bean
	    RedisMessageListenerContainer redisContainer() {
	        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	        container.setConnectionFactory(lettuceConnectionFactory());
	        container.addMessageListener(messageListener(), topic());
	        return container;
	    }

	    @Bean
	    MessagePublisher redisPublisher() {
	        return new RedisMessagePublisher(redisTemplate(), topic());
	    }

	
	@Bean
	ChannelTopic topic() {
		return new ChannelTopic("pubsub:pank-channel");
	}
	
	/*
	 * @Bean CacheManager cacheManager() { return new C(); }
	 */
}
