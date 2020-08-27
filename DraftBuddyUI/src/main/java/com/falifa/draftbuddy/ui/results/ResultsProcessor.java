package com.falifa.draftbuddy.ui.results;

import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.Drafter;

public class ResultsProcessor {

	public static void processResults(Draft draft) {
		for (Drafter drafter : draft.getDrafters()) {
			drafter.setDraftResultStats(buildDraftResultStats(drafter));
		}
	}

	private static DraftResultStatistics buildDraftResultStats(Drafter drafter) {
		DraftResultStatistics stats = new DraftResultStatistics(drafter);
		
		
		
		
		return stats;
	}
}
