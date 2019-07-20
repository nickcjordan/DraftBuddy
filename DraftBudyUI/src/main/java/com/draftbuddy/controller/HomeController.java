package com.draftbuddy.controller;

import static com.draftbuddy.constants.DataSourcePaths.*;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.draftbuddy.Log;
import com.draftbuddy.constants.DraftType;
import com.draftbuddy.constants.Position;
import com.draftbuddy.exception.FalifaException;
import com.draftbuddy.io.DataFileReader;
import com.draftbuddy.io.StatsCleaner;
import com.draftbuddy.logic.LogicHandler;
import com.draftbuddy.model.Draft;
import com.draftbuddy.model.NFL;

@Controller
public class HomeController extends BaseController {

    @RequestMapping(value = "/")
    public String init(Model model) {
    	NFL.initNFL();
    	initDraft();
        return "home";
    }
    
    @RequestMapping(value = "/update")
    public String updateDataFiles() {
    	Log.info("Updating data source files...");
    	DataFileReader data = new DataFileReader();
    	String dataFilePath = data.getDataFilePathFromFantasyProsWebsite(ECR_FANTASYPROS_URL);
    	Log.info("Downloading from dataFilePath: " + dataFilePath);
    	data.downloadFileFromUrl(dataFilePath, ECR_FANTASYPROS_TEST_PATH);
        return "/";
    }
	
	// to hit this with "type" --> "http://localhost:8080/init?appRunType=type"
    @RequestMapping(value = "/start")
    public String start(@RequestParam(required=false, defaultValue="real") String appRunType, Model model) throws FalifaException {
    	mockDraftMode = false;
    	BaseController.draftType = DraftType.getDraftType(appRunType);
    	Log.deb(appRunType + " : " + draftType);
    	BaseController.draft = new Draft(draftType.getOrder());
        BaseController.currentDrafter = draft.getDrafters().get(0);
        
        model.addAttribute("progressPercent", getPercent());
        model.addAttribute("draftType", draftType);
    	model.addAttribute("draft", draft);
		model.addAttribute("currentDrafter", currentDrafter);
		
		model.addAttribute("playersSortedBySuggestions", getSuggestedAvailablePlayers(currentDrafter));
		model.addAttribute("playersSortedByAdp", NFL.getAllAvailablePlayersByADP());
		model.addAttribute("playersSortedByRank", NFL.getAllAvailablePlayersByRank());
		
		model.addAttribute("roundNumber", roundNum);
		model.addAttribute("pickNumber", pickNumber);
		model.addAttribute("draftPicks", draftPicks);
		model.addAttribute("playerList", NFL.getAllAvailablePlayersByADP());
		model.addAttribute("currentDraftedTeam", currentDrafter.getDraftedTeam());
      	model.addAttribute("strategy", strategyByRound.get(String.valueOf(roundNum)));
      	model.addAttribute("draftersPickNumberList", new LogicHandler(currentDrafter).getDraftPickIndexList());
      	model.addAttribute("allPlayersList", BaseController.getAllPlayers());
		for (Position position : Position.values()) {
			model.addAttribute("drafted" + position.getAbbrev(), currentDrafter.getDraftedTeam().getPlayersByPosition(position)); 
			model.addAttribute(position.getAbbrev() + "List", NFL.getAvailablePlayersByPositionAsList(position));
		}
        DraftController c = new DraftController();
        return (draftType.equals(DraftType.AUTO_DRAFT)) ? c.autoDraft(model) : (draftType.equals(DraftType.MOCK_DRAFT ))  ? c.mockDraft(model) : "pages/dashboardPage";
    }

}
