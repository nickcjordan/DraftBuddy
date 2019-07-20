package com.draftbuddy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.draftbuddy.Log;
import com.draftbuddy.constants.Position;
import com.draftbuddy.exception.FalifaException;
import com.draftbuddy.logic.LogicHandler;
import com.draftbuddy.model.Drafter;
import com.draftbuddy.model.NFL;
import com.draftbuddy.model.Team;

@Controller
public class PageController extends BaseController {

	
    @RequestMapping(value = "/pos")
    public String positionPage(@RequestParam(required=false, defaultValue="all") String pos, Model model) throws FalifaException {
    	if (pos.equals("all")) {
	    	model.addAttribute("playerList", NFL.getAllAvailablePlayersByADP());
	    	model.addAttribute("positionName", "All Available Players");
		} else {
	    	model.addAttribute("playerList", NFL.getAvailablePlayersByPositionAsList(Position.get(pos)));
	    	model.addAttribute("positionName", Position.get(pos).getName());
		}
        addCommonModelAttributes(model);
        return "pages/positionPage";
    }
    
    @RequestMapping(value = "/nflTeams")
    public String teamPage(@RequestParam(required=false, defaultValue="1") String teamId, Model model) throws FalifaException {
		Team team = NFL.getTeam(Integer.parseInt(teamId.replace("'", "")));
		model.addAttribute("team", team);
		model.addAttribute("teamName", team.getFullName());
		model.addAttribute("allTeams", NFL.getTeamList());
        addCommonModelAttributes(model);
        return "pages/teamPage";
    }
    
    @RequestMapping(value = "/drafters")
    public String drafterPage(@RequestParam(required=false, defaultValue="") String drafterName, Model model) throws FalifaException {
		Drafter drafter = draft.getDrafters().get(0);
    	for (Drafter d : draft.getDrafters()) {
    		if (d.getName().equals(drafterName)) {
    			drafter = d;
    		}
    	}
		model.addAttribute("team", drafter.getDraftedTeam());
		model.addAttribute("teamName", drafter.getName());
		model.addAttribute("drafters", draft.getDrafters());
        addCommonModelAttributes(model);
        return "pages/drafterPage";
    }
    
    @RequestMapping(value = "/dashboard")
	public String dashboard(Model model) {
    	errorMessage = null;
    	model.addAttribute("draft", draft);
		model.addAttribute("currentDrafter", currentDrafter);
		model.addAttribute("suggestions", getSuggestedAvailablePlayers(currentDrafter));
		model.addAttribute("draftPicks", draftPicks);
		model.addAttribute("playerList", NFL.getAllAvailablePlayersByADP());
        model.addAttribute("currentDraftedTeam", currentDrafter.getDraftedTeam());
        
        model.addAttribute("playersSortedBySuggestions", getSuggestedAvailablePlayers(currentDrafter));
		model.addAttribute("playersSortedByAdp", NFL.getAllAvailablePlayersByADP());
		model.addAttribute("playersSortedByRank", NFL.getAllAvailablePlayersByRank());
		
		for (Position position : Position.values()) {
			model.addAttribute("drafted" + position.getAbbrev(), currentDrafter.getDraftedTeam().getPlayersByPosition(position)); 
			model.addAttribute(position.getAbbrev() + "List", NFL.getAvailablePlayersByPositionAsList(position));
		}
    	model.addAttribute("drafters", DraftController.getCorrectlyOrderedDrafterList());
      	model.addAttribute("strategy", strategyByRound.get(String.valueOf(roundNum)));
      	model.addAttribute("draftersPickNumberList", new LogicHandler(currentDrafter).getDraftPickIndexList());
      	addCommonModelAttributes(model);
		return "pages/dashboardPage";
    }
    
    @RequestMapping(value = "/draftBoard")
	public String draftBoard(Model model) {
    	List<Drafter> drafterList = new ArrayList<Drafter>(draft.getDrafters());
    	if (!draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
    		Collections.reverse(drafterList);
    	}
    	model.addAttribute("drafters", drafterList);
    	model.addAttribute("draft", draft);
    	model.addAttribute("roundNumber", roundNum);
    	model.addAttribute("pickNumber", pickNumber);
    	addCommonModelAttributes(model);
    	return "pages/draftBoardPage";
    }
    
    private void addCommonModelAttributes(Model model) {
    	model.addAttribute("roundNumber", roundNum);
        model.addAttribute("pickNumber", pickNumber);
        model.addAttribute("progressPercent", getPercent());
        model.addAttribute("currentRoundHandcuffs", BaseController.currentRoundHandcuffs);
        model.addAttribute("allPlayersList", BaseController.getAllPlayers());
    }
    
}
