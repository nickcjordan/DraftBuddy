package com.falifa.draftbuddy.ui.constants;

import java.util.Arrays;

public enum DraftType {
	
	REAL_DRAFT("real"),
	AUTO_DRAFT("auto"),
	MOCK_DRAFT("mock"),
	TRACKED_DRAFT("track");

	private String[] order;
	private String type;
	
	DraftType(String type) {
		this.type = type;
		this.order = getDraftOrder(type);
	}

	private String[] getDraftOrder(String t) {
		switch(t) {
			case "real"		: return currentSet();
			case "auto"	: return currentSet();
			case "mock"	: return currentSet();
			case "track" 	: String[] picks = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}; return picks;
		}
		return null;
	}

	private String[] currentSet() {
		return FALIFA_LEAGUE;
//		return NEELY_LEAGUE;
//		return WHOLE_FAMILY_LEAGUE;	
	}

	public String[] getOrder() {
		return order;
	}

	public void setOrder(String[] order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public static DraftType getDraftType(String type) {
		for (DraftType dt : DraftType.values()) {
			if (dt.getType().equals(type)) {
				return dt;
			}
		}
		return null;
	}
	
	private String[] FALIFA_LEAGUE = new String[]{
			"Nick J",
			"Nick W",
			"Josh",
			"Austin",
			"Scott",
			"Mason",
			"Dan",
			"Ryan",
			"Chris R",
			"Will",
			"Matt",
			"Chris T"
	};
	
	private String[] NEELY_LEAGUE = new String[]{
			"Dad",
			"Brian",
			"Chris",
			"Tyler",
			"Nick J",
			"Dan F",
			"Dan M",
			"Jay",
	};
	
	private String[] WHOLE_FAMILY_LEAGUE = new String[]{
			"Brittany",
			"Diane",
			"Mom",
			"Heather",
			"Dianes Dad",
			"Dad",
			"Nick J", 
			"Michael",
			"Chris",
			"Claire",
			"Nicole",
			"Katie",
			"Brittany's Boyfriend",
			"Jason"
	};
}
