package com.falifa.draftbuddy.ui.constants;

public enum DraftZone {

	/* Round Definitions:
	 * 	- Early: 1-29
	 * 	- Dead Zone: 30-59
	 * 	- Middle: 60-108
	 * 	- Late: 109-
	 */
	EARLY_ROUNDS(4, 1, 30),
	DEAD_ZONE(3, 31, 60),
	MIDDLE_ROUNDS(2, 61, 108),
	LATE_ROUNDS(1, 109, 180),
	DART_THROW(0, 181, 1000);
	
	private int score;
	private int start;
	private int end;
	
	DraftZone(int s, int start, int end){
		this.score = s;
		this.start = start;
		this.end = end;
	}

	public int getScore() {
		return score;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
	
	

}
