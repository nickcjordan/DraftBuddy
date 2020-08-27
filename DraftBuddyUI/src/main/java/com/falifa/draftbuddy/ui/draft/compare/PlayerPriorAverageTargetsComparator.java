package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class PlayerPriorAverageTargetsComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2){
		return Double.valueOf(p1.getPositionalStats().getPriorAverageTargetsPerGame()).compareTo(Double.valueOf(p2.getPositionalStats().getPriorAverageTargetsPerGame()));
	}

}
