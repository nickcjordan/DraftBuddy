package com.falifa.draftbuddy.ui.prep.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.draft.LogicHandler;
import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.DrafterSummary;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.PositionalOverview;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
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
	
	@Value("${numberOfRounds}")
	private String numberOfRounds;

	public void updateCommonAttributes(Model model) {
		updateCurrentDrafterAttributes(model);
		updateDraftStateAttributes(model);
		updateNflTeamListsAttributes(model);
		model.addAttribute("allTags", Arrays.asList(Tag.values()));
		log.debug("Models updated for all fields :: updated for drafter={}", draftState.getCurrentDrafter().getName());
	}

	private void updateCommonAttributesSubset(Model model) {
		model.addAttribute("error", draftState.errorMessage);
		model.addAttribute("allTags", Arrays.asList(Tag.values()));
		updateDraftStateAttributes(model);
		log.debug("Models updated for common subset");
	}

	public void updateCurrentDrafterAttributes(Model model) {
		if (draftState.getCurrentDrafter() != null) {
			model.addAttribute("currentDrafter", draftState.getCurrentDrafter());
			List<Player> suggs = handler.getSortedSuggestedPlayers(draftState.getCurrentDrafter());
			model.addAttribute("playersSortedBySuggestions", suggs);
			for (Position position : Position.values()) {
				model.addAttribute("drafted" + position.getAbbrev(),
						draftState.currentDrafter.getDraftedTeam().getPlayersByPosition(position));
			}
			log.debug("Models updated for drafter={}", draftState.getCurrentDrafter().getName());
		}
	}

	public void updateNflTeamListsAttributes(Model model) {
		model.addAttribute("playersToBuildModalFor", NFLTeamManager.getAllPlayersByADP().subList(0,
				Math.min(NFLTeamManager.getAllPlayersByADP().size(), 250)));
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
		tos.sort((HighestRemainingPositionInTierTO t1, HighestRemainingPositionInTierTO t2) -> t1.getTier()
				.compareTo(t2.getTier()));
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
		to.setPos("Players");
		to.setTier(to.getPlayers().get(0).getTier());
		return to;
	}

	private List<Player> getAllRemainingPlayersAtHighestTier(int amount) {
		List<Player> list = NFLTeamManager.getAllAvailablePlayersByRank();
		Player first = list.get(0);
		if (list.size() > 0) {
			List<Player> filtered = list.stream().filter(p -> p.getTier() <= first.getTier())
					.collect(Collectors.toList());
			if (filtered.size() > amount) {
				return filtered.subList(0, amount);
			}
			return filtered;
		} else {
			return new ArrayList<Player>();
		}
	}

	public void updateDraftStateAttributes(Model model) {
		Map<String, PositionalOverview> overviews = buildRemainingPositionPlayersOverview();
		if (draftState.initialOverviews == null) {
			draftState.initialOverviews = overviews;
		}
		model.addAttribute("remainingPositionPlayersOverview", overviews);
		model.addAttribute("upcomingDrafters", buildUpcomingDrafters());
		model.addAttribute("progressPercent", draftState.getPercent());
		model.addAttribute("draft", draftState);
		model.addAttribute("roundNumber", Math.min(draftState.roundNum, draftState.NUMBER_OF_ROUNDS));
		model.addAttribute("drafters", draftState.getCorrectlyOrderedDrafterList());
		model.addAttribute("strategy", draftState.getStrategyByRound().get(String.valueOf(draftState.roundNum)));
		log.debug("Models updated for draft state attributes");
	}

	private List<DrafterSummary> buildUpcomingDrafters() {
		List<DrafterSummary> drafterSummaries = new ArrayList<DrafterSummary>();
		int rounds = this.draftState.sleeperState != null ? this.draftState.sleeperState.getDraft().getSettings().getRounds() : Integer.valueOf(numberOfRounds);
		int drafterCount = draftState.getDraft().getDrafters().size();
		int totalPickCount = rounds * drafterCount;
		int remainingPicks = totalPickCount - draftState.getDraftPicks().size();
		for (int i = 1; i <= Math.min(remainingPicks, 12); i++) {
			int pickInd = draftState.getDraftPicks().size() + 1 + i;
			for (Drafter drafter : draftState.getDraft().getDrafters()) {
				if (drafter.getDraftPickIndices().contains(pickInd)) {
					int round = Math.floorDiv(pickInd, drafterCount) + 1;
					int roundIndex = (pickInd % drafterCount);
					if (roundIndex == 0) {
						roundIndex = drafterCount;
					}
					DrafterSummary summary = buildDrafterSummary(drafter, round, roundIndex);
					drafterSummaries.add(summary);
				}
			}
		}
		return drafterSummaries;
	}

	private DrafterSummary buildDrafterSummary(Drafter drafter, int roundNumber, int roundIndex) {
		DrafterSummary summary = new DrafterSummary();
		String teamName = "Team " + drafter.getName();
		String avatar = null;
		if (drafter.getSleeperUser() != null && drafter.getSleeperUser().getMetadata() != null) {
			if (drafter.getSleeperUser().getMetadata().getTeamName() != null) {
				teamName = drafter.getSleeperUser().getMetadata().getTeamName();
			}
			if (drafter.getSleeperUser().getMetadata().getAvatar() != null) {
				avatar = drafter.getSleeperUser().getMetadata().getAvatar();
			}
		}
		summary.setAvatar(avatar);
		summary.setTeamName(teamName);
		summary.setDrafter(drafter);
		summary.setRoundNumber(roundNumber);
		summary.setRoundIndex(roundIndex);
		return summary;
	}

	private Map<String, PositionalOverview> buildRemainingPositionPlayersOverview() {
		Map<String, PositionalOverview> overviews = new HashMap<String, PositionalOverview>();
		for (Position pos : Position.values()) {
			overviews.put(pos.getAbbrev(), buildPositionalOverview(pos));
		}
		if (draftState.initialOverviews != null) {
			draftState.initialOverviews = overviews;
		}
		return overviews;
	}

	private PositionalOverview buildPositionalOverview(Position pos) {
		PositionalOverview overview = new PositionalOverview(pos);
		int numberOfFirstStringPlayersAtPosition = 0;
		int numberOfSecondStringPlayersAtPosition = 0;
		int numberOfThirdStringPlayersAtPosition = 0;
		int numberOfFourthStringPlayersAtPosition = 0;
		int numberOfFifthStringPlayersAtPosition = 0;
		int numberOfTotalPlayersAtPosition = 0;
		for (Player player : NFLTeamManager.getAvailablePlayersByPositionAsListByADP(pos)) {
			Integer rank = Integer.parseInt(player.getRankMetadata().getPositionRank());
			int numberOfDrafters = draftState.getDraft().getDrafters().size();
			if (rank <= numberOfDrafters) { numberOfFirstStringPlayersAtPosition++; }
			else if (rank <= (numberOfDrafters * 2)) { numberOfSecondStringPlayersAtPosition++; }
			else if (rank <= (numberOfDrafters * 3)) { numberOfThirdStringPlayersAtPosition++; }
			else if (rank <= (numberOfDrafters * 4)) { numberOfFourthStringPlayersAtPosition++; }
			else if (rank <= (numberOfDrafters * 5)) { numberOfFifthStringPlayersAtPosition++; }
			numberOfTotalPlayersAtPosition++;
		}
		overview.getRemainingCounts().set(0, numberOfFirstStringPlayersAtPosition);
		overview.getRemainingCounts().set(1, numberOfSecondStringPlayersAtPosition);
		overview.getRemainingCounts().set(2, numberOfThirdStringPlayersAtPosition);
		overview.getRemainingCounts().set(3, numberOfFourthStringPlayersAtPosition);
		overview.getRemainingCounts().set(4, numberOfFifthStringPlayersAtPosition);
		overview.setTotal(numberOfTotalPlayersAtPosition);
		return overview;
	}

	public void updateModelForPositionPage(String pos, Model model) {
		if (StringUtils.isEmpty(pos) || pos.equalsIgnoreCase("ALL")) {
			model.addAttribute("playerList", NFLTeamManager.getAllAvailablePlayersByADP());
			model.addAttribute("playersToBuildModalFor", NFLTeamManager.getAllAvailablePlayersByADP());
			model.addAttribute("positionName", "All Available Players");
			model.addAttribute("positionAbbrev", "ALL");
		} else {
			model.addAttribute("playerList",
					NFLTeamManager.getAvailablePlayersByPositionAsListByADP(Position.get(pos)));
			model.addAttribute("playersToBuildModalFor",
					NFLTeamManager.getAvailablePlayersByPositionAsListByADP(Position.get(pos)));
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
