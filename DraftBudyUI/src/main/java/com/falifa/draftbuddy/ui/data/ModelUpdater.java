package com.falifa.draftbuddy.ui.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.drafting.LogicHandler;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class ModelUpdater {
	
	
	private static final Logger log = LoggerFactory.getLogger(ModelUpdater.class);

	
	@Autowired
	private DraftState draftState;
	
	@Autowired
	private NFLTeamManager nflTeams;
	
	@Autowired
	private LogicHandler handler;

	public void updateCommonAttributes(Model model) {
		updateCurrentDrafterAttributes(model);
		updateDraftStateAttributes(model);
      	updateNflTeamListsAttributes(model);
      	log.debug("Models updated for all fields :: updated for drafter={}", draftState.getCurrentDrafter().getName());
      	printModel(model);
	}
	
	private void updateCommonAttributesSubset(Model model) {
		model.addAttribute("error", draftState.errorMessage);
		updateDraftStateAttributes(model);
		log.debug("Models updated for common subset");
		printModel(model);
	}

	public void updateCurrentDrafterAttributes(Model model) {
		model.addAttribute("currentDrafter", draftState.getCurrentDrafter());
		model.addAttribute("playersSortedBySuggestions", handler.getSortedSuggestedPlayers(draftState.getCurrentDrafter()));
		for (Position position : Position.values()) {
			model.addAttribute("drafted" + position.getAbbrev(), draftState.currentDrafter.getDraftedTeam().getPlayersByPosition(position)); 
		}
		log.debug("Models updated for drafter={}", draftState.getCurrentDrafter().getName());
	}

	public void updateNflTeamListsAttributes(Model model) {
		model.addAttribute("playersToBuildModalFor", nflTeams.getAllPlayersByADP());
		model.addAttribute("playersSortedByAdp", nflTeams.getAllAvailablePlayersByADP());
      	model.addAttribute("playersSortedByRank", nflTeams.getAllAvailablePlayersByRank());
        log.debug("Models updated for team list attributes");
	}

	public void updateDraftStateAttributes(Model model) {
		model.addAttribute("progressPercent", draftState.getPercent());
		model.addAttribute("draft", draftState);
        model.addAttribute("roundNumber", Math.min(draftState.roundNum, draftState.NUMBER_OF_ROUNDS));
      	model.addAttribute("drafters", draftState.getCorrectlyOrderedDrafterList());
      	model.addAttribute("strategy", draftState.getStrategyByRound().get(String.valueOf(draftState.roundNum)));
      	log.debug("Models updated for draft state attributes");
	}
	

	public void updateModelForPositionPage(String pos, Model model) {
		if (pos.equals("all")) {
	    	model.addAttribute("playerList", nflTeams.getAllAvailablePlayersByADP());
	    	model.addAttribute("playersToBuildModalFor", nflTeams.getAllAvailablePlayersByADP());
	    	model.addAttribute("positionName", "All Available Players");
		} else {
	    	model.addAttribute("playerList", nflTeams.getAvailablePlayersByPositionAsList(Position.get(pos)));
	    	model.addAttribute("playersToBuildModalFor", nflTeams.getAvailablePlayersByPositionAsList(Position.get(pos)));
	    	model.addAttribute("positionName", Position.get(pos).getName());
		}
        updateCommonAttributesSubset(model);
        log.debug("Models updated for position page");
	}

	public void updateModelForTeamPage(String teamAbbrev, Model model) {
		NFLTeam team = nflTeams.getTeam(teamAbbrev);
		model.addAttribute("team", team);
		model.addAttribute("teamName", team.getTeam().getFullName());
		model.addAttribute("playersToBuildModalFor", team.getPlayers());
		model.addAttribute("allTeams", nflTeams.getNflTeamsSortedByAbbreviation());
        updateCommonAttributesSubset(model);
        log.debug("Models updated for team page");
	}

	public void updateModelForDrafterPage(Model model) {
//		model.addAttribute("team", drafter.getDraftedTeam());
//		model.addAttribute("teamName", drafter.getName());
		model.addAttribute("drafters", draftState.draft.getDrafters());
		model.addAttribute("playersToBuildModalFor", nflTeams.getAllUnavailablePlayersByADP());
        updateCommonAttributesSubset(model);
        log.debug("Models updated for drafter page");
	}

	public void updateModelForDraftBoardPage(Model model) {
		List<Drafter> drafterList = new ArrayList<Drafter>(draftState.draft.getDrafters());
		model.addAttribute("playersToBuildModalFor", nflTeams.getAllUnavailablePlayersByADP());
    	if (!draftState.draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
    		Collections.reverse(drafterList);
    	}
    	model.addAttribute("drafters", drafterList);
    	model.addAttribute("draft", draftState);
    	updateCommonAttributesSubset(model);
    	log.debug("Models updated for draftboard page");
	}
	
	private void printModel(Model model) {
//		System.out.println("\n\nMODEL:\n");
//		for (String name : model.asMap().keySet()) {
//			System.out.println("\t" + name);
//		}
		
//		ObjectWriter writer = new ObjectMapper().writer();
//		for (Entry<String, Object> entry : model.asMap().entrySet()) {
//			try {
//				System.out.println("\n\n\t" + entry.getKey() + ":\n" + writer.writeValueAsString(entry.getValue()) + "\n\n");
//			} catch (JsonProcessingException e) {
//				e.printStackTrace(); // TODO
//			}
//		}
	}
	
}
