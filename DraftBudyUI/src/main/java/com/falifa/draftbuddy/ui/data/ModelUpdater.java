package com.falifa.draftbuddy.ui.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.drafting.LogicHandler;
import com.falifa.draftbuddy.ui.drafting.sort.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerADPComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerADPValueComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerAveragePriorPointsComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerPriorAverageTargetsComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerPriorPointsComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerPriorTotalTargetsComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerProjectedPointsComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerRankComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerVsADPValueComparator;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class ModelUpdater {
	
	private static final Logger log = LoggerFactory.getLogger(ModelUpdater.class);

	@Autowired
	private DraftState draftState;
	
	@Autowired
	private NFLTeamManager nflTeams;
	
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
		model.addAttribute("playersSortedBySuggestions", handler.getSortedSuggestedPlayers(draftState.getCurrentDrafter()));
		for (Position position : Position.values()) {
			model.addAttribute("drafted" + position.getAbbrev(), draftState.currentDrafter.getDraftedTeam().getPlayersByPosition(position)); 
		}
		log.debug("Models updated for drafter={}", draftState.getCurrentDrafter().getName());
	}

	public void updateNflTeamListsAttributes(Model model) {
		model.addAttribute("playersToBuildModalFor", nflTeams.getAllPlayersByADP());
		model.addAttribute("playersSortedByAdp", nflTeams.getAllAvailablePlayersByADP());
      	model.addAttribute("playersSortedByRank", nflTeams.getAllAvailablePlayersByRank());
        log.debug("Models updated for team list attributes");
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
	    	model.addAttribute("playerList", nflTeams.getAllAvailablePlayersByADP());
	    	model.addAttribute("playersToBuildModalFor", nflTeams.getAllAvailablePlayersByADP());
	    	model.addAttribute("positionName", "All Available Players");
	    	model.addAttribute("positionAbbrev", "ALL");
		} else {
	    	model.addAttribute("playerList", nflTeams.getAvailablePlayersByPositionAsList(Position.get(pos)));
	    	model.addAttribute("playersToBuildModalFor", nflTeams.getAvailablePlayersByPositionAsList(Position.get(pos)));
	    	model.addAttribute("positionName", Position.get(pos).getName());
	    	model.addAttribute("positionAbbrev", Position.get(pos).getAbbrev());
		}
        updateCommonAttributesSubset(model);
        log.debug("Models updated for position page");
	}

	public void updateModelForTeamPage(String teamAbbrev, Model model) {
		NFLTeam team = nflTeams.getTeam(teamAbbrev);
		model.addAttribute("team", team);
		model.addAttribute("teamName", team.getTeam().getFullName());
		model.addAttribute("playersToBuildModalFor", team.getPlayers());
		model.addAttribute("allTeams", nflTeams.getNflTeamsSortedByAbbreviation());
        updateCommonAttributesSubset(model);
        log.debug("Models updated for team page");
	}

	public void updateModelForDrafterPage(Model model) {
		model.addAttribute("drafters", draftState.draft.getDrafters());
		model.addAttribute("playersToBuildModalFor", nflTeams.getAllUnavailablePlayersByADP());
        updateCommonAttributesSubset(model);
        log.debug("Models updated for drafter page");
	}

	public void updateModelForDraftBoardPage(Model model) {
		List<Drafter> drafterList = new ArrayList<Drafter>(draftState.draft.getDrafters());
		model.addAttribute("playersToBuildModalFor", nflTeams.getAllUnavailablePlayersByADP());
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
