package com.falifa.draftbuddy.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Positions {
	
	public static final Position QUARTERBACK = new Position("QB", "Quarterback");
	public static final Position RUNNINGBACK = new Position("RB", "Running Back");
	public static final Position WIDERECEIVER = new Position("WR", "Wide Receiver");
	public static final Position TIGHTEND = new Position("TE", "Tight End");
	public static final Position KICKER = new Position("K", "Kicker");
	
	private static final Map<String, Position> POSITIONS;
	
	static {
		POSITIONS = new TreeMap<String, Position>();
		POSITIONS.put(QUARTERBACK.getAbbrev(), QUARTERBACK);
		POSITIONS.put(RUNNINGBACK.getAbbrev(), RUNNINGBACK);
		POSITIONS.put(WIDERECEIVER.getAbbrev(), WIDERECEIVER);
		POSITIONS.put(TIGHTEND.getAbbrev(), TIGHTEND);
		POSITIONS.put(KICKER.getAbbrev(), KICKER);
	}
	
	public static Position get(String abbrev) {
		return POSITIONS.get(abbrev);
	}
	
	public static List<Position> getAll() {
		return new ArrayList<Position>(POSITIONS.values());
	}
	
}
