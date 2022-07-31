package com.falifa.draftbuddy.ui.prep.scraper;

public class TeammateTO {

	private String id;
	private String name;
	
	public TeammateTO() { super(); }
	
	public TeammateTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
