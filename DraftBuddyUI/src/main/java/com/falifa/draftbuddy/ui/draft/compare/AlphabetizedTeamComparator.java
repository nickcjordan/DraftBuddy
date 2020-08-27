package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.Team;

public class AlphabetizedTeamComparator implements Comparator<NFLTeam>{
	
	public int compare(NFLTeam t1, NFLTeam t2){
		return (t1.getTeam().getCity()).compareTo(t2.getTeam().getCity());
	}
}
