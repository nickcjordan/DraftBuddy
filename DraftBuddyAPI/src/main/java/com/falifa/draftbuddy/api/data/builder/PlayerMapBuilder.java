package com.falifa.draftbuddy.api.data.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.data.PlayerStatsAPIDelegate;
import com.falifa.draftbuddy.api.data.ThreadRunner;
import com.falifa.draftbuddy.api.data.model.RawPlayerStats;
import com.falifa.draftbuddy.api.data.model.WeekStatsResponse;
import com.falifa.draftbuddy.api.model.PlayerTO;

@Component
public class PlayerMapBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMapBuilder.class);

	@Value("${data.weeksInSeason}")
	private String weeksInSeason;
	
	@Value("${data.cache.active}")
	private boolean cacheIsActive;
	
	@Autowired
	private PlayerStatsAPIDelegate delegate;

	@Autowired
	private PositionStatsDetailsBuilder statsBuilder;
	
	@Autowired
	private DataFileCache cache;
	
	@Autowired
	private UrlBuilder urls;
	
	public Map<String, PlayerTO> buildAndPopulatePlayerMap() {
		if (cacheIsActive) {
			return cache.getCachedCompleteData();
		}
		
		Map<String, PlayerTO> players = buildPlayerMapWithRawStats();
		log.info("Retrieved raw stats for {} players", players.size());
		cache.updateCacheWithRawStats(players);
		
		List<PlayerTO> playersToRemove = Collections.synchronizedList(new ArrayList<PlayerTO>());
		
		log.info("Initiating iterative threads...");
		Iterator<PlayerTO> iterator = players.values().iterator();
		int count = 0;
		ExecutorService executor = Executors.newCachedThreadPool();
		
		while (iterator.hasNext()) {
			count++;
			PlayerTO player = iterator.next();
			String baseUrl = urls.buildMetaDataBaseUrl();
			executor.execute(new ThreadRunner(playersToRemove, player, baseUrl));
			if (count >= 150) {
				waitForThreads(executor);
				executor = Executors.newCachedThreadPool();
				count = 0;
			}
		}
		waitForThreads(executor);
		
		log.info("Filtered out {} players to remove", playersToRemove.size());
		
		for (PlayerTO playerToRemove : playersToRemove) {
			players.remove(playerToRemove.getPlayerId());
		}
		
		cache.updateCacheWithCompleteData(players);
		return players;
	}
	
	private Map<String, PlayerTO> buildPlayerMapWithRawStats() {
		Map<String, PlayerTO> players = new HashMap<String, PlayerTO>();
		for (int week = 1; week <= Integer.valueOf(weeksInSeason); week++) {
			populatePlayerMapWithWeeklyStats(players, delegate.retrieveAllWeekStats(week));
		}
		log.info("Retrieved all weeklyStats");
		return players;
	}

	private void populatePlayerMapWithWeeklyStats(Map<String, PlayerTO> players, WeekStatsResponse response) {
		for (Entry<String, RawPlayerStats> entry : response.getStats().entrySet()) {
			PlayerTO player = (players.containsKey(entry.getKey())) ? players.get(entry.getKey()) : new PlayerTO(entry.getKey());
			player.getStatsByWeek().put(Integer.toString(response.getWeekNumber()), statsBuilder.buildPositionStatsDetails(entry.getValue()));
			players.put(player.getPlayerId(), player);
		}
	}
	
	private void waitForThreads(ExecutorService executor) {
		executor.shutdown();
		try {
			executor.awaitTermination(30, TimeUnit.SECONDS);
			log.info("All threads have returned");
		} catch (InterruptedException e) {
			log.error("ERROR: ", e);
		}
	}

}
