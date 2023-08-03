package com.falifa.draftbuddy.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.falifa.draftbuddy.api.data.builder.DataFileCache;
import com.falifa.draftbuddy.api.model.PlayerTO;

@Component
public class PlayerDataStorage {

	private Map<String, PlayerTO> playerIdMap;
	private Map<String, PlayerTO> playerNameMap;
	private List<PlayerTO> players;
	
	@Autowired
	private DataFileCache cache;
	
	public List<PlayerTO> getAllPlayers() {
		return players;
	}
	
	public PlayerTO getPlayerById(String id) {
		return playerIdMap.get(id);
	}
	
	public PlayerTO getPlayerByName(String name) {
		return playerNameMap.get(name);
	}

	public boolean updateState(Map<String, PlayerTO> map) {
		try {
			playerIdMap.putAll(map);
			cache.updateCacheWithCompleteData(playerIdMap);
			build(false);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean resetStateToCache() {
		try {
			build(true);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void build(boolean setFromCache) {
		if (setFromCache) {
			playerIdMap = cache.getCachedCompleteData();
		}
		players = playerIdMap.values().stream().collect(Collectors.toList());
		playerNameMap = buildPlayerNameMap();
	}

	private Map<String, PlayerTO> buildPlayerNameMap() {
		Map<String, PlayerTO> temp = new HashMap<String, PlayerTO>();
		playerIdMap.forEach((String id, PlayerTO p) -> addNameToMapIfNotNull(temp, p));
		return temp;
	}
		
	private void addNameToMapIfNotNull(Map<String, PlayerTO> map, PlayerTO p) {
		if (!StringUtils.isEmpty(p.getPlayerName())) {
			map.put(p.getPlayerName(), p);
		}
	}

	public Map<String, PlayerTO> getPlayerNameMap() {
		return playerNameMap;
	}


}
