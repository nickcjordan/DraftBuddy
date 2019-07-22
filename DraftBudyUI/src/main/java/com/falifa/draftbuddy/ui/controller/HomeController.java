package com.falifa.draftbuddy.ui.controller;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.*;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.exception.FalifaException;
import com.falifa.draftbuddy.ui.io.DataFileReader;
import com.falifa.draftbuddy.ui.io.StatsCleaner;
import com.falifa.draftbuddy.ui.logic.LogicHandler;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.NFL;

@Controller
public class HomeController extends BaseController {

    @RequestMapping(value = "/")
    public String init(Model model) {
    	NFL.initNFL();
    	initDraft();
        return "home";
    }
    
//    @RequestMapping(value = "/ui/updateDataFiles")
//    public String updateDataFiles() {
//    	Log.info("Updating data source files...");
//    	DataFileReader data = ;
//    	data.
////    	String dataFilePath = data.getDataFilePathFromFantasyProsWebsite(ECR_FANTASYPROS_URL);
////    	Log.info("Downloading from dataFilePath: " + dataFilePath);
////    	new DataFileReader().downloadFileFromUrl(dataFilePath, ECR_FANTASYPROS_TEST_PATH);
//        return "/";
//    }
	
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
