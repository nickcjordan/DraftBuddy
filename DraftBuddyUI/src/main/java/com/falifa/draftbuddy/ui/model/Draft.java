package com.falifa.draftbuddy.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Draft {

//	int roundNumber;
	List<Drafter> drafters;
	String[] orderedNames;
	
	public Draft(String[] orderedNames){
		this.orderedNames = orderedNames;
		drafters = new ArrayList<Drafter>(orderedNames.length);
		
		for (int draftPosition = 0; draftPosition < orderedNames.length; draftPosition++){
			drafters.add(draftPosition, buildDrafter(orderedNames[draftPosition], draftPosition+1));
		}
		
	}

	private Drafter buildDrafter(String name, int draftPosition) {
		return new Drafter(name, draftPosition, new Team(name, "", draftPosition, ""));
	}

	public void reverseOrder() {
		Collections.reverse(drafters);
	}

//	public int getRoundNumber() {
//		return roundNumber;
//	}
//
//	public void increaseRoundNumber() {
//		this.roundNumber++;
//	}

	public List<Drafter> getDrafters() {
		return drafters;
	}

	public void setDrafters(List<Drafter> drafters) {
		this.drafters = drafters;
	}

	public String[] getOrderedNames() {
		return orderedNames;
	}

	public void setOrderedNames(String[] orderedNames) {
		this.orderedNames = orderedNames;
	}

//	public void setRoundNumber(int roundNumber) {
//		this.roundNumber = roundNumber;
//	}
//	
	
}
