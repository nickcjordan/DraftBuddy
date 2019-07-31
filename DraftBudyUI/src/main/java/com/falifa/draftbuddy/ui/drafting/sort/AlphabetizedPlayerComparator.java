package com.falifa.draftbuddy.ui.drafting.sort;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.Team;
import com.falifa.draftbuddy.ui.model.player.Player;

public class AlphabetizedPlayerComparator implements Comparator<Player>{
	
	public int compare(Player p1, Player p2){
		return (p1.getPlayerName()).compareTo(p2.getPlayerName());
	}
}
