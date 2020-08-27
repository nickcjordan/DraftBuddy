package com.falifa.draftbuddy.ui.model;

import java.util.Map;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class MasterPlayersTO {
	
	@JsonProperty
	private Map<String, Player> players;
	
	@JsonCreator
	public MasterPlayersTO() {}

	public MasterPlayersTO(Map<String, Player> players) {
		this.players = players;
	}

	@JsonGetter
	public Map<String, Player> getPlayers() {
		return players;
	}

	@JsonSetter
	public void setPlayers(Map<String, Player> players) {
		this.players = players;
	}
	
}
