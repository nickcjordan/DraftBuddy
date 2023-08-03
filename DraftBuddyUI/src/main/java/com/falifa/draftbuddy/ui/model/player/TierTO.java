package com.falifa.draftbuddy.ui.model.player;

import java.util.List;

public class TierTO {
	
	int tier;
	List<Player> players;
	
	public TierTO(int tier, List<Player> players) {
		super();
		this.tier = tier;
		this.players = players;
	}
	public int getTier() {
		return tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	

}
