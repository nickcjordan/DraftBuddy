package com.falifa.draftbuddy.ui.constants;

public enum StatCategory {

	PASSING("PASSING"),
	RECEIVING("RECEIVING"),
	RUSHING("RUSHING"),
	MISC("MISC"),
	ALL("ALL");
	
	
	private String value;

	public String getValue() {
		return value;
	}

	private StatCategory(String value) {
		this.value = value;
	}
	
	
	

}
