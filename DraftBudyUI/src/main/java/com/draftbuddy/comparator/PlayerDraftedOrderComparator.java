package com.draftbuddy.comparator;

import java.util.Comparator;

import com.draftbuddy.model.Player;

public class PlayerDraftedOrderComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2) {
		return Integer.valueOf(p1.getRoundDrafted()).compareTo(Integer.valueOf(p2.getRoundDrafted()));
	}

}
