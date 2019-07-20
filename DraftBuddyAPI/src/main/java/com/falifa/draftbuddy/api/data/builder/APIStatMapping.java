package com.falifa.draftbuddy.api.data.builder;

import java.util.HashMap;
import java.util.Map;

public enum APIStatMapping {
	
	TOTAL_POINTS("pts"),
	PASS_ATT("2"),
	PASS_CMP("3"),
	PASS_YRD("5"),
	PASS_TD("6"),
	PASS_INT("7"),
	PASS_SCK("8"),
	RUSH_ATT("13"),
	RUSH_YRD("14"),
	RUSH_TD("15"),
	REC_CMP("20"),
	REC_YRD("21"),
	REC_TD("22"),
	XP_CMP("33"),
	FG_0_20("35"),
	FG_20_30("36"),
	FG_30_40("37"),
	FG_40_50("38"),
	FG_50_PLUS("39");

	private String apiMapping;
	private static Map<String, APIStatMapping> mapping;
	
	static {
		mapping = new HashMap<String, APIStatMapping>();
		for (APIStatMapping val : APIStatMapping.values()) {
			mapping.put(val.getApiMapping(), val);
		}
	}

	private APIStatMapping(String apiMapping) {
		this.apiMapping = apiMapping;
	}

	public String getApiMapping() {
		return apiMapping;
	}

	public void setApiMapping(String apiMapping) {
		this.apiMapping = apiMapping;
	}
	
	public static APIStatMapping mapValue(String valToMap) {
		return mapping.get(valToMap);
	}
	
}
