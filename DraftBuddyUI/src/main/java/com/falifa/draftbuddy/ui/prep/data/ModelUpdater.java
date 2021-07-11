package com.falifa.draftbuddy.ui.prep.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.draft.LogicHandler;
import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.data.model.HighestRemainingPositionInTierTO;
import com.falifa.draftbuddy.ui.prep.data.model.RemainingTierTO;

@Component
public class ModelUpdater {
	
	private static final Logger log = LoggerFactory.getLogger(ModelUpdater.class);

	@Autowired
	private DraftState draftState;
	
	@Autowired
	private LogicHandler handler;
	
	@Autowired
	private PlayerFilterSorter filterSorter;
	
	private String nameFilterText;
	private String selectedSort;
	
	public void updateCommonAttributes(Model model) {
		updateCurrentDrafterAttributes(model);
		updateDraftStateAttributes(model);
      	updateNflTeamListsAttributes(model);
      	log.debug("Models updated for all fields :: updated for drafter={}", draftState.getCurrentDrafter().getName());
	}
	
	private void updateCommonAttributesSubset(Model model) {
		model.addAttribute("error", draftState.errorMessage);
		updateDraftStateAttributes(model);
		log.debug("Models updated for common subset");
	}

	public void updateCurrentDrafterAttributes(Model model) {
		model.addAttribute("currentDrafter", draftState.getCurrentDrafter());
		List<Player> suggs = handler.getSortedSuggestedPlayers(draftState.getCurrentDrafter());
		model.addAttribute("playersSortedBySuggestions", suggs);
		for (Position position : Position.values()) {
			model.addAttribute("drafted" + position.getAbbrev(), draftState.currentDrafter.getDraftedTeam().getPlayersByPosition(position)); 
		}
		log.debug("Models updated for drafter={}", draftState.getCurrentDrafter().getName());
	}

	public void updateNflTeamListsAttributes(Model model) {
		model.addAttribute("playersToBuildModalFor", NFLTeamManager.getAllPlayersByADP());
		model.addAttribute("playersSortedByAdp", NFLTeamManager.getAllAvailablePlayersByADP());
      	model.addAttribute("playersSortedByRank", NFLTeamManager.getAllAvailablePlayersByRank());
      	updateRemainingPlayersForTierByPosition(model);
        log.debug("Models updated for team list attributes");
	}
	
	public void updateRemainingPlayersForTierByPosition(Model model) {
		model.addAttribute("remainingTierTO", buildRemainigTierTO());
        log.debug("Models updated for remaining players at current tier for each position");
	}

	private RemainingTierTO buildRemainigTierTO() {
		RemainingTierTO to = new RemainingTierTO();
		List<HighestRemainingPositionInTierTO> tos = new ArrayList<HighestRemainingPositionInTierTO>();
		tos.add(buildHighestRemainingTierTO(5));
		tos.add(buildHighestRemainingTierTO(5, Position.QUARTERBACK));
		tos.add(buildHighestRemainingTierTO(5, Position.RUNNINGBACK));
		tos.add(buildHighestRemainingTierTO(5, Position.WIDERECEIVER));
		tos.add(buildHighestRemainingTierTO(5, Position.TIGHTEND));
		tos.sort((HighestRemainingPositionInTierTO t1, HighestRemainingPositionInTierTO t2) -> t1.getTier().compareTo(t2.getTier()));
		to.setTos(tos);
		return to;
	}

	private HighestRemainingPositionInTierTO buildHighestRemainingTierTO(int i, Position pos) {
		HighestRemainingPositionInTierTO to = new HighestRemainingPositionInTierTO();
		to.setPlayers(getRemainingPlayersAtHighestTierByPosition(i, pos));
		to.setPos(pos.getAbbrev());
		to.setTier(to.getPlayers().get(0).getTier());
		return to;
	}

	private List<Player> getRemainingPlayersAtHighestTierByPosition(int amount, Position pos) {
		List<Player> list = NFLTeamManager.getAvailablePlayersByPositionAsListByECR(pos);
		Player first = list.get(0);
		List<Player> filtered = list.stream().filter(p -> p.getTier() <= first.getTier()).collect(Collectors.toList());
		if (filtered.size() > amount) {
			return filtered.subList(0, amount);
		}
		return filtered;
	}
	
