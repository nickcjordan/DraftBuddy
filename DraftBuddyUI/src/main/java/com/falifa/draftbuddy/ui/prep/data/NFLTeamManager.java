package com.falifa.draftbuddy.ui.prep.data;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_NFL_TEAMS_JSON_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_PLAYERS_JSON_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_SLEEPER_PLAYERS_JSON_FILE_PATH;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedTeamComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerADPComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerRankComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerSleeperADPComparator;
import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.MasterTeamTO;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.SleeperPlayerData;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.prep.api.PlayerNameMatcher;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NFLTeamManager {

	private static final Logger log = LoggerFactory.getLogger(NFLTeamManager.class);

	private static Map<String, NFLTeam> teamsByAbbreviation;
	private static Map<String, Player> playersById;
	private static Map<String, SleeperPlayerData> sleeperPlayersById;
	
	
	
	private static PlayerNameMatcher nameMatcher;

	 static {
		playersById = new HashMap<String, Player>();
		teamsByAbbreviation = new HashMap<String, NFLTeam>();
		sleeperPlayersById = new HashMap<String, SleeperPlayerData>();
		nameMatcher = new PlayerNameMatcher();
	}

	public static void initializeNFL() {
		try {
			populateMasterPlayersFromJsonFile();
			populateMasterTeamsFromJsonFile();
			populateMasterSleeperPlayersFromJsonFile();
			
			updatePlayersWithSleeperData();
			log.info("NFL initialized with {} teams and {} players", teamsByAbbreviation.size(), playersById.size());
		} catch (Exception e) {
			log.error("ERROR initializing master players from json", e);
		}
	}

	private static void updatePlayersWithSleeperData() {
		for (Player p : playersById.values()) {
			if (p.getPosition().equals(Position.DEFENSE)) {
				nameMatcher.addNameMapping(p.getTeam().getAbbreviation(), p.getFantasyProsId());
				nameMatcher.addNameMapping(p.getTeam().getFullName(), p.getFantasyProsId());
			}
			nameMatcher.addAlternateNames(p.getPlayerName(), p.getFantasyProsId());
		}
		
		
		
		
		for (String key : sleeperPlayersById.keySet()) {
			SleeperPlayerData sleeperPlayer = sleeperPlayersById.get(key);
			List<String> fantasyPositions = Arrays.asList("QB", "WR", "RB", "TE", "DEF", "DST", "K");
			if (fantasyPositions.contains(sleeperPlayer.getDepthChartPosition())) {
				String id = nameMatcher.findIdForClosestMatchingName(sleeperPlayer.getFullName());
				Player player = playersById.get(id);
				if (player != null) {
					player.setSleeperData(sleeperPlayer);
					player.setSleeperId(key);
					sleeperPlayer.setFantasyProsId(player.getFantasyProsId());
				}
			}
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
	
	private static void populateMasterSleeperPlayersFromJsonFile() {
		try {
		    TypeReference<HashMap<String,SleeperPlayerData>> typeRef = new TypeReference<HashMap<String,SleeperPlayerData>>() {};
			sleeperPlayersById = new ObjectMapper().readValue(new File(MASTER_SLEEPER_PLAYERS_JSON_FILE_PATH), typeRef);
		} catch (FileNotFoundException ex) {
			log.error("Did not find master sleeper players json at path={}", MASTER_SLEEPER_PLAYERS_JSON_FILE_PATH);
		} catch (Exception e) {
			log.error("ERROR extracting master sleeper players json from file at path={}", MASTER_SLEEPER_PLAYERS_JSON_FILE_PATH);
			e.printStackTrace();
		}
	}
	
	public static Player getPlayerBySleeperId(String sleeperId) {
		SleeperPlayerData sleeperPlayer = sleeperPlayersById.get(sleeperId);
		if (sleeperPlayer != null) {
			String playerName = sleeperPlayer.getFullName();
			if (sleeperPlayer.getPosition().equals("DEF") || sleeperPlayer.getPosition().equals("DST")) {
				playerName = sleeperPlayer.getFirstName() + " " + sleeperPlayer.getLastName();
			}
			String id = NFLTeamManager.nameMatcher.findIdForClosestMatchingName(playerName);
			sleeperPlayer.setFantasyProsId(id);
			Player player = playersById.get(id);
			if (player != null) {
				player.setSleeperData(sleeperPlayer);
			} else {
				throw new RuntimeException("SLEEPER PICK PLAYER WAS NULL :: " + playerName);
			}
			return player;
		} else { return null; }
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
	
	public static List<Player> getAllAvailablePlayersBySleeperADP() {
		return getAllPlayers(new PlayerSleeperADPComparator(), true);
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
	
	public static List<Player> getAllPlayersBySleeperADP() {
		return getAllPlayers(new PlayerSleeperADPComparator());
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
