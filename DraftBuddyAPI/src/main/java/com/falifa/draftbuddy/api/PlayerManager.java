package com.falifa.draftbuddy.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.data.builder.PlayerMapBuilder;
import com.falifa.draftbuddy.api.model.PlayerTO;

@Component
public class PlayerManager {
	
	@Autowired
	private PlayerMapBuilder builder;
	
	@Autowired
	private PlayerDataStorage data;

	public boolean updateAllPlayers() {
		Map<String, PlayerTO> map = builder.buildAndPopulatePlayerMap();
		return data.updateState(map);
	}
	
	public List<PlayerTO> updateAndGetAllPlayers() {
		Map<String, PlayerTO> map = builder.buildAndPopulatePlayerMap();
		data.updateState(map);
		List<PlayerTO> list = map.values().stream().collect(Collectors.toList());
		return list;
	}

	

}
