package com.falifa.draftbuddy.ui.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.DraftPick;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class DraftState {
	
	@Value( "${numberOfRounds}" )
	public int NUMBER_OF_ROUNDS;
	
	public int pickNumber = 1;
	public int roundNum = 0;
	public int draftOrderIndex = 0;
	
	public boolean mockDraftMode;
	
	public Drafter currentDrafter;
	public Draft draft;
	public DraftType draftType;
	public List<DraftPick> draftPicks;
	public String errorMessage = null;
	
	public Map<String, RoundSpecificStrategy> strategyByRound;
	public List<Player> currentRoundHandcuffs;
	
	@Autowired
	protected NFLTeamManager nflTeams;
	
	public DraftState() {}
	
	public void initializeDraft() {
    	errorMessage = null;
    	draftPicks = new ArrayList<DraftPick>();
    	roundNum = 1;
    	pickNumber = 1;
    	draftOrderIndex = 0;
    	System.out.println("\n\n<^>     Ready to Draft     <^>\n\n");
	}

	public List<Drafter> getCorrectlyOrderedDrafterList() {
		List<Drafter> drafterList = new ArrayList<Drafter>(draft.getDrafters());
      	if (!draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
      		Collections.reverse(drafterList);
      	}
		return drafterList;
	}

	public double getPercent() {
		double percent = 0;
		if (pickNumber == 1) {
			return 0;
		}
		percent = (((pickNumber - 1) / (NUMBER_OF_ROUNDS * draft.getDrafters().size())) * 100);
		double x = (NUMBER_OF_ROUNDS * draft.getDrafters().size());
		double y = ((pickNumber - 1) / x);
		double perc = (y * 100);
		percent = (double)Math.round(percent * 100d) / 100d;
		if (percent < 1) {
			return 1;
		}
		return percent;
	}
	
	public int getPickNumber() {
		return pickNumber;
	}

	public int getCurrentRoundNumber() {
		return roundNum;
	}
	
	public Drafter moveToNextDrafter() {
		currentDrafter = draft.getDrafters().get(++draftOrderIndex);
		return currentDrafter;
	}

	public Drafter getCurrentDrafter() {
		return currentDrafter;
	}

	public boolean isMockDraftMode() {
		return mockDraftMode;
	}

	public int getRoundNum() {
		return roundNum;
	}

	public int getNumberOfRounds() {
		return NUMBER_OF_ROUNDS;
	}

	public int getDraftOrderIndex() {
		return draftOrderIndex;
	}

	public Draft getDraft() {
		return draft;
	}

	public DraftType getDraftType() {
		return draftType;
	}
	
	public boolean inMockDraftMode(Drafter drafter) {
		return (mockDraftMode && !drafter.getName().equals("Nick J"));
	}
	
	public List<DraftPick> getDraftPicks() {
		return draftPicks;
	}

	public void setDraftPicks(List<DraftPick> draftPicks) {
		this.draftPicks = draftPicks;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<String, RoundSpecificStrategy> getStrategyByRound() {
		return strategyByRound;
	}

	public void setStrategyByRound(Map<String, RoundSpecificStrategy> strategyByRound) {
		this.strategyByRound = strategyByRound;
	}

	public NFLTeamManager getNflTeams() {
		return nflTeams;
	}

	public void setNflTeams(NFLTeamManager nflTeams) {
		this.nflTeams = nflTeams;
	}

	public void setPickNumber(int pickNumber) {
		this.pickNumber = pickNumber;
	}

	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	public void setDraftOrderIndex(int draftOrderIndex) {
		this.draftOrderIndex = draftOrderIndex;
	}

	public void setCurrentDrafter(Drafter currentDrafter) {
		this.currentDrafter = currentDrafter;
	}

	public void setMockDraftMode(boolean mockDraftMode) {
		this.mockDraftMode = mockDraftMode;
	}

	public void setDraft(Draft draft) {
		this.draft = draft;
	}

	public void setDraftType(DraftType draftType) {
		this.draftType = draftType;
	}

	
	

}
