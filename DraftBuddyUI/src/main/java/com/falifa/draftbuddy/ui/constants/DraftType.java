package com.falifa.draftbuddy.ui.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum DraftType {
	
	REAL_DRAFT("real"),
	AUTO_DRAFT("auto"),
	MOCK_DRAFT("mock"),
	SLEEPER("sleeper");

	private String[] order;
	private String type;
	
	DraftType(String type) {
		this.type = type;
		this.order = getDraftOrder(type);
	}

	private String[] getDraftOrder(String t) {
		switch(t) {
			case "sleeper"	: return SLEEPER_LEAGUE;
			case "real"	: return currentSet();
			case "auto"	: return currentSet();
			case "mock"	: return currentSet();
		}
		return null;
	}

	private String[] currentSet() {
		return FALIFA_LEAGUE;
//		return NEELY_LEAGUE;
//		return WHOLE_FAMILY_LEAGUE;	
	}
	
	private String[] randomSet() {
		List<String> list = Arrays.asList(FALIFA_LEAGUE);
//		List<String> list = Arrays.asList(NEELY_LEAGUE);
//		List<String> list = Arrays.asList(WHOLE_FAMILY_LEAGUE);
		Collections.shuffle(list);
		return (String[]) list.toArray();
	}
	
	public void shuffleOrder() {
		List<String> list = Arrays.asList(this.order);
		Collections.shuffle(list);
		this.order = (String[]) list.toArray();
	}

	public String[] getOrder() {
		return getDraftOrder(this.type);
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
	
	private String[] SLEEPER_LEAGUE = new String[]{};
	
	private String[] FALIFA_LEAGUE = new String[]{
			"Chris",
			"Matt",
			"Nick W",
			"Mason",
			"Scott",
			"Josh",
			"Hunter",
			"Nick J",
			"Will",
			"Ryan",
			"Austin",
			"Dan"
	};
	
	private String[] NEELY_LEAGUE = new String[]{
			"Dan F",
			"Chris",
			"Dan M",
			"Tyler",
			"Justin",
			"Jay",
			"Brian",
			"Dad",
			"Nick J"
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
