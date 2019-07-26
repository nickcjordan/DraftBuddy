package com.falifa.draftbuddy.ui.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.logic.LogicHandler;
import com.falifa.draftbuddy.ui.manager.NFLTeamManager;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.NFLTeam;

@Component
public class ModelUpdater {
	
	@Autowired
	private DraftState draftState;
	
	@Autowired
	private NFLTeamManager nflTeams;
	
	@Autowired
	private LogicHandler handler;

	public void updateCommonAttributes(Drafter currentDrafter, Model model) {
		updateCurrentDrafterAttributes(currentDrafter, model);
		updateDraftStateAttributes(model);
      	updateNflTeamListsAttributes(model);
	}

	public void updateCurrentDrafterAttributes(Drafter currentDrafter, Model model) {
		model.addAttribute("currentDrafter", currentDrafter);
		model.addAttribute("currentDraftedTeam", currentDrafter.getDraftedTeam());
		model.addAttribute("draftersPickNumberList", handler.getDraftPickIndexList(currentDrafter));
		model.addAttribute("playersSortedBySuggestions", currentDrafter.getName().equals("Nick J") ? handler.getMySuggestions(currentDrafter) : handler.getAiPick(currentDrafter));
		for (Position position : Position.values()) {
			model.addAttribute("drafted" + position.getAbbrev(), draftState.currentDrafter.getDraftedTeam().getPlayersByPosition(position)); 
		}
	}

	public void updateNflTeamListsAttributes(Model model) {
		model.addAttribute("playersSortedByAdp", nflTeams.getAllAvailablePlayersByADP());
      	model.addAttribute("playersSortedByRank", nflTeams.getAllAvailablePlayersByRank());
      	model.addAttribute("allPlayersList", nflTeams.getAllAvailablePlayersByADP());
      	model.addAttribute("playerList", nflTeams.getAllAvailablePlayersByADP());
        for (Position position : Position.values()) {
        	model.addAttribute(position.getAbbrev() + "List", nflTeams.getAvailablePlayersByPositionAsList(position));
        }
	}

	public void updateDraftStateAttributes(Model model) {
		model.addAttribute("progressPercent", draftState.getPercent());
		model.addAttribute("draft", draftState);
		model.addAttribute("draftType", draftState.draftType);
        model.addAttribute("currentRoundHandcuffs", draftState.currentRoundHandcuffs);
        model.addAttribute("roundNumber", ((draftState.roundNum < draftState.NUMBER_OF_ROUNDS) ? draftState.roundNum : draftState.NUMBER_OF_ROUNDS));
        model.addAttribute("pickNumber", draftState.pickNumber);
        model.addAttribute("draftPicks", draftState.draftPicks);
      	model.addAttribute("drafters", draftState.getCorrectlyOrderedDrafterList());
      	model.addAttribute("strategy", draftState.strategyByRound.get(String.valueOf(draftState.roundNum)));
	}
	
	
	private void addCommonModelAttributes(Model model) {
    	model.addAttribute("roundNumber", draftState.roundNum);
        model.addAttribute("pickNumber", draftState.pickNumber);
        model.addAttribute("progressPercent", draftState.getPercent());
        model.addAttribute("currentRoundHandcuffs", draftState.currentRoundHandcuffs);
        model.addAttribute("allPlayersList", nflTeams.getAllAvailablePlayersByADP());
    }

	public void updateModelForPositionPage(String pos, Model model) {
		if (pos.equals("all")) {
	    	model.addAttribute("playerList", nflTeams.getAllAvailablePlayersByADP());
	    	model.addAttribute("positionName", "All Available Players");
		} else {
	    	model.addAttribute("playerList", nflTeams.getAvailablePlayersByPositionAsList(Position.get(pos)));
	    	model.addAttribute("positionName", Position.get(pos).getName());
		}
        addCommonModelAttributes(model);
	}

	public void updateModelForTeamPage(String teamId, Model model) {
		NFLTeam team = nflTeams.getTeam(teamId.replace("'", ""));
		model.addAttribute("team", team);
		model.addAttribute("teamName", team.getTeam().getFullName());
		model.addAttribute("allTeams", nflTeams.getTeamsByAbbreviation());
        addCommonModelAttributes(model);
	}

	public void updateModelForDrafterPage(Drafter drafter, Model model) {
		model.addAttribute("team", drafter.getDraftedTeam());
		model.addAttribute("teamName", drafter.getName());
		model.addAttribute("drafters", draftState.draft.getDrafters());
        addCommonModelAttributes(model);
	}

	public void updateModelForDraftBoardPage(Model model) {
		List<Drafter> drafterList = new ArrayList<Drafter>(draftState.draft.getDrafters());
    	if (!draftState.draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
    		Collections.reverse(drafterList);
    	}
    	model.addAttribute("drafters", drafterList);
    	model.addAttribute("draft", draftState.draft);
    	model.addAttribute("roundNumber", draftState.roundNum);
    	model.addAttribute("pickNumber", draftState.pickNumber);
    	addCommonModelAttributes(model);
	}
	
}
