package com.falifa.draftbuddy.ui.draft.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftState;
import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.draft.DraftManager;
import com.falifa.draftbuddy.ui.draft.LogicHandler;
import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.draft.sleeper.SleeperAPIManager;
import com.falifa.draftbuddy.ui.draft.sleeper.SleeperDraftManager;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.data.ModelUpdater;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;

@Controller
public class DraftController {
	
	private static final String ALL = "ALL";

	private static final Logger log = LoggerFactory.getLogger(DraftController.class);
	
	private static final String DASHBOARD_PAGE = "pages/dashboardPage";
	private static final String POSITION_PAGE = "pages/positionPage";
	
	@Autowired
	private DraftState draftState;
	
	@Autowired
	private ModelUpdater modelUpdater;
	
	@Autowired
	private DraftManager draftManager;
	
	@Autowired
	private LogicHandler handler;
	
	@Autowired
	private SleeperAPIManager sleeperApi;
	
	@Autowired
	private SleeperDraftManager sleeperManager;
	
	
	@RequestMapping(value = "/")
	public String init() {
//		nflTeams.initializeNFL();
//		draftState.initializeDraft();
		log.info("Going to /home");
		return "home";
	}
	
	// to hit this with "type" --> "http://localhost:8080/start?appRunType=type"
	@RequestMapping(value = "/start")
	public String start(@RequestParam(required=false, defaultValue="real") String appRunType, Model model) {
//		draftState.startTimer();
		NFLTeamManager.initializeNFL();
		draftState.initializeDraft();
		draftManager.updateDraftStrategyDataFromFile();
		
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
	
	@RequestMapping(value = "/startSleeper")
	public String startSleeper(@RequestParam String draftId, Model model) {
		NFLTeamManager.initializeNFL();
		draftState.initializeDraft();
		draftManager.updateDraftStrategyDataFromFile();
		
		draftState.mockDraftMode = false;
		draftState.draftType = DraftType.SLEEPER;
		log.info("SLEEPER DRAFT :: " + draftId);
		
		
		SleeperDraftState sleeperState = sleeperApi.getDraftState(draftId);
//		draftState.draft = new Draft(sleeperState.getDrafters().stream().map(x -> x.getDisplayName()).collect(Collectors.toList()).toArray(new String[0]));
		draftState.draft = new Draft(sleeperState.getDrafters());
		draftState.sleeperState = sleeperState;
		draftState.draftId = draftId;
		
		draftState.initializeDraftersDraftPickIndexList();
		
		sleeperManager.updateDraftStateWithSleeperPicks(sleeperState);

		modelUpdater.updateCommonAttributes(model);
		
		
		return DASHBOARD_PAGE;
	}
	
	@RequestMapping(value = "/refreshSleeper")
	public String refreshSleeper(Model model) {
		if (draftState.sleeperState != null) {
			
//			String draftId = draftState.sleeperState.getDraft().getDraftId();
//			SleeperDraftState sleeperState = sleeperApi.getDraftState(draftId);
//			draftState.sleeperState = sleeperState;
			
//			sleeperManager.updateDraftStateWithSleeperPicks(sleeperState);
			sleeperManager.updateDraftStateWithSleeperPicks(draftState.sleeperState);
			
			modelUpdater.updateCommonAttributes(model);
		}
		
		return DASHBOARD_PAGE;
	}

	@RequestMapping(value = "/pickPlayer")
    public String doPickForDrafter(@RequestParam(defaultValue="") String playerId, Model model) {
//		draftState.startTimer();
		draftState.setErrorMessage(null);
		Player player = NFLTeamManager.getPlayerById(draftManager.resolvePlayerId(playerId));
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
		List<Player> att = modelUpdater.sortAndFilterPlayersBy(handler.getSortedSuggestedPlayers(draftState.getCurrentDrafter()), sortBy, null);
		model.addAttribute("playersSortedBySuggestions", att);
		return DASHBOARD_PAGE;
	}
	
	@RequestMapping("/sortPositionPage")
	public String sortPositionPage(@RequestParam String sortBy, @RequestParam(defaultValue = ALL) String position, Model model) {
		modelUpdater.updateModelForPositionPage(position, model);
		List<Player> playerList = position.equals(ALL) ? NFLTeamManager.getAllAvailablePlayersByADP() : NFLTeamManager.getAvailablePlayersByPositionAsListByADP(Position.get(position));
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
		List<Player> playerList = NFLTeamManager.getAllAvailablePlayersByADP();
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
