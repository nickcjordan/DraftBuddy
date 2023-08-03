package com.falifa.draftbuddy.ui.prep.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleeperADPItem {
	
	@JsonProperty("Player")
	private SleeperADPItemPlayer player;
	
	@JsonProperty("Redraft PPR ADP")
	private SleeperADPItemDetail adp;

	@JsonProperty("Player")
	public SleeperADPItemPlayer getPlayer() {
		return player;
	}

	@JsonProperty("Player")
	public void setPlayer(SleeperADPItemPlayer player) {
		this.player = player;
	}

	@JsonProperty("Redraft PPR ADP")
	public SleeperADPItemDetail getAdp() {
		return adp;
	}

	@JsonProperty("Redraft PPR ADP")
	public void setAdp(SleeperADPItemDetail adp) {
		this.adp = adp;
	}

}
