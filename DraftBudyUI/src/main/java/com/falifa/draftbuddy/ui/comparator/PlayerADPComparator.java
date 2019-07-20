package com.falifa.draftbuddy.ui.comparator;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.Player;

public class PlayerADPComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2){
		//Print.info(String.format("in adp comparator: %s %s | %s %s",  p1.getPlayerName(), p1.getAdp(), p2.getPlayerName(), p2.getAdp()));
		return Integer.valueOf(p1.getAdp()).compareTo(Integer.valueOf(p2.getAdp()));
	}

}
