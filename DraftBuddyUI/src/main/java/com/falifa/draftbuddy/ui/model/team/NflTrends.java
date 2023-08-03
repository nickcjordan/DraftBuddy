package com.falifa.draftbuddy.ui.model.team;

public class NflTrends {
	
	// derived score to represent how many players from this team are highly drafted
	public Integer draftZoneScore;
	public Integer passCatcherDraftZoneScore;

	public Double averageAdp;
	public Double passCatcherAverageAdp;
	
	
	public Integer draftRank;
	public Integer passCatcherDraftRank;

	public Integer getDraftZoneScore() {
		return draftZoneScore;
	}

	public void setDraftZoneScore(Integer draftZoneScore) {
		this.draftZoneScore = draftZoneScore;
	}

	public Double getAverageAdp() {
		return averageAdp;
	}

	public void setAverageAdp(Double averageAdp) {
		this.averageAdp = averageAdp;
	}

	public Integer getDraftRank() {
		return draftRank;
	}

	public void setDraftRank(Integer draftRank) {
		this.draftRank = draftRank;
	}

	public Integer getPassCatcherDraftZoneScore() {
		return passCatcherDraftZoneScore;
	}

	public void setPassCatcherDraftZoneScore(Integer passcatcherDraftZoneScore) {
		this.passCatcherDraftZoneScore = passcatcherDraftZoneScore;
	}

	public Integer getPassCatcherDraftRank() {
		return passCatcherDraftRank;
	}

	public void setPassCatcherDraftRank(Integer passCatcherDraftRank) {
		this.passCatcherDraftRank = passCatcherDraftRank;
	}

	public Double getPassCatcherAverageAdp() {
		return passCatcherAverageAdp;
	}

	public void setPassCatcherAverageAdp(Double passCatcherAverageAdp) {
		this.passCatcherAverageAdp = passCatcherAverageAdp;
	}

	
	
}
