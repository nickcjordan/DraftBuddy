package com.falifa.draftbuddy.ui.model.player;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatistics {
	
	private String totalPoints;
	private String totalTargets;
	private String avgTargets;

	private Map<String, String> stats;
	
	public PlayerStatistics() {
		this.stats = new HashMap<String, String>();
	}

	public String getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getTotalTargets() {
		return totalTargets;
	}

	public void setTotalTargets(String totalTargets) {
		this.totalTargets = totalTargets;
	}

	public String getAvgTargets() {
		return avgTargets;
	}

	public void setAvgTargets(String avgTargets) {
		this.avgTargets = avgTargets;
	}

	public Map<String, String> getStats() {
		return stats;
	}

	public void setStats(Map<String, String> stats) {
		this.stats = stats;
	}
	
	public void addStat(String key, String val) {
		this.stats.put(key, val);
	}

}
