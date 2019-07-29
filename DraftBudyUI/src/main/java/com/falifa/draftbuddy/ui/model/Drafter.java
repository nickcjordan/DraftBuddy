package com.falifa.draftbuddy.ui.model;

import com.falifa.draftbuddy.ui.results.DraftResultStatistics;

public class Drafter {

	private String name;
	private int draftOrderNumber;
	private Team draftedTeam;
	private DraftResultStatistics draftResultStats;
	private boolean optimized;
	
	public Drafter(String name, int draftOrderNumber, Team draftedTeam) {
		this.name = name;
		this.draftOrderNumber = draftOrderNumber;
		this.draftedTeam = draftedTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDraftOrderNumber() {
		return draftOrderNumber;
	}

	public void setDraftOrderNumber(int draftOrderNumber) {
		this.draftOrderNumber = draftOrderNumber;
	}

	public Team getDraftedTeam() {
		return draftedTeam;
	}

	public void setDraftedTeam(Team draftedTeam) {
		this.draftedTeam = draftedTeam;
	}

	public void setDraftResultStats(DraftResultStatistics draftResultStats) {
		this.draftResultStats = draftResultStats;
	}

	public DraftResultStatistics getDraftResultStats() {
		return draftResultStats;
	}

	public boolean isOptimized() {
		return optimized;
	}

	public void setOptimized(boolean optimized) {
		this.optimized = optimized;
	}
	
}
