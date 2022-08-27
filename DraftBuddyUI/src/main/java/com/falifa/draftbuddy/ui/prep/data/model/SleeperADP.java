package com.falifa.draftbuddy.ui.prep.data.model;

public class SleeperADP {

	private String name;
	private Double adp;

	public SleeperADP() {
	}

	public SleeperADP(String name, Double adp) {
		this.name = name;
		this.adp = adp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAdp() {
		return adp;
	}

	public void setAdp(Double adp) {
		this.adp = adp;
	}

}
