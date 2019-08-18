package com.falifa.draftbuddy.ui.model;

import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.model.player.Player;

public class UndoDraftStateTO {
	
	private int pickNumber;
	private int roundNum;
	private int draftOrderIndex;
	private boolean mockDraftMode;
	private Drafter currentDrafter;
	
	public int getPickNumber() {
		return pickNumber;
	}
	public void setPickNumber(int pickNumber) {
		this.pickNumber = pickNumber;
	}
	public int getRoundNum() {
		return roundNum;
	}
	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}
	public int getDraftOrderIndex() {
		return draftOrderIndex;
	}
	public void setDraftOrderIndex(int draftOrderIndex) {
		this.draftOrderIndex = draftOrderIndex;
	}
	public boolean isMockDraftMode() {
		return mockDraftMode;
	}
	public void setMockDraftMode(boolean mockDraftMode) {
		this.mockDraftMode = mockDraftMode;
	}
	public Drafter getCurrentDrafter() {
		return currentDrafter;
	}
	public void setCurrentDrafter(Drafter currentDrafter) {
		this.currentDrafter = currentDrafter;
	}
	
}
