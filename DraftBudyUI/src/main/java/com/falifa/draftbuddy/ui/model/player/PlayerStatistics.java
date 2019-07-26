package com.falifa.draftbuddy.ui.model.player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.scraper.extractor.model.StatisticCategory;

public class PlayerStatistics {

	private LinkedHashMap<String, StatisticCategory> stats; // Map<category-name, category-object>
	
	public PlayerStatistics() {
		this.stats = new LinkedHashMap<String, StatisticCategory>();
	}

	public Map<String, StatisticCategory> getStats() {
		return stats;
	}

	public void addStatCategory(StatisticCategory category) {
		this.stats.put(category.getName(), category);
	}
	
	public List<StatisticCategory> getStatsList() {
		return new ArrayList<StatisticCategory>(stats.values());
	}

}
