package com.falifa.draftbuddy.api.sleeper.model;

import java.util.List;

public class SleeperDraftState {

	private List<SleeperUser> drafters;
	private SleeperDraft draft;

	public SleeperDraft getDraft() {
		return draft;
	}

	public void setDraft(SleeperDraft draft) {
		this.draft = draft;
	}

	public List<SleeperUser> getDrafters() {
		return drafters;
	}

	public void setDrafters(List<SleeperUser> drafters) {
		this.drafters = drafters;
	}

}
