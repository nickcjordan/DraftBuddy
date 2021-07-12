package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.team.Team;

public class AlphabetizedPlayerComparator implements Comparator<Player>{
	
	public int compare(Player p1, Player p2){
		return (p1.getPlayerName()).compareTo(p2.getPlayerName());
	}
}
