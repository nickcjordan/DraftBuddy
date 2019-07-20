package com.falifa.draftbuddy.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.data.builder.PlayerMapBuilder;
import com.falifa.draftbuddy.api.model.Player;

@Component
public class PlayerManager {
	
	@Autowired
	private PlayerMapBuilder builder;

	@Cacheable("allPlayers")
	public List<Player> getAllPlayers() {
		Map<String, Player> map = builder.buildAndPopulatePlayerMap();
		return map.values().stream().collect(Collectors.toList());
	}

	

}
