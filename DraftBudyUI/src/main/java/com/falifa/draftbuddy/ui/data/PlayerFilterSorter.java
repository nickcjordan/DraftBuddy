package com.falifa.draftbuddy.ui.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

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
			case "SUGGESTED" : break;
		}
		return players;
	}

}
