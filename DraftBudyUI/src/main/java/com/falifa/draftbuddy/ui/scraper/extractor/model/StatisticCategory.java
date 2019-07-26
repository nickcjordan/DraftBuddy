package com.falifa.draftbuddy.ui.scraper.extractor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.model.player.StatisticValue;

public class StatisticCategory {
	
	private int colspan;
	private String name;
	private List<StatisticValue> columns;
	
	public StatisticCategory() {
		this.columns = new ArrayList<StatisticValue>();
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
		columns.add(new StatisticValue(name, value));
	}
	public List<StatisticValue> getColumns() {
		return columns;
	}
	public void setColumns(List<StatisticValue> columns) {
		this.columns = columns;
	}
}
