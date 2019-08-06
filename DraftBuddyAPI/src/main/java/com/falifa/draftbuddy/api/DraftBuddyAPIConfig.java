package com.falifa.draftbuddy.api;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@Configuration
public class DraftBuddyAPIConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate(new SimpleClientHttpRequestFactory());
//		template.set
		return template;
	}
	
	@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(	
        															new ConcurrentMapCache("rawStatsPlayersMap"), 
//        															new ConcurrentMapCache("weekStatsResponse"), 
//        															new ConcurrentMapCache("searchedMovieDetails"),
        															new ConcurrentMapCache("allPlayers"),
        															new ConcurrentMapCache("weekStatsResponse")
        															));
        return cacheManager;
    }

}
