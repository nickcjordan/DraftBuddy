package com.falifa.draftbuddy.ui.data;

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

import com.falifa.draftbuddy.ui.api.PlayerNameMatcher;
import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.drafting.DraftManager;
import com.falifa.draftbuddy.ui.drafting.sort.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.drafting.sort.AlphabetizedTeamComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerADPComparator;
import com.falifa.draftbuddy.ui.drafting.sort.PlayerRankComparator;
import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.MasterTeamTO;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NFLTeamManager {

	private static final Logger log = LoggerFactory.getLogger(NFLTeamManager.class);

	private static final String PLUS_SIGN = "&#43;";
	private static final CharSequence MINUS_SIGN = "&#45;";

	private Map<String, Player> playersById;
	private Map<String, String> playerNameToIdMap;
	private Map<String, NFLTeam> teamsByAbbreviation;

	@Autowired
	private DraftState draftState;

	@Autowired
	private PlayerNameMatcher nameMatcher;

	@Autowired
	private StrategyFileHandler strategyHandler;
	
	@Autowired
	private ModelUpdater modelUpdater;

	public NFLTeamManager() {
		this.playersById = new HashMap<String, Player>();
		this.teamsByAbbreviation = new HashMap<String, NFLTeam>();
		this.playerNameToIdMap = new HashMap<String, String>();
	}

	public void initializeNFL() {
		try {
			populateMasterPlayersFromJsonFile();
			populateMasterTeamsFromJsonFile();
			populatePlayerNameMap();
			populateManuallySetFieldValuesAndPutOnTeam();
			populateNflTeamSpecificFieldValues();
			populatePlayersWithTags();
			setCurrentPlayerValue();
			updateDraftStrategyDataFromFile();
			modelUpdater.clearFiltersAndSorts();
			log.info("NFL initialized with {} teams and {} players", teamsByAbbreviation.size(), playersById.size());
		} catch (Exception e) {
			log.error("ERROR initializing master players from json", e);
		}
	}

	private void populatePlayerNameMap() {
		if (!CollectionUtils.isEmpty(playersById) && !CollectionUtils.isEmpty(teamsByAbbreviation)) {
			for (Player player : playersById.values()) {
				if (player.getPlayerName() != null) {
					playerNameToIdMap.put(nameMatcher.filter(player.getPlayerName()), player.getFantasyProsId());
					nameMatcher.addAlternateNames(player.getPlayerName(), player.getFantasyProsId());
				}
			}
		}
	}

	private void populateManuallySetFieldValuesAndPutOnTeam() {
		if (!CollectionUtils.isEmpty(playersById) && !CollectionUtils.isEmpty(teamsByAbbreviation)) {
			for (Player player : playersById.values()) {
				populateRemainingFieldsForPlayer(player);
				if (player.getTeam() != null) {
					NFLTeam team = teamsByAbbreviation.get(player.getTeam().getAbbreviation());
					if (team != null) {
						team.addPlayer(player);
					}
				}
			}
		}
	}

	private void populateNflTeamSpecificFieldValues() {
		if (!CollectionUtils.isEmpty(teamsByAbbreviation)) {
			for (NFLTeam team : teamsByAbbreviation.values()) {
				for (Position pos : Position.values()) {
					try {
						List<Player> positionPlayers = team.getPlayersByPosition(pos);
						Collections.sort(positionPlayers, new PlayerADPComparator());
						Player starter = positionPlayers.get(0);
						for (Player backup : positionPlayers.subList(1, positionPlayers.size() - 1)) {
							starter.getDraftingDetails().addBackup(backup);
						}
					} catch (Exception e) {
						log.debug("Could not populate backups for team {} and position {}", team.getTeam().getFullName(), pos.getAbbrev());
					}
				}
			}
		}
	}

	private void populateRemainingFieldsForPlayer(Player player) {
		player.getDraftingDetails().setAvailable(true);
		if (player.getRankMetadata().getOverallRank() == null) {
			player.getRankMetadata().setOverallRank(String.valueOf(playersById.size()));
		}
		if (player.getRankMetadata().getVsAdp() != null) {
			String vs = player.getRankMetadata().getVsAdp();
			Integer vsVal = null;
			if (StringUtils.isNotEmpty(vs)) {
				if (vs.contains(PLUS_SIGN)) {
					vsVal = Integer.valueOf(vs.replace(PLUS_SIGN, "").replace(".0", "")).intValue();
				} else if (vs.contains(MINUS_SIGN)) {
					vsVal = -Integer.valueOf(vs.replace(MINUS_SIGN, "").replace(".0", "")).intValue();
				} else {
					vsVal = Integer.valueOf(vs.replace(".0", "")).intValue();
				}
				if (vsVal != null) {
					player.getDraftingDetails().setVsValueBadgeClass(getValueBadgeClass(vsVal));
					player.getRankMetadata().setVsAdp(String.valueOf(vsVal));
				}
			}
		}
	}

	private void populateMasterTeamsFromJsonFile() {
		try {
			teamsByAbbreviation = new ObjectMapper().readValue(new File(MASTER_NFL_TEAMS_JSON_FILE_PATH), MasterTeamTO.class).getTeams();
		} catch (FileNotFoundException ex) {
			log.error("Did not find master teams json at path={}", MASTER_NFL_TEAMS_JSON_FILE_PATH);
		} catch (Exception e) {
			log.error("ERROR extracting master teams json from file at path={}", MASTER_NFL_TEAMS_JSON_FILE_PATH);
		}
	}

	private void populateMasterPlayersFromJsonFile() {
		try {
			this.playersById = new ObjectMapper().readValue(new File(MASTER_PLAYERS_JSON_FILE_PATH), MasterPlayersTO.class).getPlayers();
		} catch (FileNotFoundException ex) {
			log.error("Did not find master players json at path={}", MASTER_PLAYERS_JSON_FILE_PATH);
		} catch (Exception e) {
			log.error("ERROR extracting master players json from file at path={}", MASTER_PLAYERS_JSON_FILE_PATH);
		}
	}

	public void updateDraftStrategyDataFromFile() {
		Map<String, RoundSpecificStrategy> strategy = new HashMap<String, RoundSpecificStrategy>();
		try {
			for (List<String> split : strategyHandler.getSplitLinesFromFile(DRAFT_STRATEGY_BY_ROUND_FILE_PATH, true)) {
				if (split.size() > 1) {
					strategy.put(split.get(0), strategyHandler.buildRoundSpecificStrategy(split));
				}
			}
		} catch (Exception e) {
			log.error("ERROR populating players with tags", e);
		}
		draftState.setStrategyByRound(strategy);
	}

	private void populatePlayersWithTags() {
		try {
			for (List<String> split : strategyHandler.getSplitLinesFromFile(PLAYER_TAGS_FILE_PATH, true)) {
				if (split.size() > 1) {
					Player p = getPlayerFromName(split.get(0));
					if (p != null) {
						strategyHandler.addTagsToPlayer(p, split.get(1));
					} else {
						log.error("Could not find player for name={}", split.get(0));
					}
				}
			}
		} catch (Exception e) {
			log.error("ERROR populating players with tags", e);
		}
		cleanupTags();
	}

	private void cleanupTags() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			for (List<String> split : strategyHandler.getSplitLinesFromFile(TAGS_CUSTOM_PATH, true)) {
				Player p = getPlayerFromName(split.get(0));
				if (p != null) {
					sorted.add(p);
				} else {
					log.error("ERROR cleaning up players :: Player not found = {}", split.get(0));
				}
			}
			strategyHandler.updateTagFileWithCleanedUpResults(sorted);
		} catch (Exception e) {
			log.error("ERROR cleaning up tags", e);
		}
	}

	public Map<String, Player> getPlayersById() {
		return playersById;
	}

	public void setPlayersById(Map<String, Player> playersById) {
		this.playersById = playersById;
	}

	public Map<String, NFLTeam> getTeamsByAbbreviation() {
		return teamsByAbbreviation;
	}

	public List<NFLTeam> getTeamsSortedByAbbreviation() {
		List<NFLTeam> teamsCollection = new ArrayList<NFLTeam>(teamsByAbbreviation.values());
		Collections.sort(teamsCollection, new AlphabetizedTeamComparator());
		return teamsCollection;
	}

	public void setTeamsByAbbreviation(Map<String, NFLTeam> teamsByAbbreviation) {
		this.teamsByAbbreviation = teamsByAbbreviation;
	}

	public Player getPlayerFromName(String playerName) {
		String id = nameMatcher.findIdForClosestMatchingName(playerName);
		if (id != null) {
			log.debug("Getting player from name :: Found ID={} from player name={}", id, playerName);
			if (playersById.containsKey(id)) {
				return playersById.get(id);
			}
		}
		return null;
	}

	public Player getPlayerById(String fantasyProsId) {
		return playersById.get(fantasyProsId);
	}

	private List<Player> getAllPlayers(Comparator<Player> comparator, boolean isAvailable) {
		List<Player> allAvailable = playersById.values().stream().filter(p -> isAvailable == p.getDraftingDetails().isAvailable() && p.getPosition() != null).collect(Collectors.toList());
		Collections.sort(allAvailable, comparator);
		return allAvailable;
	}
	
	private List<Player> getAllPlayers(Comparator<Player> comparator) {
		List<Player> allAvailable = new ArrayList<Player>(playersById.values().stream().filter(p -> p.getPosition() != null).collect(Collectors.toList()));
		Collections.sort(allAvailable, comparator);
		return allAvailable;
	}

	public List<Player> getAllAvailablePlayersByADP() {
		return getAllPlayers(new PlayerADPComparator(), true);
	}

	public List<Player> getAllAvailablePlayersByRank() {
		return getAllPlayers(new PlayerRankComparator(), true);
	}
	
	public List<Player> getAllUnavailablePlayersByADP() {
		return getAllPlayers(new PlayerADPComparator(), false);
	}
	
	public List<Player> getAllPlayersByADP() {
		return getAllPlayers(new PlayerADPComparator());
	}
	
	public Map<String, String> getPlayerNameToIdMap() {
		return playerNameToIdMap;
	}

	public List<Player> getAvailablePlayersByPositionAsList(Position position) {
		List<Player> byPosition = position == null ? playersById.values().stream().filter(p -> p.getPosition() != null && p.getDraftingDetails().isAvailable()).collect(Collectors.toList()) : playersById.values().stream().filter(p -> position.equals(p.getPosition()) && p.getDraftingDetails().isAvailable()).collect(Collectors.toList());
		Collections.sort(byPosition, new PlayerADPComparator());
		return byPosition;
	}

	public NFLTeam getTeam(String abbrev) {
		return teamsByAbbreviation.get(abbrev);
	}

	public void setCurrentPlayerValue() {
		for (Player p : playersById.values()) {
			int value = draftState.getPickNumber() - Integer.valueOf(p.getRankMetadata().getAdp());
			p.getDraftingDetails().setCurrentPlayerValue(value);
			p.getDraftingDetails().setCurrentPlayerValueBadgeClass(getValueBadgeClass(value));
		}
	}

	private String getValueBadgeClass(int value) {
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

	public List<NflTeamMetadata> getNflTeamsSortedByAbbreviation() {
		return getTeamsSortedByAbbreviation().stream().map(t -> t.getTeam()).collect(Collectors.toList());
	}

	// public static List<Player> getAllPickedPlayersList(){
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
