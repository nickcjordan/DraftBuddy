package com.falifa.draftbuddy.api.model;

public class Position {
	
	private String abbrev;
	private String name;
	
	public Position() {}
	
	public Position(String abbrev, String name){
		this.abbrev = abbrev;
		this.name = name;
	}
	public String getAbbrev(){
		return abbrev;
	}
	public String getName(){
		return name;
	}
	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}
	public void setName(String name) {
		this.name = name;
	}

}
