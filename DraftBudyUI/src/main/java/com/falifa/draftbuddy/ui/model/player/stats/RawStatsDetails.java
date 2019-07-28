package com.falifa.draftbuddy.ui.model.player.stats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RawStatsDetails {
	
	private int weeksOfData;
	private LinkedHashMap<String, StatisticCategory> stats; // Map<category-name, category-object>
	
	public RawStatsDetails() {
		this.stats = new LinkedHashMap<String, StatisticCategory>();
	}

	public Map<String, StatisticCategory> getStats() {
		return stats;
	}

	public void addStatCategory(StatisticCategory category) {
		this.stats.put(category.getName(), category);
	}
	
	@JsonIgnore
	public List<StatisticCategory> getStatsList() {
		return new ArrayList<StatisticCategory>(stats.values());
	}
	
	@JsonIgnore
	public StatisticCategory getStatCategory(String nameOfCategory) {
		return stats.get(nameOfCategory);
	}

	public int getWeeksOfData() {
		return weeksOfData;
	}

	public void setWeeksOfData(int weeksOfData) {
		this.weeksOfData = weeksOfData;
	}

	public void setStats(LinkedHashMap<String, StatisticCategory> stats) {
		this.stats = stats;
	}
	
}
