package com.falifa.draftbuddy.ui.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.falifa.draftbuddy.ui.comparator.PlayerADPComparator;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.manager.NFLTeamManager;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class LogicHandler {
	
	private static final Logger log = LoggerFactory.getLogger(LogicHandler.class);
	
	@Autowired
	private DraftState draftState;

	@Autowired
	private NFLTeamManager nflTeams;
	
	@Autowired
	private RandomIndexGenerator randomGenerator;

	
	private Properties props;
	
	public LogicHandler() {
		props = new Properties();
		try {
			props.load(new FileInputStream(new File("src/main/resources/application.properties")));
		} catch (IOException e) {
			log.error("ERROR loading properties from LogicHandler properties files", e);
		}
	}
	
	public List<Player> getSortedSuggestedPlayers(Drafter currentDrafter) {
		return currentDrafter.isOptimized() ? getOptimizedSuggestions(currentDrafter) : getAiSuggestions(currentDrafter);
	}
	
	public List<Player> getOptimizedSuggestions(Drafter currentDrafter) {
		List<Player> suggestions = getPlayersForPositionsThatAreEmptyIfLateInDraft(currentDrafter);
		if (CollectionUtils.isEmpty(suggestions)) {
			suggestions = nflTeams.getAllAvailablePlayersByADP().stream().filter(p -> 
				isAtLeastInitialRoundForPosition(p) &&
				positionSlotIsNotFull(p, currentDrafter) //&& 
//				hasNotFilledEarlyRoundMaxForPositionCount(p, currentDrafter) // if already drafted a qb or te, probably want to wait until later to relook at them
			).collect(Collectors.toList());
			reorderSuggestionsBasedOnTagLogic(suggestions);
		}
		return suggestions;
	}
	
	public Player getAiPick(Drafter currentComputerDrafter) {
		List<Player> suggestedAiList = getAiSuggestions(currentComputerDrafter);
		return draftState.mockDraftMode ? suggestedAiList.get(randomGenerator.generate(draftState.pickNumber, draftState.roundNum)) : suggestedAiList.get(0);
	}
	
	public List<Player> getAiSuggestions(Drafter currentComputerDrafter) {
		List<Player> suggestions = getPlayersForPositionsThatAreEmptyIfLateInDraft(currentComputerDrafter);
		if (CollectionUtils.isEmpty(suggestions)) {
			suggestions = nflTeams.getAllAvailablePlayersByADP().stream().filter(p -> positionSlotIsNotFull(p, currentComputerDrafter)).collect(Collectors.toList());
		}
		return suggestions;
	}
	
	private boolean isAtLeastInitialRoundForPosition(Player p) {
		if (p.getPosition() == null) { return false; }
		try {
			return (draftState.getCurrentRoundNumber() >= prop(p.getPosition().getAbbrev().toLowerCase() + "Init"));
		} catch (Exception e) {
			log.error("ERROR in isAtLeastInitialRoundForPosition for player=" + p.getPlayerName());
			return false;
		}
	}

//	private boolean hasNotFilledEarlyRoundMaxForPositionCount(Player p, Drafter currentDrafter) {
//		if (p.getPosition() == null) { return false; }
//		try {
//			if (p.getPosition().equals(Position.QUARTERBACK) || (p.getPosition().equals(Position.TIGHTEND))) {
//				return (currentDrafter.getDraftedTeam().getPlayersByPosition(p.getPosition()).size() > 0) // has at least 1 player?
//				&&	(draftState.getCurrentRoundNumber() < prop("te_qb_reintroduceRound")); // late enough in the draft for qb and te?
//				
//			}
//		} catch (Exception e) {
//			log.error("ERROR in hasNotFilledEarlyRoundMaxForPositionCount for player=" + p.getPlayerName());
//			return false;
//		}
//	}

	private boolean positionSlotIsNotFull(Player p, Drafter currentDrafter) {
		if (p.getPosition() == null) { return false; }
		try {
			return (currentDrafter.getDraftedTeam().getPlayersByPosition(p.getPosition()).size() < prop(p.getPosition().getAbbrev().toLowerCase() + "Limit"));
		} catch (Exception e) {
			log.error("ERROR in positionSlotIsNotFull for player=" + p.getPlayerName());
			return false;
		}
	}
	
	private List<Player> getPlayersForPositionsThatAreEmptyIfLateInDraft(Drafter currentDrafter) {
		List<Player> positionsThatHaveNotBeenDrafted = new ArrayList<Player>();
		for (Entry<Position, List<Player>> draftedPositionList : currentDrafter.getDraftedTeam().getPlayersByPosition().entrySet()) {
			if ((draftedPositionList.getValue().size() == 0) && (draftState.getCurrentRoundNumber() >= draftState.getNumberOfRounds() - prop(draftedPositionList.getKey().getAbbrev().toLowerCase() + "Warn"))) {
				log.info("addIfPositionIsEmptyAndItIsLateEnoughToMatter " + currentDrafter.getDraftedTeam().getName() + " :: adding position = " + draftedPositionList.getKey().getName());
				positionsThatHaveNotBeenDrafted.addAll(nflTeams.getAvailablePlayersByPositionAsList(draftedPositionList.getKey()));
			}
		}
		if (!positionsThatHaveNotBeenDrafted.isEmpty()) {
			log.info("checkForEmptyPositions : " + currentDrafter.getDraftedTeam().getName() + " has not filled all positions :: replacing suggestions with players from empty positions");
			Collections.sort(positionsThatHaveNotBeenDrafted, new PlayerADPComparator());
		}
		return positionsThatHaveNotBeenDrafted;
	}
	
	
	public List<Integer> getDraftPickIndexList(Drafter currentDrafter) {
		List<Integer> list = new ArrayList<Integer>();
		int nextRoundNum = draftState.roundNum + 1;
		for (int i = nextRoundNum; i <= draftState.getNumberOfRounds(); i++) {
			if (i%2 == 0) { // if round is even
				list.add((i * draftState.draft.getDrafters().size()) - (currentDrafter.getDraftOrderNumber() - 1));
			} else { // if round is odd
				list.add(((i-1) * draftState.draft.getDrafters().size() ) + currentDrafter.getDraftOrderNumber());
			}
		}
		return list;
	}
	
	
	private void reorderSuggestionsBasedOnTagLogic(List<Player> suggestions) {
		ArrayList<Player> copy = new ArrayList<Player>(suggestions);
		for (Player player : copy) {
			try {
				for (Tag tag : Tag.values()) {
					if (player.getDraftingDetails().getTags().contains(tag.getTag())) {
						shiftPlayerByTag(tag, player, suggestions);
					}
				}
			} catch (NullPointerException e){  }
		}
	}
	
	private void shiftPlayerByTag(Tag tag, Player playerReference, List<Player> suggestions) {
		if (tag.getShift() > 0) {
			for (int i = 0; i < tag.getShift(); i++) {
				movePlayerUp(playerReference, suggestions);
			}
		} else if (tag.getShift() < 0) {
			for (int i = tag.getShift(); i < 0; i++) {
				movePlayerDown(playerReference, suggestions);
			}
		}
	}
	
	public void movePlayerUp(Player player, List<Player> suggestions) {
		int current = suggestions.indexOf(player);
		if (current == 0) { return; }
		
		Player other = suggestions.get(current - 1);
		
		if (withinTenPercent(player, other)) {
			suggestions.set(current, other);
			suggestions.set(current - 1, player);
		}
		
	}
	
	public void movePlayerDown(Player player, List<Player> suggestions) {
		int current = suggestions.indexOf(player);
		if (current == suggestions.size() - 1) { return; }
		
		Player other = suggestions.get(current + 1);
		
		if (withinTenPercent(player, other)) {
		suggestions.set(current, other);
		suggestions.set(current + 1, player);
		}
	}

	private boolean withinTenPercent(Player player, Player other) {
		return Math.abs(Integer.parseInt(player.getRankMetadata().getAdp()) - Integer.parseInt(other.getRankMetadata().getAdp())) <= 9;
	}

	private int prop(String prop) {
		return Integer.valueOf(props.getProperty(prop));
	}

}
