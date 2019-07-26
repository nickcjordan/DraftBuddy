package com.falifa.draftbuddy.ui.comparator;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class PlayerRankComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2){
		return Integer.valueOf(p1.getRankMetadata().getAvgRank()).compareTo(Integer.valueOf(p2.getRankMetadata().getAvgRank()));
	}
}
