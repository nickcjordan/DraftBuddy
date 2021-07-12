package com.falifa.draftbuddy.ui.prep.data;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.DRAFT_STRATEGY_BY_ROUND_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_NFL_TEAMS_JSON_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_PLAYERS_JSON_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYER_TAGS_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.draft.DraftManager;
import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedTeamComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerADPComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerRankComparator;
import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.MasterTeamTO;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.api.PlayerNameMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NFLTeamManager {

	private static final Logger log = LoggerFactory.getLogger(NFLTeamManager.class);

	private static Map<String, NFLTeam> teamsByAbbreviation;
	private static Map<String, Player> playersById;

	 static {
		playersById = new HashMap<String, Player>();
		teamsByAbbreviation = new HashMap<String, NFLTeam>();
	}

	public static void initializeNFL() {
		try {
			populateMasterPlayersFromJsonFile();
			populateMasterTeamsFromJsonFile();
			log.info("NFL initialized with {} teams and {} players", teamsByAbbreviation.size(), playersById.size());
		} catch (Exception e) {
			log.error("ERROR initializing master players from json", e);
		}
	}

	private static void populateMasterTeamsFromJsonFile() {
		try {
			teamsByAbbreviation = new ObjectMapper().readValue(new File(MASTER_NFL_TEAMS_JSON_FILE_PATH), MasterTeamTO.class).getTeams();
		} catch (FileNotFoundException ex) {
			log.error("Did not find master teams json at path={}", MASTER_NFL_TEAMS_JSON_FILE_PATH);
		} catch (Exception e) {
			log.error("ERROR extracting master teams json from file at path={}", MASTER_NFL_TEAMS_JSON_FILE_PATH);
			e.printStackTrace();
		}
	}

	private static void populateMasterPlayersFromJsonFile() {
		try {
			playersById = new ObjectMapper().readValue(new File(MASTER_PLAYERS_JSON_FILE_PATH), MasterPlayersTO.class).getPlayers();
		} catch (FileNotFoundException ex) {
			log.error("Did not find master players json at path={}", MASTER_PLAYERS_JSON_FILE_PATH);
		} catch (Exception e) {
			log.error("ERROR extracting master players json from file at path={}", MASTER_PLAYERS_JSON_FILE_PATH);
			e.printStackTrace();
		}
	}

	public static Map<String, Player> getPlayersById() {
		return playersById;
	}

	public static void setPlayersById(Map<String, Player> players) {
		playersById = players;
	}

	public static Map<String, NFLTeam> getTeamsByAbbreviation() {
		return teamsByAbbreviation;
	}

	public static List<NFLTeam> getTeamsSortedByAbbreviation() {
		List<NFLTeam> teamsCollection = new ArrayList<NFLTeam>(teamsByAbbreviation.values());
		Collections.sort(teamsCollection, new AlphabetizedTeamComparator());
		return teamsCollection;
	}

	public static void setTeamsByAbbreviation(Map<String, NFLTeam> teams) {
		teamsByAbbreviation = teams;
	}

	public static Player getPlayerById(String fantasyProsId) {
		return playersById.get(fantasyProsId);
	}

	private static List<Player> getAllPlayers(Comparator<Player> comparator, boolean isAvailable) {
		List<Player> allAvailable = playersById.values().stream().filter(p -> isAvailable == p.getDraftingDetails().isAvailable() && p.getPosition() != null).collect(Collectors.toList());
		Collections.sort(allAvailable, comparator);
		return allAvailable;
	}
	
	private static List<Player> getAllPlayers(Comparator<Player> comparator) {
		List<Player> allAvailable = new ArrayList<Player>(playersById.values().stream().filter(p -> p.getPosition() != null).collect(Collectors.toList()));
		Collections.sort(allAvailable, comparator);
		return allAvailable;
	}

	public static List<Player> getAllAvailablePlayersByADP() {
		return getAllPlayers(new PlayerADPComparator(), true);
	}

	public static List<Player> getAllAvailablePlayersByRank() {
		return getAllPlayers(new PlayerRankComparator(), true);
	}
	
	public static List<Player> getAllUnavailablePlayersByADP() {
		return getAllPlayers(new PlayerADPComparator(), false);
	}
	
	public static List<Player> getAllPlayersByADP() {
		return getAllPlayers(new PlayerADPComparator());
	}
	
	public static List<Player> getAvailablePlayersByPositionAsListByADP(Position position) {
		List<Player> byPosition = position == null ? playersById.values().stream().filter(p -> p.getPosition() != null && p.getDraftingDetails().isAvailable()).collect(Collectors.toList()) : playersById.values().stream().filter(p -> position.equals(p.getPosition()) && p.getDraftingDetails().isAvailable()).collect(Collectors.toList());
		Collections.sort(byPosition, new PlayerADPComparator());
		return byPosition;
	}
	
	public static List<Player> getAvailablePlayersByPositionAsListByECR(Position position) {
		List<Player> byPosition = position == null ? playersById.values().stream().filter(p -> p.getPosition() != null && p.getDraftingDetails().isAvailable()).collect(Collectors.toList()) : playersById.values().stream().filter(p -> position.equals(p.getPosition()) && p.getDraftingDetails().isAvailable()).collect(Collectors.toList());
		Collections.sort(byPosition, new PlayerRankComparator());
		return byPosition;
	}

	public static NFLTeam getTeam(String abbrev) {
		return teamsByAbbreviation.get(abbrev);
	}

	public static List<NflTeamMetadata> getNflTeamsSortedByAbbreviation() {
		return getTeamsSortedByAbbreviation().stream().map(t -> t.getTeam()).collect(Collectors.toList());
	}
	
	public static void setCurrentPlayerValue(int pickNum) {
		for (Player p : playersById.values()) {
			int value = pickNum - Integer.valueOf(p.getRankMetadata().getAdp());
			p.getDraftingDetails().setCurrentPlayerValue(value);
			p.getDraftingDetails().setCurrentPlayerValueBadgeClass(getValueBadgeClass(value));
		}
	}
	
	private static String getValueBadgeClass(int value) {
		if (value <= -40) { return "neg-40";
		} else if (value <= -20) { return "neg-20";
		} else if (value <= -10) { return "neg-10";
		} else if (value <= -5) { return "neg-5";
		} else if (value <= -2) { return "neg-2";
		} else if (value <= 2) { return "even";
		} else if (value <= 5) { return "pos-2";
		} else if (value <= 10) { return "pos-5";
		} else if (value <= 20) { return "pos-10";
		} else if (value <= 40) { return "pos-20";
		} else { return "pos-40";
		}
	}

	// public static static List<Player> getAllPickedPlayersList(){
	// List<Player> allPicked = new ArrayList<Player>();
	// for (Player player : players.values()){
	// if (!player.isAvailable()){
	// allPicked.add(player);
	// }
	// }
	// Collections.sort(allPicked, new PlayerADPComparator());
	// return allPicked;
	// }

}
