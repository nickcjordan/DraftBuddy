package com.falifa.draftbuddy.ui.draft.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.model.DraftType;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftState;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.DraftPick;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.PositionalOverview;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
public class DraftState {
	
	private static final Logger log = LoggerFactory.getLogger(DraftState.class);
	
	@JsonIgnore
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

	public SleeperDraftState sleeperState;
	public Map<String, PositionalOverview> initialOverviews;
	public String draftId;
	
	public void initializeDraft() {
    	errorMessage = null;
    	draftPicks = new ArrayList<DraftPick>();
    	currentRoundHandcuffs = new ArrayList<Player>();
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

	public String getPercent() {  // TODO
		double percent = 0;
		if (pickNumber == 1) {
			return "0";
		}
		double totalPicks = (NUMBER_OF_ROUNDS * draft.getDrafters().size());
		double div = ((pickNumber - 1) / totalPicks);
		percent = (div * 100);
		if (percent < 1) {
			return "1";
		}
		return String.valueOf(Math.ceil(percent));
	}
	
	public void initializeDraftersDraftPickIndexList() {
		for (Drafter drafter : draft.getDrafters()) {
			List<Integer> draftPickIndicies = new ArrayList<Integer>();
			for (int roundNumber = 1; roundNumber <= NUMBER_OF_ROUNDS; roundNumber++) {
				int numberOfDrafters = getDraft().getDrafters().size();
				if (roundNumber%2 == 0) { // if round is even
					draftPickIndicies.add((roundNumber * numberOfDrafters) - (drafter.getDraftOrderNumber()-1)); // start from the end of the round and subtract pick index for current round
				} else { // if round is odd
					draftPickIndicies.add(((roundNumber-1) * numberOfDrafters) + drafter.getDraftOrderNumber()); // start from beginning of round and add pick index for current round
				}
			}
			drafter.setDraftPickIndices(draftPickIndicies);
		}
	}
	
	
	public int getPickNumber() {
		return pickNumber;
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

	public List<Player> getCurrentRoundHandcuffs() {
		return currentRoundHandcuffs;
	}

	public void setCurrentRoundHandcuffs(List<Player> currentRoundHandcuffs) {
		this.currentRoundHandcuffs = currentRoundHandcuffs;
	}
	
	public boolean isSleeper() {
		return this.sleeperState != null;
	}

	
	
	// can be used to help monitor performance
//	@JsonIgnore public void startTimer() { timer.start(); timeIncrementStart = 0; }
//	@JsonIgnore public long getTime() { 
//		return timer.getTime();
//	}
//	@JsonIgnore public long getTimeIncrement() { 
//		long current = getTime();
//		long res = current - timeIncrementStart;
//		timeIncrementStart = current;
//		log.info("Time increment = {}", res);
//		return res;
//	}
//	@JsonIgnore public long getTimeIncrement(String text) { 
//		long current = getTime();
//		long res = current - timeIncrementStart;
//		timeIncrementStart = current;
//		log.info("Time increment = {} :: {}", res, text);
//		return res;
//	}
//	@JsonIgnore public void stopTimer() { 
//		log.info("Time increment = {}", getTimeIncrement());
//		timer.stop(); 
//		log.info("Final elapsed time = {}", getTime());
//		timer.reset(); 
//	}

}
