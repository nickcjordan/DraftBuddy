package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class JJRankComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2) {
		if (p2.getLateRoundRank() != null && p1.getLateRoundRank() != null) {
			return Integer.valueOf(p1.getLateRoundRank().getOverall()).compareTo(Integer.valueOf(p2.getLateRoundRank().getOverall()));
		} else {
			return 0;
		}
	}

}
