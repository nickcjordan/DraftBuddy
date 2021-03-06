package com.falifa.draftbuddy.ui.model.player.stats;

public class StatisticValue {
	
	private String name;
	private String value;
	
	public StatisticValue(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public StatisticValue() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return name + "=" + value;
	}
	
	

}
