package com.falifa.draftbuddy.ui.model;

import java.util.Map;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class MasterTeamTO {
	
	@JsonProperty
	private Map<String, NFLTeam> teams;
	
	@JsonCreator
	public MasterTeamTO() {}

	public MasterTeamTO(Map<String, NFLTeam> teams) {
		this.teams = teams;
	}

	@JsonGetter
	public Map<String, NFLTeam> getTeams() {
		return teams;
	}

	@JsonSetter
	public void setTeams(Map<String, NFLTeam> teams) {
		this.teams = teams;
	}

}
