package com.falifa.draftbuddy.ui.constants;

public enum Position {

	QUARTERBACK("QB", "Quarterbacks", "badge-warning"),
	RUNNINGBACK("RB", "Running Backs", "badge-info"),
	WIDERECEIVER("WR", "Wide Receivers", "badge-success"),
	TIGHTEND("TE", "Tight Ends", "badge-error"),
	KICKER("K", "Kickers", ""),
	DEFENSE("DST", "Defenses/Special Teams", "badge-inverse");
	
	private String abbrev;
	private String name;
	private String badgeClass;

	
	Position(String abbrev, String name, String badgeClass){
		this.abbrev = abbrev;
		this.name = name;
		this.badgeClass = badgeClass;
	}
	
	public String getAbbrev(){
		return abbrev;
	}
	
	public String getName(){
		return name;
	}
	
	public String getBadgeClass() {
		return badgeClass;
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
