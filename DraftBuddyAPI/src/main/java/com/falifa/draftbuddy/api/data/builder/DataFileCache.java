package com.falifa.draftbuddy.api.data.builder;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.model.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataFileCache {
	
	private static final Logger log = LoggerFactory.getLogger(DataFileCache.class);
	
	@Value("${data.cache.path.complete}")
	private String cachedComleteDataPath;
	
	@Value("${data.cache.path.raw}")
	private String cachedRawStatsPath;

	public Map<String, Player> getCachedCompleteData() {
		Map<String, Player> map = null;
		try {
			map = new ObjectMapper().readValue(new File(cachedComleteDataPath), new TypeReference<Map<String,Player>>(){});
		} catch (IOException e) {
			log.error("ERROR :: ", e);
		}
		return map;
	}

	public void updateCacheWithCompleteData(Map<String, Player> players) {
		log.info("updating complete data in cache..." );
		try {
			new ObjectMapper().writeValue(new File(cachedComleteDataPath), players);
		} catch (IOException e) {
			log.error("ERROR :: ", e);
		}
	}
	
	public Map<String, Player> getCachedRawStats() {
		Map<String, Player> map = null;
		try {
			map = new ObjectMapper().readValue(new File(cachedRawStatsPath), new TypeReference<Map<String,Player>>(){});
		} catch (IOException e) {
			log.error("ERROR :: ", e);
		}
		return map;
	}

	public void updateCacheWithRawStats(Map<String, Player> players) {
		log.info("updating raw stats in cache..." );
		try {
			new ObjectMapper().writeValue(new File(cachedRawStatsPath), players);
		} catch (IOException e) {
			log.error("ERROR :: ", e);
		}
	}
	
}
