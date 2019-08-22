package com.falifa.draftbuddy.ui.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	
	private static final String ALL = "ALL";

	private static final Logger log = LoggerFactory.getLogger(DraftController.class);
	
	private static final String DASHBOARD_PAGE = "pages/dashboardPage";
	private static final String POSITION_PAGE = "pages/positionPage";
	
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
		draftManager.clearUndoToStack();
		return (draftState.draftType.equals(DraftType.AUTO_DRAFT)) ? draftManager.autoDraft(model) 
				: (draftState.draftType.equals(DraftType.MOCK_DRAFT ))  ? draftManager.mockDraft(model) : DASHBOARD_PAGE;
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
			return DASHBOARD_PAGE;
		}
		draftManager.doBaseDraft(player, model);
    	return draftManager.draftHasCompleted() ? draftManager.prepareResults(model) 
    			: draftState.mockDraftMode ? draftManager.mockDraft(model) : DASHBOARD_PAGE;
    }
	
	@RequestMapping("/sortSuggestions")
	public String sortSuggestedPlayers(@RequestParam String sortBy, Model model) {
		modelUpdater.updateCommonAttributes(model);
		model.addAttribute("playersSortedBySuggestions", modelUpdater.sortAndFilterPlayersBy(handler.getSortedSuggestedPlayers(draftState.getCurrentDrafter()), sortBy, null));
		return DASHBOARD_PAGE;
	}
	
	@RequestMapping("/sortPositionPage")
	public String sortPositionPage(@RequestParam String sortBy, @RequestParam(defaultValue = ALL) String position, Model model) {
		modelUpdater.updateModelForPositionPage(position, model);
		List<Player> playerList = position.equals(ALL) ? nflTeams.getAllAvailablePlayersByADP() : nflTeams.getAvailablePlayersByPositionAsList(Position.get(position));
		model.addAttribute("playerList", modelUpdater.sortPlayersBy(playerList, sortBy));
		return POSITION_PAGE;
	}
	
	@RequestMapping("/undo")
	public String undoLastPick(Model model) {
		boolean success = draftManager.undoLastDraftPick(model);
		log.debug("Draft pick UNDO result: success={}", success);
		return DASHBOARD_PAGE;
	}
	
	@RequestMapping(value = "/filter")
	public String filterPlayers(@RequestParam String filterText, Model model) {
		modelUpdater.updateModelForPositionPage(ALL, model);
		List<Player> playerList = nflTeams.getAllAvailablePlayersByADP();
		if (StringUtils.isEmpty(filterText)) {
			log.info("Clearing filter on player list");
			model.addAttribute("playerList", modelUpdater.filterPlayersBy(playerList, null));
		} else {
			log.info("Filtering player list by filterText={}", filterText);
			model.addAttribute("playerList", modelUpdater.filterPlayersBy(playerList, filterText));
		}
		return POSITION_PAGE;
	}
	
}
