package com.falifa.draftbuddy.ui.model;

public class DrafterSummary {

	Drafter drafter;
	String avatar;
	String teamName;
	int roundNumber;
	int roundIndex;

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public int getRoundIndex() {
		return roundIndex;
	}

	public void setRoundIndex(int roundIndex) {
		this.roundIndex = roundIndex;
	}

	public Drafter getDrafter() {
		return drafter;
	}

	public void setDrafter(Drafter drafter) {
		this.drafter = drafter;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}
