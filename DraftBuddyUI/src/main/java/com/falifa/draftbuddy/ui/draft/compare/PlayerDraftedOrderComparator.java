package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class PlayerDraftedOrderComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2) {
		return Integer.valueOf(p1.getDraftingDetails().getRoundDrafted()).compareTo(Integer.valueOf(p2.getDraftingDetails().getRoundDrafted()));
	}

}
