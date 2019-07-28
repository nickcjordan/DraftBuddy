package com.falifa.draftbuddy.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerListResponse {
	
	@JsonProperty
	private List<PlayerTO> players;
	
	public PlayerListResponse() {}

	public PlayerListResponse(List<PlayerTO> players) {
		this.players = players;
	}

	public List<PlayerTO> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerTO> players) {
		this.players = players;
	}

}
