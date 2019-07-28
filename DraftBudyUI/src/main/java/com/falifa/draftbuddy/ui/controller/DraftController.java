package com.falifa.draftbuddy.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.data.ModelUpdater;
import com.falifa.draftbuddy.ui.exception.FalifaException;
import com.falifa.draftbuddy.ui.manager.DraftManager;
import com.falifa.draftbuddy.ui.manager.NFLTeamManager;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.player.Player;

@Controller
public class DraftController {
	
	private static final Logger log = LoggerFactory.getLogger(DraftController.class);
	
	@Autowired
	private DraftState draftState;
	
	@Autowired
	protected NFLTeamManager nflTeams;
	
	@Autowired
	private ModelUpdater modelUpdater;
	
	@Autowired
	private DraftManager draftManager;
	
	
	@RequestMapping(value = "/")
	public String init(Model model) {
		nflTeams.initializeNFL();
		draftState.initializeDraft();
		return "home";
	}
	
	// to hit this with "type" --> "http://localhost:8080/init?appRunType=type"
	@RequestMapping(value = "/start")
	public String start(@RequestParam(required=false, defaultValue="real") String appRunType, Model model) throws FalifaException {
		init(model);
		draftState.mockDraftMode = (appRunType.equalsIgnoreCase("mock") || appRunType.equalsIgnoreCase("auto"));
		draftState.draftType = DraftType.getDraftType(appRunType);
		log.info(appRunType + " :: " + draftState.draftType);
		draftState.draft = new Draft(draftState.draftType.getOrder());
		draftState.currentDrafter = draftState.draft.getDrafters().get(0);
		modelUpdater.updateCommonAttributes(draftState.currentDrafter, model);
		return (draftState.draftType.equals(DraftType.AUTO_DRAFT)) ? draftManager.autoDraft(draftState.currentDrafter, model) 
				: (draftState.draftType.equals(DraftType.MOCK_DRAFT ))  ? draftManager.mockDraft(draftState.currentDrafter, model) : "pages/dashboardPage";
	}

	@RequestMapping(value = "/pickPlayer")
    public String doPickForDrafter(@RequestParam(defaultValue="") String playerId, Model model) throws FalifaException {
		Drafter current = draftState.getCurrentDrafter();
		draftState.setErrorMessage(null);
		Player player = nflTeams.getPlayerById(draftManager.resolvePlayerId(playerId));
		log.info("doPickForDrafter :: picking player " + player.getPlayerName());
		if (!player.getDraftingDetails().isAvailable()) {
			log.error("doPickForDrafter : " + current.getName() + " :: player " + player.getPlayerName() + " is not available");
			modelUpdater.updateCommonAttributes(current, model);
			draftState.setErrorMessage(player.getPlayerName() + " is not available");
			return "pages/dashboardPage";
		}
		draftManager.doBaseDraft(player, current, model);
    	return draftManager.draftHasCompleted() ? draftManager.prepareResults(model) : draftState.mockDraftMode ? draftManager.mockDraft(current, model) : "pages/dashboardPage";
    }

}
