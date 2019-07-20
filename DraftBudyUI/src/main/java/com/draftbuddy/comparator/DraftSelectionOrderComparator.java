package com.draftbuddy.comparator;

import java.util.Comparator;

import com.draftbuddy.model.DraftPick;

public class DraftSelectionOrderComparator implements Comparator<DraftPick> {

	@Override
	public int compare(DraftPick pick1, DraftPick pick2) {
		return Integer.valueOf(pick2.getPick()).compareTo(Integer.valueOf(pick1.getPick()));
	}

}
