package com.falifa.draftbuddy.api.data.model;

import java.util.Map;

public class RawPlayerStats {
	
	private String playerId;
	private String weekNumber;
	private Map<String, String> stats; // Map< ambiguousStat, value > coming from response
	
	public RawPlayerStats(String playerId, String weekNumber, Map<String, String> stats) {
		this.playerId = playerId;
		this.weekNumber = weekNumber;
		this.stats = stats;
	}

	public Map<String, String> getStats() {
		return stats;
	}

	public void setStats(Map<String, String> stats) {
		this.stats = stats;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(String weekNumber) {
		this.weekNumber = weekNumber;
	}
	
}
