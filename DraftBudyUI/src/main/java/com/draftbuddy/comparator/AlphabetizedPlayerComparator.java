package com.draftbuddy.comparator;

import java.util.Comparator;

import com.draftbuddy.model.Player;
import com.draftbuddy.model.Team;

public class AlphabetizedPlayerComparator implements Comparator<Player>{
	
	public int compare(Player p1, Player p2){
		return (p1.getPlayerName()).compareTo(p2.getPlayerName());
	}
}
