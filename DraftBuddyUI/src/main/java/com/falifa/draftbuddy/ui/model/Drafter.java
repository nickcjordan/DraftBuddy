package com.falifa.draftbuddy.ui.model;

import java.util.ArrayList;
import java.util.List;

import com.falifa.draftbuddy.api.sleeper.model.SleeperUser;
import com.falifa.draftbuddy.ui.model.team.Team;
import com.falifa.draftbuddy.ui.results.DraftResultStatistics;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Drafter {

	private String name;
	private int draftOrderNumber;
	private Team draftedTeam;
	private DraftResultStatistics draftResultStats;
	private boolean optimized;
	private List<Integer> draftPickIndices;
	private SleeperUser sleeperUser;
	
	public Drafter(String name, int draftOrderNumber, Team draftedTeam) {
		this.name = name;
		this.draftOrderNumber = draftOrderNumber;
		this.draftedTeam = draftedTeam;
		this.draftPickIndices = new ArrayList<Integer>();
	}
	
	
	public Drafter(SleeperUser sleeperUser, int draftPosition) {
		this.name = sleeperUser.getDisplayName();
		this.draftOrderNumber = draftPosition;
		this.draftedTeam = new Team(sleeperUser.getDisplayName(), "", draftPosition, "");
		this.draftPickIndices = new ArrayList<Integer>();	
	}

	@JsonIgnore
	public boolean hasDraftPickIndex(int index) {
		boolean hasIt = draftPickIndices.contains(index);
		return hasIt;
	}
	
	public SleeperUser getSleeperUser() {
		return sleeperUser;
	}

	public void setSleeperUser(SleeperUser sleeperUser) {
		this.sleeperUser = sleeperUser;
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

	public List<Integer> getDraftPickIndices() {
		return draftPickIndices;
	}

	public void setDraftPickIndices(List<Integer> draftPickIndices) {
		this.draftPickIndices = draftPickIndices;
	}
	
}
