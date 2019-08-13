package com.falifa.draftbuddy.ui.drafting.sort;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class PlayerAveragePriorPointsComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2){
		return Double.valueOf(p1.getPositionalStats().getPriorAveragePointsPerGame()).compareTo(Double.valueOf(p2.getPositionalStats().getPriorAveragePointsPerGame()));
	}

}