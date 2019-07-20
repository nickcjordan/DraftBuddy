package com.falifa.draftbuddy.ui.comparator;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.Drafter;

public class UserDraftOrderComparator implements Comparator<Drafter> {

	public int compare(Drafter p1, Drafter p2) {
		return Integer.valueOf(p1.getDraftOrderNumber()).compareTo(Integer.valueOf(p2.getDraftOrderNumber()));
	}

}
