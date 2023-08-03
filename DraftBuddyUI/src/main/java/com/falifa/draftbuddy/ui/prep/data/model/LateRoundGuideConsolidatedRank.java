package com.falifa.draftbuddy.ui.prep.data.model;

public class LateRoundGuideConsolidatedRank {
	
	String overall;
	String positionRank;
	String tier;
	public String getOverall() {
		return overall;
	}
	public void setOverall(String overall) {
		this.overall = overall;
	}
	public String getPositionRank() {
		return positionRank;
	}
	public void setPositionRank(String positionRank) {
		this.positionRank = positionRank;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public LateRoundGuideConsolidatedRank(String overall, String positionRank, String tier) {
		super();
		this.overall = overall;
		this.positionRank = positionRank;
		this.tier = tier;
	}
	
	public LateRoundGuideConsolidatedRank() {};

	

}
