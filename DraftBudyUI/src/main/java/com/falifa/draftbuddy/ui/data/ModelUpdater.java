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

	public void updateCommonAttributes(Model model) {
		updateCurrentDrafterAttributes(model);
		updateDraftStateAttributes(model);
      	updateNflTeamListsAttributes(model);
      	log.debug("Models updated for all fields :: updated for drafter={}", draftState.getCurrentDrafter().getName());
      	printModel(model);
	}
	
	private void updateCommonAttributesSubset(Model model) {
		model.addAttribute("error", draftState.errorMessage);
		updateDraftStateAttributes(model);
		log.debug("Models updated for common subset");
		printModel(model);
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
//		model.addAttribute("team", drafter.getDraftedTeam());
//		model.addAttribute("teamName", drafter.getName());
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
	
	private void printModel(Model model) {
//		System.out.println("\n\nMODEL:\n");
//		for (String name : model.asMap().keySet()) {
//			System.out.println("\t" + name);
//		}
		
//		ObjectWriter writer = new ObjectMapper().writer();
//		for (Entry<String, Object> entry : model.asMap().entrySet()) {
//			try {
//				System.out.println("\n\n\t" + entry.getKey() + ":\n" + writer.writeValueAsString(entry.getValue()) + "\n\n");
//			} catch (JsonProcessingException e) {
//				e.printStackTrace(); // TODO
//			}
//		}
	}

	public List<Player> filterAndSort(List<Player> players, String sortBy, Model model) {
		List<Player> sorted = players;
		Comparator<Player> compareBy = null;
		switch (sortBy) {
			case "ADP" : 	
								compareBy = new PlayerADPComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getRankMetadata().getAdp())).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy); 
								break;
			case "ECR" : 	
								compareBy = new PlayerRankComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getRankMetadata().getOverallRank())).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy); 
								break;
			case "NAME" : 
								compareBy = new AlphabetizedPlayerComparator(); 
								Collections.sort(sorted, compareBy); 
								break;
			case "PROJ_PTS" : 
								compareBy = new PlayerProjectedPointsComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getProjectedTotalPoints())).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy);
								Collections.reverse(sorted);
								break;
			case "PRIOR_PTS" : 
								compareBy = new PlayerPriorPointsComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorTotalPoints())).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy);
								Collections.reverse(sorted);
								break;
			case "AVG_PRIOR_PTS" : 
								compareBy = new PlayerAveragePriorPointsComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorAveragePointsPerGame())).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy);
								Collections.reverse(sorted);
								break;
			case "ADP_VAL" : 
								compareBy = new PlayerADPValueComparator(); 
								Collections.sort(sorted, compareBy);
								Collections.reverse(sorted);
								break;
			case "VS_ADP_VAL" : compareBy = new PlayerVsADPValueComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getRankMetadata().getVsAdp()) && Integer.valueOf(p.getRankMetadata().getAdp()) < 400).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy);
								Collections.reverse(sorted);
								break;
			case "TOT_TARGETS" : 
								compareBy = new PlayerPriorTotalTargetsComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorTotalTargets())).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy);
								Collections.reverse(sorted);
								break;
			case "AVG_TARGETS" : 
								compareBy = new PlayerPriorAverageTargetsComparator(); 
								sorted = sorted.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorAverageTargetsPerGame())).collect(Collectors.toList()); 
								Collections.sort(sorted, compareBy);
								Collections.reverse(sorted);
								break;
			case "SUGGESTED" : compareBy = null; 
								break;
		}
		return sorted;
	}
	
}
