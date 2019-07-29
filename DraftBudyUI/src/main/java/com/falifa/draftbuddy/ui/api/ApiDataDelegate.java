package com.falifa.draftbuddy.ui.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.falifa.draftbuddy.api.model.PlayerMapResponse;
import com.falifa.draftbuddy.api.model.PlayerTO;

@Component
public class ApiDataDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(ApiDataDelegate.class);
	
	private static final String API_PLAYER_MAP_URL = "http://localhost:8081/api/players/map";
	private RestTemplate restTemplate;

	public ApiDataDelegate() {
		this.restTemplate = new RestTemplate();
	}
	
	public Map<String, PlayerTO> getPlayersMapFromApi() {
		Map<String, PlayerTO> players = null;
		try {
			PlayerMapResponse response = restTemplate.getForObject(API_PLAYER_MAP_URL, PlayerMapResponse.class);
			players = response.getPlayers();
			log.info("Got {} players in response from Player Stats API", players.size());
		} catch (Exception e) {
			log.error("ERROR calling Player Stats API :: " + e.getMessage(), e);
		}
		return players;
	}

}