package com.falifa.draftbuddy.ui.data;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.DRAFTSTRATEGY_CUSTOM_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.DRAFT_LOGIC_PROPERTIES_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.io.DataFileReader;
import com.falifa.draftbuddy.ui.logic.LogicHandler;
import com.falifa.draftbuddy.ui.manager.NFLTeamManager;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.DraftPick;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class DraftState {
	
	
	private static final Logger log = LoggerFactory.getLogger(DraftState.class);

	
	public int pickNumber = 1;
	public int roundNum = 0;
	public int NUMBER_OF_ROUNDS;
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
	
	public DraftState() {
		initializeDraft();
	}
	
	public void initializeDraft() {
    	strategyByRound = getStrategyFromFile();
    	errorMessage = null;
    	NUMBER_OF_ROUNDS = Integer.valueOf(System.getProperty("numberOfRounds"));
    	draftPicks = new ArrayList<>();
    	roundNum = 1;
    	pickNumber = 1;
    	draftOrderIndex = 0;
//    	StatsCleaner.cleanupTags();
//    	StatsCleaner.cleanupNickNotes();
    	
    	System.out.println("\n\n<^>     Ready to Draft     <^>\n\n");
	}

	protected Map<String, RoundSpecificStrategy> getStrategyFromFile() {
		Map<String, RoundSpecificStrategy> map = new HashMap<String, RoundSpecificStrategy>();
//		try {
//			for (List<String> split : new DataFileReader().getSplitLinesFromFile(DRAFTSTRATEGY_CUSTOM_PATH, true, ",")) {
//				RoundSpecificStrategy strategy = buildStrategy(split);
//				map.put(strategy.getRound(), strategy);
//			}
//		} catch (FileNotFoundException e) {
//			log.error("ERROR :: could not find strategies file: " + e.getMessage());
//		}
		return map;
	}
	

	public List<Drafter> getCorrectlyOrderedDrafterList() {
		List<Drafter> drafterList = new ArrayList<Drafter>(draft.getDrafters());
      	if (!draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
      		Collections.reverse(drafterList);
      	}
		return drafterList;
	}

	private RoundSpecificStrategy buildStrategy(List<String> split) {
		RoundSpecificStrategy strategy = new RoundSpecificStrategy();
		strategy.setRound(split.get(0));
		strategy.setStrategyText(split.get(1));
		strategy.setTargetPositions(split.get(2));
		strategy.setTargetPlayers(buildListOfTargetPlayers(split));
		return strategy;
	}

	private List<String> buildListOfTargetPlayers(List<String> split) {
		List<String> players = new ArrayList<String>();
		for (int i = 3; i < split.size(); i++) {
			players.add(split.get(i));
		}
		return players;
	}

	public double getPercent() {
		double percent = 0;
		if (pickNumber == 1) {
			return 0;
		}
//		percent = (((pickNumber - 1) / (NUMBER_OF_ROUNDS * draft.getDrafters().size())) * 100);
		double x = (NUMBER_OF_ROUNDS * draft.getDrafters().size());
		double y = ((pickNumber - 1) / x);
		double perc = (y * 100);
		percent = (double)Math.round(percent * 100d) / 100d;
		if (percent < 1) {
			return 1;
		}
		return percent;
	}
	
//	public List<Player> getSuggestedAvailablePlayers(Drafter drafter) {
//		return new LogicHandler(drafter).getMySuggestions();
//	}
    
	public int getPickNumber() {
		return pickNumber;
	}

	public int getCurrentRoundNumber() {
		return roundNum;
	}
	
	public void moveToNextDrafter() {
		currentDrafter = draft.getDrafters().get(++draftOrderIndex);
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

//	public List<Player> getCurrentRoundHandcuffs() {
//		return currentRoundHandcuffs;
//	}
//
//	public void setCurrentRoundHandcuffs(List<Player> currentRoundHandcuffs) {
//		this.currentRoundHandcuffs = currentRoundHandcuffs;
//	}

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