	private HighestRemainingPositionInTierTO buildHighestRemainingTierTO(int i) {
		HighestRemainingPositionInTierTO to = new HighestRemainingPositionInTierTO();
		to.setPlayers(getAllRemainingPlayersAtHighestTier(i));
		to.setPos("Flex");
		to.setTier(to.getPlayers().get(0).getTier());
		return to;
	}
	
	private List<Player> getAllRemainingPlayersAtHighestTier(int amount) {
		List<Player> list = NFLTeamManager.getAllAvailablePlayersByRank();
		Player first = list.get(0);
		List<Player> filtered = list.stream().filter(p -> p.getTier() <= first.getTier()).collect(Collectors.toList());
		if (filtered.size() > amount) {
			return filtered.subList(0, amount);
		}
		return filtered;
	}

	public void updateDraftStateAttributes(Model model) {
		model.addAttribute("progressPercent", draftState.getPercent());
		model.addAttribute("draft", draftState);
        model.addAttribute("roundNumber", Math.min(draftState.roundNum, draftState.NUMBER_OF_ROUNDS));
      	model.addAttribute("drafters", draftState.getCorrectlyOrderedDrafterList());
      	model.addAttribute("strategy", draftState.getStrategyByRound().get(String.valueOf(draftState.roundNum)));
      	log.debug("Models updated for draft state attributes");
	}
	

	public void updateModelForPositionPage(String pos, Model model) {
		if (StringUtils.isEmpty(pos) || pos.equalsIgnoreCase("ALL")) {
	    	model.addAttribute("playerList", NFLTeamManager.getAllAvailablePlayersByADP());
	    	model.addAttribute("playersToBuildModalFor", NFLTeamManager.getAllAvailablePlayersByADP());
	    	model.addAttribute("positionName", "All Available Players");
	    	model.addAttribute("positionAbbrev", "ALL");
		} else {
	    	model.addAttribute("playerList", NFLTeamManager.getAvailablePlayersByPositionAsListByADP(Position.get(pos)));
	    	model.addAttribute("playersToBuildModalFor", NFLTeamManager.getAvailablePlayersByPositionAsListByADP(Position.get(pos)));
	    	model.addAttribute("positionName", Position.get(pos).getName());
	    	model.addAttribute("positionAbbrev", Position.get(pos).getAbbrev());
		}
        updateCommonAttributesSubset(model);
        log.debug("Models updated for position page");
	}

	public void updateModelForTeamPage(String teamAbbrev, Model model) {
		NFLTeam team = NFLTeamManager.getTeam(teamAbbrev);
		model.addAttribute("team", team);
		model.addAttribute("teamName", team.getTeam().getFullName());
		model.addAttribute("playersToBuildModalFor", team.getPlayers());
		model.addAttribute("allTeams", NFLTeamManager.getNflTeamsSortedByAbbreviation());
        updateCommonAttributesSubset(model);
        log.debug("Models updated for team page");
	}

	public void updateModelForDrafterPage(Model model) {
		model.addAttribute("drafters", draftState.draft.getDrafters());
		model.addAttribute("playersToBuildModalFor", NFLTeamManager.getAllUnavailablePlayersByADP());
        updateCommonAttributesSubset(model);
        log.debug("Models updated for drafter page");
	}

	public void updateModelForDraftBoardPage(Model model) {
		List<Drafter> drafterList = new ArrayList<Drafter>(draftState.draft.getDrafters());
		model.addAttribute("playersToBuildModalFor", NFLTeamManager.getAllUnavailablePlayersByADP());
    	if (!draftState.draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
    		Collections.reverse(drafterList);
    	}
    	model.addAttribute("drafters", drafterList);
    	model.addAttribute("draft", draftState);
    	updateCommonAttributesSubset(model);
    	log.debug("Models updated for draftboard page");
	}
	
	public List<Player> sortPlayersBy(List<Player> players, String sortBy) {
		selectedSort = sortBy;
		return filterSorter.filterAndSort(nameFilterText, selectedSort, players);
	}
	
	public List<Player> filterPlayersBy(List<Player> players, String filterText) {
		nameFilterText = filterText;
		return filterSorter.filterAndSort(nameFilterText, selectedSort, players);
	}
	
	public List<Player> sortAndFilterPlayersBy(List<Player> players, String sortBy, String filterBy) {
		nameFilterText = filterBy;
		selectedSort = sortBy;
		return filterSorter.filterAndSort(nameFilterText, selectedSort, players);
	}
	

	public void clearFiltersAndSorts() {
		nameFilterText = null;
		selectedSort = null;
	}
}
