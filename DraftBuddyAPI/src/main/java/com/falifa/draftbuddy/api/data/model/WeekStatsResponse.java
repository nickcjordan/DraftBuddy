package com.falifa.draftbuddy.api.data.model;

import java.util.Map;

import com.falifa.draftbuddy.api.data.deserializer.WeekStatsResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(using = WeekStatsResponseDeserializer.class)
public class WeekStatsResponse {
	
	private int weekNumber;
	private Map<String, RawPlayerStats> stats; // Map< PlayerId, PlayerStats>
	
	public WeekStatsResponse(int weekNumber, Map<String, RawPlayerStats> stats) {
		this.weekNumber = weekNumber;
		this.stats = stats;
	}
	
	public WeekStatsResponse(Map<String, RawPlayerStats> stats) {
		this.stats = stats;
	}

	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public Map<String, RawPlayerStats> getStats() {
		return stats;
	}
	public void setStats(Map<String, RawPlayerStats> stats) {
		this.stats = stats;
	}
	
}
