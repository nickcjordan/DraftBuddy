package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class PlayerSleeperADPComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2){
		String adp1 = p1.getRankMetadata().getSleeperAdp();
		String adp2 = p2.getRankMetadata().getSleeperAdp();
		return Integer.valueOf(adp1 == null ? p1.getRankMetadata().getAdp() : adp1).compareTo(Integer.valueOf(adp2 == null ? p2.getRankMetadata().getAdp() : adp2));
	}

}
