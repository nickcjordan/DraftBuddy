package com.falifa.draftbuddy.ui.prep.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleeperADPItemPlayer {
	
	private String unformatted;

	public String getUnformatted() {
		return unformatted;
	}

	public void setUnformatted(String unformatted) {
		this.unformatted = unformatted;
	}
	
}
