package com.draftbuddy.constants;

public enum CSVFieldMapping {
	
	RANK(0),
	PLAYER_NAME(2),
	TEAM_NAME(3),
	POS(4),
	BYE(5),
	BEST(6),
	WORST(7),
	AVG(8),
	STD_DEV(9),
	ADP(10),
	VERSUS_ADP(11);
	
	private int index;
	
	CSVFieldMapping(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
