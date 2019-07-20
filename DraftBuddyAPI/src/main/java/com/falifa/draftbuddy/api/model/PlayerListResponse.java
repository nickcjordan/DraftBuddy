package com.falifa.draftbuddy.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerListResponse {
	
	@JsonProperty
	private List<Player> players;
	
	public PlayerListResponse() {}

	public PlayerListResponse(List<Player> players) {
		this.players = players;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
