package com.falifa.draftbuddy.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.data.ModelUpdater;
import com.falifa.draftbuddy.ui.exception.FalifaException;
import com.falifa.draftbuddy.ui.logic.LogicHandler;
import com.falifa.draftbuddy.ui.manager.NFLTeamManager;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.NFLTeam;

@Controller
public class PageController {
	
	@Autowired
	private DraftState draftState;
	
	@Autowired
	private ModelUpdater modelUpdater;

	
    @RequestMapping(value = "/pos")
    public String positionPage(@RequestParam(required=false, defaultValue="all") String pos, Model model) throws FalifaException {
    	modelUpdater.updateModelForPositionPage(pos, model);
        return "pages/positionPage";
    }
    
    @RequestMapping(value = "/nflTeamsTeams")
    public String teamPage(@RequestParam(required=false, defaultValue="1") String teamId, Model model) throws FalifaException {
		modelUpdater.updateModelForTeamPage(teamId, model);
        return "pages/teamPage";
    }
    
    @RequestMapping(value = "/drafters")
    public String drafterPage(@RequestParam(required=false, defaultValue="") String drafterName, Model model) throws FalifaException {
		Drafter drafter = null;
    	for (Drafter d : draftState.draft.getDrafters()) {
    		if (d.getName().equals(drafterName)) {
    			drafter = d;
    		}
    	}
		modelUpdater.updateModelForDrafterPage(drafter, model);
        return "pages/drafterPage";
    }
    
    @RequestMapping(value = "/dashboard")
	public String dashboard(Model model) {
    	draftState.errorMessage = null;
    	modelUpdater.updateCommonAttributes(draftState.currentDrafter, model);
		return "pages/dashboardPage";
    }
    
    @RequestMapping(value = "/draftBoard")
	public String draftBoard(Model model) {
    	modelUpdater.updateModelForDraftBoardPage(model);
    	return "pages/draftBoardPage";
    }
    
}
