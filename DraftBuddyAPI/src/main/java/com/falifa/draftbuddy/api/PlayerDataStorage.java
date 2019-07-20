package com.falifa.draftbuddy.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.data.builder.DataFileCache;
import com.falifa.draftbuddy.api.model.Player;

@Component
public class PlayerDataStorage {

	private Map<String, Player> playerIdMap;
	private Map<String, Player> playerNameMap;
	private List<Player> players;
	
	@Autowired
	private DataFileCache cache;
	
	public List<Player> getAllPlayers() {
		return players;
	}
	
	public Player getPlayerById(String id) {
		return playerIdMap.get(id);
	}
	
	public Player getPlayerByName(String name) {
		return playerNameMap.get(name);
	}

	public boolean updateState(Map<String, Player> map) {
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

	private Map<String, Player> buildPlayerNameMap() {
		Map<String, Player> temp = new HashMap<String, Player>();
		playerIdMap.forEach((String id, Player p) -> temp.put(p.getPlayerName(), p));
		return temp;
	}
	
	

}
