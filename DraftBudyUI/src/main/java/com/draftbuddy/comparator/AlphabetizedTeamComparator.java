package com.draftbuddy.comparator;

import java.util.Comparator;

import com.draftbuddy.model.Team;

public class AlphabetizedTeamComparator implements Comparator<Team>{
	
	public int compare(Team t1, Team t2){
		return (t1.getCity()).compareTo(t2.getCity());
	}
}
