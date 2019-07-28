package com.falifa.draftbuddy.api.data.model;

import java.util.Map;

import com.falifa.draftbuddy.api.model.PlayerTO;

public class PlayerStatsAPIResponse {
	
	private Map<String, PlayerTO> players;
	
	public PlayerStatsAPIResponse() {}

	public PlayerStatsAPIResponse(Map<String, PlayerTO> players) {
		this.players = players;
	}

	public Map<String, PlayerTO> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, PlayerTO> players) {
		this.players = players;
	}
	
}
