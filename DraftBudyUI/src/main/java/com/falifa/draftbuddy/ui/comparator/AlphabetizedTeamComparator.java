package com.falifa.draftbuddy.ui.comparator;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.Team;

public class AlphabetizedTeamComparator implements Comparator<Team>{
	
	public int compare(Team t1, Team t2){
		return (t1.getCity()).compareTo(t2.getCity());
	}
}
