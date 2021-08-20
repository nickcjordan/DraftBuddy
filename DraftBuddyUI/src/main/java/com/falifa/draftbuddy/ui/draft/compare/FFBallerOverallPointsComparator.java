package com.falifa.draftbuddy.ui.draft.compare;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersConsolidatedProjection;

public class FFBallerOverallPointsComparator implements Comparator<FFBallersConsolidatedProjection> {

	public int compare(FFBallersConsolidatedProjection p1, FFBallersConsolidatedProjection p2) {
		return Integer.valueOf(p2.getOverallPoints()).compareTo(Integer.valueOf(p1.getOverallPoints()));
	}

}
