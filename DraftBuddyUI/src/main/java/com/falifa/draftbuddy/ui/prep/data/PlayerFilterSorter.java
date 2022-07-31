package com.falifa.draftbuddy.ui.prep.data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.draft.compare.FFBallerFlexGradeComparator;
import com.falifa.draftbuddy.ui.draft.compare.FFBallerFlexRankComparator;
import com.falifa.draftbuddy.ui.draft.compare.FFBallerPositionRankComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerADPComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerADPValueComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerAveragePriorPointsComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerPriorAverageTargetsComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerPriorPointsComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerPriorTotalTargetsComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerProjectedPointsComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerRankComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerVsADPValueComparator;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class PlayerFilterSorter {
	
	public List<Player> filterAndSort(String nameFilterText, String selectedSort, List<Player> players) {
		List<Player> sorted = sortPlayers(selectedSort, players);
		List<Player> filtered = filterPlayers(nameFilterText, sorted);
		return filtered;
	}

	private List<Player> filterPlayers(String nameFilterText, List<Player> players) {
		if (StringUtils.isNotEmpty(nameFilterText)) {
			return players.stream().filter(p -> p.getPlayerName().toLowerCase().replace(" ", "").contains(nameFilterText.toLowerCase().replace(" ", ""))).collect(Collectors.toList());
		} else {
			return players;
		}
	}

	private List<Player> sortPlayers(String selectedSort, List<Player> players) {
		if (StringUtils.isEmpty(selectedSort)) {
			selectedSort = "ADP";
		}
		switch (selectedSort) {
			case "ADP" : 	
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getRankMetadata().getAdp())).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerADPComparator());
								break;
			case "ECR" : 	
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getRankMetadata().getOverallRank())).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerRankComparator()); 
								break;
			case "NAME" : 
								Collections.sort(players, new AlphabetizedPlayerComparator()); 
								break;
			case "PROJ_PTS" : 
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getProjectedTotalPoints())).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerProjectedPointsComparator());
								Collections.reverse(players);
								break;
			case "PRIOR_PTS" : 
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorTotalPoints())).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerPriorPointsComparator());
								Collections.reverse(players);
								break;
			case "AVG_PRIOR_PTS" : 
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorAveragePointsPerGame())).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerAveragePriorPointsComparator());
								Collections.reverse(players);
								break;
			case "ADP_VAL" : 
								Collections.sort(players, new PlayerADPValueComparator());
								Collections.reverse(players);
								break;
			case "VS_ADP_VAL" : 
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getRankMetadata().getVsAdp()) && Integer.valueOf(p.getRankMetadata().getAdp()) < 400).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerVsADPValueComparator());
								Collections.reverse(players);
								break;
			case "TOT_TARGETS" : 
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorTotalTargets())).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerPriorTotalTargetsComparator());
								Collections.reverse(players);
								break;
			case "AVG_TARGETS" : 
								players = players.stream().filter(p -> StringUtils.isNotEmpty(p.getPositionalStats().getPriorAverageTargetsPerGame())).collect(Collectors.toList()); 
								Collections.sort(players, new PlayerPriorAverageTargetsComparator());
								Collections.reverse(players);
								break;
			case "FFB_RANK" : 
								players = players.stream().filter(p -> (p.getFfBallersPlayerProjection() != null && p.getFfBallersPlayerProjection().getPositionRank() != null)).collect(Collectors.toList()); 
								Collections.sort(players, new FFBallerPositionRankComparator());
								Collections.reverse(players);
								break;
			case "FFB_FLEX_RANK" : 
								players = players.stream().filter(p -> (p.getFfBallersPlayerProjection() != null && p.getFfBallersPlayerProjection().getFlexRank() != 0)).collect(Collectors.toList()); 
								Collections.sort(players, new FFBallerFlexRankComparator());
								Collections.reverse(players);
								break;
			case "FFB_FLEX_GRADE" : 
								players = players.stream().filter(p -> (p.getFfBallersPlayerProjection() != null && p.getFfBallersPlayerProjection().getFlexGrade() != 0)).collect(Collectors.toList()); 
								Collections.sort(players, new FFBallerFlexGradeComparator());
								break;
								
								
			case "SUGGESTED" : break;
		}
		return players;
	}

}
