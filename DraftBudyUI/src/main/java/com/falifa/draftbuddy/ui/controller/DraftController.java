package com.falifa.draftbuddy.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.data.ModelUpdater;
import com.falifa.draftbuddy.ui.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.drafting.DraftManager;
import com.falifa.draftbuddy.ui.drafting.LogicHandler;
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
	
	@Autowired
	private LogicHandler handler;
	
	
	@RequestMapping(value = "/")
	public String init() {
		nflTeams.initializeNFL();
		draftState.initializeDraft();
		return "home";
	}
	
	// to hit this with "type" --> "http://localhost:8080/start?appRunType=type"
	@RequestMapping(value = "/start")
	public String start(@RequestParam(required=false, defaultValue="real") String appRunType, Model model) {
//		draftState.startTimer();
		if (CollectionUtils.isEmpty(draftState.getDraftPicks())) { init(); }
		draftState.mockDraftMode = (appRunType.equalsIgnoreCase("mock") || appRunType.equalsIgnoreCase("auto"));
		draftState.draftType = DraftType.getDraftType(appRunType);
		log.info(appRunType + " :: " + draftState.draftType);
		draftState.draft = new Draft(draftState.draftType.getOrder());
		draftState.currentDrafter = draftState.draft.getDrafters().get(0);
		draftManager.setOptimizedDrafter(draftState.draft);
		draftState.initializeDraftersDraftPickIndexList();
		modelUpdater.updateCommonAttributes(model);
		return (draftState.draftType.equals(DraftType.AUTO_DRAFT)) ? draftManager.autoDraft(model) 
				: (draftState.draftType.equals(DraftType.MOCK_DRAFT ))  ? draftManager.mockDraft(model) : "pages/dashboardPage";
	}

	@RequestMapping(value = "/pickPlayer")
    public String doPickForDrafter(@RequestParam(defaultValue="") String playerId, Model model) {
//		draftState.startTimer();
		draftState.setErrorMessage(null);
		Player player = nflTeams.getPlayerById(draftManager.resolvePlayerId(playerId));
		log.debug("doPickForDrafter :: picking player " + player.getPlayerName());
		if (!player.getDraftingDetails().isAvailable()) {
			log.error("doPickForDrafter : " + draftState.getCurrentDrafter().getName() + " :: player " + player.getPlayerName() + " is not available");
			draftState.setErrorMessage(player.getPlayerName() + " is not available");
			modelUpdater.updateCommonAttributes(model);
			return "pages/dashboardPage";
		}
		draftManager.doBaseDraft(player, model);
    	return draftManager.draftHasCompleted() ? draftManager.prepareResults(model) 
    			: draftState.mockDraftMode ? draftManager.mockDraft(model) : "pages/dashboardPage";
    }
	
	@RequestMapping("/sortSuggestions")
	public String sortSuggestedPlayers(@RequestParam String sortBy, Model model) {
		modelUpdater.updateCommonAttributes(model);
		model.addAttribute("playersSortedBySuggestions", modelUpdater.filterAndSort(handler.getSortedSuggestedPlayers(draftState.getCurrentDrafter()), sortBy, model));
		return "pages/dashboardPage";
	}
	
	@RequestMapping("/sortPositionPage")
	public String sortPositionPage(@RequestParam String sortBy, @RequestParam String position, Model model) {
		modelUpdater.updateModelForPositionPage(position, model);
		model.addAttribute("playerList", modelUpdater.filterAndSort(nflTeams.getAvailablePlayersByPositionAsList(Position.get(position)), sortBy, model));
		return "pages/positionPage";
	}
///sortPositionPage?sortBy=AVG_TARGETS&position=${position}
}
