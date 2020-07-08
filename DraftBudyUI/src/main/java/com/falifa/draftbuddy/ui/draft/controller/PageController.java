package com.falifa.draftbuddy.ui.draft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.prep.data.ModelUpdater;

@Controller
public class PageController {
	
	private static final Logger log = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private DraftState draftState;
	
	@Autowired
	private ModelUpdater modelUpdater;

	
    @RequestMapping(value = "/pos")
    public String positionPage(@RequestParam(required=false, defaultValue="all") String pos, Model model) {
    	modelUpdater.updateModelForPositionPage(pos, model);
        return "pages/positionPage";
    }
    
    @RequestMapping(value = "/nflTeams")
    public String teamPage(@RequestParam(required=false, defaultValue="DAL", name = "teamId") String teamAbbrev, Model model) {
		modelUpdater.updateModelForTeamPage(teamAbbrev, model);
        return "pages/teamPage";
    }
    
    @RequestMapping(value = "/drafters")
    public String drafterPage(Model model) {
		modelUpdater.updateModelForDrafterPage(model);
        return "pages/drafterPage";
    }
    
    @RequestMapping(value = "/dashboard")
	public String dashboard(Model model) {
    	draftState.errorMessage = null;
    	modelUpdater.updateCommonAttributes(model);
		return "pages/dashboardPage";
    }
    
    @RequestMapping(value = "/draftBoard")
	public String draftBoard(Model model) {
    	modelUpdater.updateModelForDraftBoardPage(model);
    	return "pages/draftBoardPage";
    }
    
}
