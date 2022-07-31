package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;

public class FFBallerFlexGradeComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2) {
		return Integer.valueOf(p2.getFfBallersPlayerProjection().getFlexGrade()).compareTo(Integer.valueOf(p1.getFfBallersPlayerProjection().getFlexGrade()));
	}

}
