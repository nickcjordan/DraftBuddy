package com.falifa.draftbuddy.api.data.model;

import java.util.Map;

import com.falifa.draftbuddy.api.model.Player;

public class PlayerStatsAPIResponse {
	
	private Map<String, Player> players;
	
	public PlayerStatsAPIResponse() {}

	public PlayerStatsAPIResponse(Map<String, Player> players) {
		this.players = players;
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, Player> players) {
		this.players = players;
	}
	
}
