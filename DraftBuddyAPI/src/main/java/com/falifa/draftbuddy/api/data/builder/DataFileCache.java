package com.falifa.draftbuddy.api.data.builder;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.model.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataFileCache {
	
	private static final Logger log = LoggerFactory.getLogger(DataFileCache.class);
	
	private static final String CACHED_DATA_PATH = "src/main/resources/stats/players.json";

	public Map<String, Player> getCachedData() {
		Map<String, Player> map = null;
		try {
			map = new ObjectMapper().readValue(new File(CACHED_DATA_PATH), new TypeReference<Map<String,Player>>(){});
		} catch (IOException e) {
			log.error("ERROR :: ", e);
		}
		return map;
	}

	public void updateCacheWithData(Map<String, Player> players) {
		log.info("updating data in cache..." );
		try {
			new ObjectMapper().writeValue(new File(CACHED_DATA_PATH), players);
		} catch (IOException e) {
			log.error("ERROR :: ", e);
		}
	}
	
}
