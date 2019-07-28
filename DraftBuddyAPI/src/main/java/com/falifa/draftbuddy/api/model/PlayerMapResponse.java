package com.falifa.draftbuddy.api.model;

import java.util.Map;

public class PlayerMapResponse {
	
	private Map<String, PlayerTO> players;

	public PlayerMapResponse() {}
	
	public PlayerMapResponse(Map<String, PlayerTO> players) {
		this.players = players;
	}
	
	public Map<String, PlayerTO> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, PlayerTO> players) {
		this.players = players;
	}
	
}
