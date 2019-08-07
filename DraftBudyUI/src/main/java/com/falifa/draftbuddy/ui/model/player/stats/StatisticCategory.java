package com.falifa.draftbuddy.ui.model.player.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StatisticCategory {
	
	private int colspan;
	private String name;
	private List<StatisticValue> columns;
	private Map<String, StatisticValue> stats;
	
	public StatisticCategory() {
		this.columns = new ArrayList<StatisticValue>();
		this.stats = new HashMap<String, StatisticValue>();
	}
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void addColumn(String name, String value) {
		StatisticValue val = new StatisticValue(name, value);
		columns.add(val);
		stats.put(name.toUpperCase(), val);
	}
	public List<StatisticValue> getColumns() {
		return columns;
	}
	public void setColumns(List<StatisticValue> columns) {
		this.columns = columns;
	}
	@JsonIgnore
	public String getStat(String key) {
		return stats.get(key.toUpperCase()).getValue();
	}
	@JsonIgnore
	public StatisticValue getStatisticValue(String key) {
		return stats.get(key.toUpperCase());
	}
	public Map<String, StatisticValue> getStats() {
		return stats;
	}
	public void setStats(Map<String, StatisticValue> stats) {
		this.stats = stats;
	}
	@Override
	public String toString() {
		return getName() + " :: " + columns.stream().map(val -> val.toString()).collect(Collectors.joining(","));
	}
	
	
}
