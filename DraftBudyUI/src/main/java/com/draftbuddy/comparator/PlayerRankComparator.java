package com.draftbuddy.comparator;

import java.util.Comparator;

import com.draftbuddy.model.Player;

public class PlayerRankComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2){
		//Print.info(String.format("in rank comparator: %s %s | %s %s",  p1.getPlayerName(), p1.getRank(), p2.getPlayerName(), p2.getRank()));
		return Integer.valueOf(p1.getRank()).compareTo(Integer.valueOf(p2.getRank()));
	}
}
