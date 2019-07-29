package com.falifa.draftbuddy.ui.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.comparator.DraftSelectionOrderComparator;
import com.falifa.draftbuddy.ui.comparator.UserDraftOrderComparator;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.data.ModelUpdater;
import com.falifa.draftbuddy.ui.exception.FalifaException;
import com.falifa.draftbuddy.ui.logic.LogicHandler;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.DraftPick;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.Team;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.results.ResultsProcessor;

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
	
	public String mockDraft(Drafter currentDrafter, Model model) throws FalifaException {
    	if (currentDrafter.getName().equals(optimizedDrafterName)) { return "pages/dashboardPage"; }
		Player player = handler.getAiPick(draftState.currentDrafter);
		doBaseDraft(player, currentDrafter, model);
    	return draftHasCompleted() ? prepareResults(model) : mockDraft(currentDrafter, model);
    }
    
    public String autoDraft(Drafter currentDrafter, Model model) throws FalifaException {
    	Player player = (draftState.currentDrafter.getName().equals(optimizedDrafterName)) ? handler.getOptimizedSuggestions(draftState.currentDrafter).get(0) : handler.getAiPick(draftState.currentDrafter);
    	doBaseDraft(player, currentDrafter, model);
    	return draftHasCompleted() ? prepareResults(model) : autoDraft(currentDrafter, model);
    }
    
	public String resolvePlayerId(String playerId) {
		return !playerId.isEmpty() ? playerId : draftState.currentDrafter.getName().equals(optimizedDrafterName) ? handler.getOptimizedSuggestions(draftState.currentDrafter).get(0).getFantasyProsId() : handler.getAiPick(draftState.currentDrafter).getFantasyProsId();
	}

    public String prepareResults(Model model) {
    	ResultsProcessor.processResults(draftState.draft);
    	ArrayList<Drafter> orderedDrafters = new ArrayList<Drafter>(draftState.draft.getDrafters());
    	Collections.sort(orderedDrafters, new UserDraftOrderComparator());
    	model.addAttribute("drafterResults", orderedDrafters);
    	return "pages/resultsPage";
    }
    
	public void doBaseDraft(Player player, Drafter currentDrafter, Model model) {
		log.info("Player picked = " + player.getPlayerName());
		draftState.draftPicks.add(draftPlayer(draftState.currentDrafter, player));
		Collections.sort(draftState.draftPicks, new DraftSelectionOrderComparator());
		checkIfEndOfRound();
		draftState.moveToNextDrafter();
		nflTeams.setCurrentPlayerValue();
		setCorrectHandcuffsForCurrentDrafter(currentDrafter);
		modelUpdater.updateCommonAttributes(currentDrafter, model);
	}
    
	private void setCorrectHandcuffsForCurrentDrafter(Drafter currentDrafter) {
		List<Player> players = new ArrayList<Player>();
		for (Player drafted : currentDrafter.getDraftedTeam().getAllInDraftedOrder()) {
			for (Player handcuff : drafted.getDraftingDetails().getBackups()) {
				players.add(handcuff);
			}
		}
		draftState.currentRoundHandcuffs = players;
	}

//	public List<Drafter> getCorrectlyOrderedDrafterList() {
//		List<Drafter> drafterList = new ArrayList<Drafter>(draftState.draft.getDrafters());
//      	if (!draftState.draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
//      		Collections.reverse(drafterList);
//      	}
//		return drafterList;
//	}
    
	private DraftPick draftPlayer(Drafter drafter, Player draftedPlayer) {
		draftedPlayer.getDraftingDetails().setRoundDrafted(draftState.roundNum);
		addPlayerToDraftersTeam(draftedPlayer, drafter.getDraftedTeam());
		draftedPlayer.getDraftingDetails().setAvailable(false);
		for (Player handcuff : draftedPlayer.getDraftingDetails().getBackups()) {
			handcuff.getDraftingDetails().setHandcuff(true);
		}
		return new DraftPick(draftState.pickNumber, draftState.roundNum, drafter, draftedPlayer);
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
		String pos = player.getPosition().getAbbrev();

		if (pos.equals("QB")){
			team.getQb().add(player);
		} if (pos.equals("RB")){
			team.getRb().add(player);
		} if (pos.equals("WR")){
			team.getWr().add(player);
		} if (pos.equals("TE")){
			team.getTe().add(player);
		} if (pos.equals("K")){
			team.getK().add(player);
		} if (pos.equals("DST")){
			team.getD().add(player);
		}
}

	public void setOptimizedDrafter(Draft draft) {
		 for (Drafter drafter : draft.getDrafters()) {
			 if (drafter.getName().equals(optimizedDrafterName)) {
				 drafter.setOptimized(true);
			 }
		 }
	}

}
