package com.falifa.draftbuddy.ui.prep.data.model;

import java.util.List;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;

public class HighestRemainingPositionInTierTO {
	
	private String pos;
	private Integer tier;
	private List<Player> players;
	
	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public Integer getTier() {
		return tier;
	}
	
	public void setTier(Integer tier) {
		this.tier = tier;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
