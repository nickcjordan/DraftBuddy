package com.draftbuddy.constants;

public enum Position {

	QUARTERBACK("QB", "Quarterbacks"),
	RUNNINGBACK("RB", "Running Backs"),
	WIDERECEIVER("WR", "Wide Receivers"),
	TIGHTEND("TE", "Tight Ends"),
	KICKER("K", "Kickers"),
	DEFENSE("DST", "Defenses/Special Teams");
	
	private String abbrev;
	private String name;

	
	Position(String abbrev, String name){
		this.abbrev = abbrev;
		this.name = name;
	}
	
	public String getAbbrev(){
		return abbrev;
	}
	
	public String getName(){
		return name;
	}

	public static Position get(String pos) {
		for (Position p : Position.values()) {
			if (p.getAbbrev().equals(pos)) {
				return p;
			}
		}
		return null;
	}
	
}
