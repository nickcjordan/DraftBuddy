package com.falifa.draftbuddy.ui.constants;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.controller.BaseController;

public enum DraftType {
	
	REAL_DRAFT("real"),
	AUTO_DRAFT("auto"),
	MOCK_DRAFT("mock");

	private String[] order;
	private String type;
	
	DraftType(String type) {
		this.type = type;
		this.order = getDraftOrder(type);
	}

	private String[] getDraftOrder(String t) {
		switch(t) {
			case "real"		: return realOrder();
			case "auto"	: return getAuto();
			case "mock"	: return getMock();
		}
		Log.err("did not find order: " + t);
		return null;
	}

	private String[] getMock() {
//		return FALIFA_LEAGUE;
//		return NEELY_LEAGUE;
		return WHOLE_FAMILY_LEAGUE;	
		}
		
	private String[] getAuto() {
//		return FALIFA_LEAGUE;
//		return NEELY_LEAGUE;
		return WHOLE_FAMILY_LEAGUE;	
		}
	
	private String[] realOrder() {
//		return FALIFA_LEAGUE;			
//		return NEELY_LEAGUE;
		return WHOLE_FAMILY_LEAGUE;
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
				if (type.equals("mock") || type.equals("auto")) {
					BaseController.mockDraftMode = true;
				}
				return dt;
			}
		}
		Log.err("Could not find DraftType of \"" + type + "\".");
		return null;
	}
	
	private String[] FALIFA_LEAGUE = new String[]{
			"Chris T",
			"Chris R",
			"Will",
			"Matt",
			"Nick J",
			"Ryan",
			"Josh",
			"Mason",
			"Nick W",
			"Austin",
			"Dan",
			"Scott"
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
