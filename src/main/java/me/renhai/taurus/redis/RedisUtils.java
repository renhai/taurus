//package me.renhai.taurus.redis;
//
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//public class RedisUtils {
//
//	public static <K, V> RedisTemplate<K, V> createRedisTemplate(RedisConnectionFactory connectionFactory,
//			Class<V> valueClass) {
//		RedisTemplate<K, V> redisTemplate = new RedisTemplate<K, V>();
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<V>(valueClass));
//
//		redisTemplate.setConnectionFactory(connectionFactory);
//		redisTemplate.afterPropertiesSet();
//		return redisTemplate;
//	}
//
//	public static RedisOperations<String, String> stringTemplate(RedisConnectionFactory redisConnectionFactory) {
//		return new StringRedisTemplate(redisConnectionFactory);
//	}
//}
