package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.DraftPick;

public class DraftSelectionOrderComparator implements Comparator<DraftPick> {

	@Override
	public int compare(DraftPick pick1, DraftPick pick2) {
		return Integer.valueOf(pick2.getPick()).compareTo(Integer.valueOf(pick1.getPick()));
	}

}
