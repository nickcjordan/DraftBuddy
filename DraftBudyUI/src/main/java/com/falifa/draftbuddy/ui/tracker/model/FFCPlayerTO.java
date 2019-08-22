package com.falifa.draftbuddy.ui.tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FFCPlayerTO {
	
	@JsonProperty("player_id")
	private String id;
	private String name;
	@JsonProperty("player_id")
	public String getId() {
		return id;
	}
	@JsonProperty("player_id")
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
