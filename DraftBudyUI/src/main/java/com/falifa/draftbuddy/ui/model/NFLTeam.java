package com.falifa.draftbuddy.ui.model;

public class NFLTeam extends Team {

	String abbrev;
	String city;
	
	public NFLTeam(String city, String name, String abbrev, int id) {
		super(name, city, id, abbrev);
		this.abbrev = abbrev;
		this.city = city;
		this.fullName = city + " " + String.format("%-15s", name);
	}

	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
