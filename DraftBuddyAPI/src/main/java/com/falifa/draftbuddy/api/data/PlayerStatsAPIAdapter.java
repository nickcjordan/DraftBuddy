package com.falifa.draftbuddy.api.data;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.data.builder.DataFileCache;
import com.falifa.draftbuddy.api.data.builder.PlayerMapBuilder;
import com.falifa.draftbuddy.api.data.model.PlayerStatsAPIResponse;
import com.falifa.draftbuddy.api.model.PlayerTO;

@Component
public class PlayerStatsAPIAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerStatsAPIAdapter.class);

	@Value("${data.cache.active}")
	private boolean useCachedData;

	@Autowired
	private DataFileCache cache;

	@Autowired
	private PlayerMapBuilder playerBuilder;

	public PlayerStatsAPIResponse getAllPlayerStats() {
		Map<String, PlayerTO> players = null;
		if (useCachedData) { 	// return cached data
			log.info("Using cached data...");
			players = cache.getCachedCompleteData();
		} else {						// build and return updated data
			log.info("Making calls to get updated data...");
			players = playerBuilder.buildAndPopulatePlayerMap();
			cache.updateCacheWithCompleteData(players);
		}
		return new PlayerStatsAPIResponse(players);
	}

}
