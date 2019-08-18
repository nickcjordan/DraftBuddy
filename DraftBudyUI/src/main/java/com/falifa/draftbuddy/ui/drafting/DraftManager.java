package com.falifa.draftbuddy.ui.drafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.data.ModelUpdater;
import com.falifa.draftbuddy.ui.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.drafting.sort.DraftSelectionOrderComparator;
import com.falifa.draftbuddy.ui.drafting.sort.UserDraftOrderComparator;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.DraftPick;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.Team;
import com.falifa.draftbuddy.ui.model.UndoDraftStateTO;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.results.ResultsProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DraftManager {

	private static final Logger log = LoggerFactory.getLogger(DraftManager.class);

	@Value("${optimizedDrafterName}")
	private String optimizedDrafterName;

	@Autowired
	private DraftState draftState;

	@Autowired
	protected NFLTeamManager nflTeams;

	@Autowired
	private LogicHandler handler;

	@Autowired
	private ModelUpdater modelUpdater;

	private Stack<UndoDraftStateTO> undoStack;
	
	public DraftManager() {
		undoStack = new Stack<UndoDraftStateTO>();
	}
	
	public void clearUndoToStack() {
		undoStack.clear();
	}

	public boolean undoLastDraftPick(Model model) {
		if (undoStack.size() > 0) {
			try {
				UndoDraftStateTO previousState = copyPropertiesFromPreviousState();
				DraftPick previousPick = undoPickedStatuses();
				log.info("UNDO was successful :: undid DraftPick={}.{} :: previous state = [roundNum={}, pickNum={}, currentDrafter={}] current state after undo = [roundNum={}, pickNum={}, currentDrafter={}]", previousPick.getRound(), previousPick.getPick(), 
						previousState.getRoundNum(), previousState.getPickNumber(), previousState.getCurrentDrafter().getName(), draftState.getRoundNum(), draftState.getPickNumber(), draftState.getCurrentDrafter().getName());
				modelUpdater.updateCommonAttributes(model);
				return true;
			} catch (Exception e) {
				log.error("Error setting statuses for UNDO", e);
			}
		} else {
			log.info("Undo stack is empty :: nothing to undo...");
		}
		return false;
	}
	
	private UndoDraftStateTO copyPropertiesFromPreviousState() {
		UndoDraftStateTO undoTO = undoStack.pop();
		draftState.setCurrentDrafter(undoTO.getCurrentDrafter());
		draftState.setDraftOrderIndex(undoTO.getDraftOrderIndex());
		draftState.setMockDraftMode(undoTO.isMockDraftMode());
		draftState.setPickNumber(undoTO.getPickNumber());
		draftState.setRoundNum(undoTO.getRoundNum());
		setDraftStateHandcuffsForCurrentDrafter();
		return undoTO;
	}

	private DraftPick undoPickedStatuses() {
		DraftPick previousPick = draftState.getDraftPicks().remove(0);
		previousPick.getPlayer().getDraftingDetails().setRoundDrafted(0);
		previousPick.getPlayer().getDraftingDetails().setAvailable(true);
		if (!removePickedPlayerFromDraftersTeam(previousPick.getDrafter(), previousPick.getPlayer())) {
			log.error("ERROR player could not be removed from Drafter's team :: player={} :: drafter={}", previousPick.getPlayer(), previousPick.getDrafter());
		}
		nflTeams.setCurrentPlayerValue();
		setDraftStateHandcuffsForCurrentDrafter();
		return previousPick;
	}

	private boolean removePickedPlayerFromDraftersTeam(Drafter drafter, Player player) {
		return drafter.getDraftedTeam().getPlayersByPosition(player.getPosition()).remove(player);
	}

	private UndoDraftStateTO buildUndoTO() {
		UndoDraftStateTO to = new UndoDraftStateTO();
		to.setCurrentDrafter(draftState.getCurrentDrafter());
		to.setDraftOrderIndex(draftState.getDraftOrderIndex());
		to.setMockDraftMode(draftState.isMockDraftMode());
		to.setPickNumber(draftState.getPickNumber());
		to.setRoundNum(draftState.getRoundNum());
		return to;
	}
	
	public void doBaseDraft(Player player, Model model) {
		log.info(String.format("%s.%s :: %-10s --> %s", draftState.getRoundNum(), draftState.getDraftOrderIndex(), draftState.getCurrentDrafter().getName(), player.getPlayerName()));
		draftState.getDraftPicks().add(draftPlayer(draftState.getCurrentDrafter(), player));
		Collections.sort(draftState.getDraftPicks(), new DraftSelectionOrderComparator());
		undoStack.push(buildUndoTO());
		checkIfEndOfRound();
		draftState.moveToNextDrafter();
		nflTeams.setCurrentPlayerValue();
		setDraftStateHandcuffsForCurrentDrafter();
		modelUpdater.updateCommonAttributes(model);
	}

	public String mockDraft(Model model) {
		if (draftState.getCurrentDrafter().isOptimized()) {
			// draftState.getTimeIncrement("right before exiting java");
			return "pages/dashboardPage";
		} else {
			Player player = handler.getAiPick(draftState.getCurrentDrafter());
			doBaseDraft(player, model);
			// log.info("Middle of turn :: elapsed time = {}", draftState.getTime());
			return draftHasCompleted() ? prepareResults(model) : mockDraft(model);
		}
	}

	public String autoDraft(Model model) {
		Player player = (draftState.currentDrafter.isOptimized()) ? handler.getOptimizedSuggestions(draftState.currentDrafter).get(0)
				: handler.getAiPick(draftState.currentDrafter);
		doBaseDraft(player, model);
		return draftHasCompleted() ? prepareResults(model) : autoDraft(model);
	}

	public String resolvePlayerId(String playerId) {
		return !playerId.isEmpty() ? playerId
				: draftState.currentDrafter.isOptimized() ? handler.getOptimizedSuggestions(draftState.currentDrafter).get(0).getFantasyProsId()
						: handler.getAiPick(draftState.currentDrafter).getFantasyProsId();
	}

	public String prepareResults(Model model) {
		ResultsProcessor.processResults(draftState.draft);
		ArrayList<Drafter> orderedDrafters = new ArrayList<Drafter>(draftState.draft.getDrafters());
		Collections.sort(orderedDrafters, new UserDraftOrderComparator());
		model.addAttribute("drafterResults", orderedDrafters);
		return "pages/resultsPage";
	}

	private void setDraftStateHandcuffsForCurrentDrafter() {
		draftState.setCurrentRoundHandcuffs(getHandcuffsForCurrentDrafter());
	}
	
	private List<Player> getHandcuffsForCurrentDrafter() {
		List<Player> players = new ArrayList<Player>();
		for (Player drafted : draftState.getCurrentDrafter().getDraftedTeam().getAllInDraftedOrder()) {
			for (Player handcuff : drafted.getDraftingDetails().getBackups()) {
				players.add(handcuff);
			}
		}
		return players;
	}

	private DraftPick draftPlayer(Drafter drafter, Player draftedPlayer) {
		draftedPlayer.getDraftingDetails().setRoundDrafted(draftState.roundNum);
		addPlayerToDraftersTeam(draftedPlayer, drafter.getDraftedTeam());
		draftedPlayer.getDraftingDetails().setAvailable(false);
		for (Player handcuff : draftedPlayer.getDraftingDetails().getBackups()) {
			handcuff.getDraftingDetails().setHandcuff(true);
		}
		return new DraftPick(draftState.getPickNumber(), draftState.getRoundNum(), drafter, draftedPlayer);
	}

	public boolean draftHasCompleted() {
		return (draftState.pickNumber > (draftState.NUMBER_OF_ROUNDS * draftState.draft.getOrderedNames().length));
	}

	private boolean checkIfEndOfRound() {
		draftState.pickNumber++;
		if (draftState.draftOrderIndex == draftState.draft.getDrafters().size() - 1) {
			draftState.draft.reverseOrder();
			draftState.draftOrderIndex = -1;
			if (draftState.roundNum != draftState.NUMBER_OF_ROUNDS) {
				draftState.roundNum++;
			}
			return true;
		} else {
			return false;
		}
	}

	public void addPlayerToDraftersTeam(Player player, Team team) {
		team.getPlayersByPosition(player.getPosition()).add(player);
	}

	public void setOptimizedDrafter(Draft draft) {
		for (Drafter drafter : draft.getDrafters()) {
			if (drafter.getName().equals(optimizedDrafterName)) {
				drafter.setOptimized(true);
			}
		}
	}

}
