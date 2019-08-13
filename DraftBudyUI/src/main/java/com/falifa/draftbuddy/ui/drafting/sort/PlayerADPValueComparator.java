package com.falifa.draftbuddy.ui.drafting.sort;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class PlayerADPValueComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2){
		return Integer.valueOf(p1.getDraftingDetails().getCurrentPlayerValue()).compareTo(Integer.valueOf(p2.getDraftingDetails().getCurrentPlayerValue()));
	}

}
