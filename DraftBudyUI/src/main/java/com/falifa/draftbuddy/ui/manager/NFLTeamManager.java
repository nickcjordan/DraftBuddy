package com.falifa.draftbuddy.ui.manager;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_NFL_TEAMS_JSON_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_PLAYERS_JSON_FILE_PATH;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.falifa.draftbuddy.api.model.PlayerTO;
import com.falifa.draftbuddy.ui.api.ApiDataDelegate;
import com.falifa.draftbuddy.ui.api.PlayerNameMatcher;
import com.falifa.draftbuddy.ui.comparator.PlayerADPComparator;
import com.falifa.draftbuddy.ui.comparator.PlayerRankComparator;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.data.PlayerPopulator;
import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.MasterTeamTO;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NFLTeamManager {

	private static final Logger log = LoggerFactory.getLogger(NFLTeamManager.class);

	private Map<String, Player> playersById;
	private Map<String, String> playerNameToIdMap;
	private Map<String, PlayerTO> playerStatsTOMap;
	private Map<String, NFLTeam> teamsByAbbreviation;
	
	private ArrayList<String> playerNameToIdMapFailedToFind;
	
	@Autowired
	private DraftState draftState;

	@Autowired
	private PlayerPopulator playerPopulator;
	
	@Autowired
	private ApiDataDelegate dataDelegate;
	
	@Autowired
	private PlayerNameMatcher nameMatcher;


	public NFLTeamManager() {
		this.playersById = new HashMap<String, Player>();
		this.teamsByAbbreviation = new HashMap<String, NFLTeam>();
		this.playerStatsTOMap = new HashMap<String, PlayerTO>();
		this.playerNameToIdMap = new HashMap<String, String>();
		this.playerNameToIdMapFailedToFind = new ArrayList<String>();
	}

	public void initializeNFL() {
		this.playerNameToIdMapFailedToFind = new ArrayList<String>();
		try {
			populateMasterPlayersFromJsonFile();
			populateMasterTeamsFromJsonFile();
			populatePlayerStatsFieldsPriorToApi();
			populatePlayerStatsFromApi();
			populateManuallySetFieldValuesAndPutOnTeam();
			populateNflTeamSpecificFieldValues();
			log.info("NFL initialized with {} teams and {} players", teamsByAbbreviation.size(), playersById.size());
		} catch (Exception e) {
			log.error("ERROR initializing master players from json", e);
		}
	}

	private void populatePlayerStatsFieldsPriorToApi() {
		if (!CollectionUtils.isEmpty(playersById) && !CollectionUtils.isEmpty(teamsByAbbreviation)) {
			for (Player player : playersById.values()) {
				if (player.getPlayerName() != null) {
					playerNameToIdMap.put(nameMatcher.filter(player.getPlayerName()), player.getFantasyProsId());
					nameMatcher.addAlternateNames(player.getPlayerName(), player.getFantasyProsId());
				}
			}
		}
	}

	private void populatePlayerStatsFromApi() {
		int count = 0;
		playerStatsTOMap = Optional.ofNullable(dataDelegate.getPlayersMapFromApi()).orElse(playerStatsTOMap);
		for (Entry<String, PlayerTO> entry : playerStatsTOMap.entrySet()) {
			if (entry.getKey() == null) {
				log.error("NULL name found in playerStatsTOMap :: id={}", entry.getValue().getPlayerId());
			} else {
				String id = null;
				id = (playerNameToIdMap.containsKey(nameMatcher.filter(entry.getKey()))) ? playerNameToIdMap.get(nameMatcher.filter(entry.getKey())) : nameMatcher.checkForClosestMatch(entry.getKey());
				if (playersById.containsKey(id)) {
					Player p = playersById.get(id);
					playerPopulator.populatePlayerWithStatsFromTO(p, entry.getValue());
					count++;
				} else {
					log.debug("ERROR playersById did not contain key={}", id);
					playerNameToIdMapFailedToFind.add(entry.getKey());
				}
			}
		}
		if (playerNameToIdMapFailedToFind.size() > 0) {
			log.error("{} players from API response were not found in existing data from FantasyPros :: [{}]", playerNameToIdMapFailedToFind.size(), playerNameToIdMapFailedToFind.stream().collect(Collectors.joining(", ")));
		}
		log.info("Successfully populated stats of {} players out of the {} found in the API response", count, playerStatsTOMap.size());
	}

	private void populateManuallySetFieldValuesAndPutOnTeam() {
		if (!CollectionUtils.isEmpty(playersById) && !CollectionUtils.isEmpty(teamsByAbbreviation)) {
			for (Player player : playersById.values()) {
				populateRemainingFieldsForPlayer(player);
				NFLTeam team = teamsByAbbreviation.get(player.getTeam().getAbbreviation());
				if (team != null) {
					team.addPlayer(player);
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
						for (Player backup : positionPlayers.subList(1, positionPlayers.size()-1)) {
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
		playerPopulator.populateMapStats(player);
		playerPopulator.populatePlayerProjectedTotalsFields(player);
		playerPopulator.populatePlayerPriorTotalsFields(player);
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

	public Map<String, Player> getPlayersById() {
		return playersById;
	}

	public void setPlayersById(Map<String, Player> playersById) {
		this.playersById = playersById;
	}

	public Map<String, NFLTeam> getTeamsByAbbreviation() {
		return teamsByAbbreviation;
	}

	public void setTeamsByAbbreviation(Map<String, NFLTeam> teamsByAbbreviation) {
		this.teamsByAbbreviation = teamsByAbbreviation;
	}

	public Player getPlayerById(String fantasyProsId) {
		return playersById.get(fantasyProsId);
	}

	private List<Player> getAllAvailablePlayers(Comparator<Player> comparator) {
		List<Player> allAvailable = new ArrayList<Player>();
		for (Player player : playersById.values()) {
			if (player.getDraftingDetails().isAvailable()) {
				allAvailable.add(player);
			}
		}
		Collections.sort(allAvailable, comparator);
		return allAvailable;
	}

	public List<Player> getAllAvailablePlayersByADP() {
		return getAllAvailablePlayers(new PlayerADPComparator());
	}

	public List<Player> getAllAvailablePlayersByRank() {
		return getAllAvailablePlayers(new PlayerRankComparator());
	}

	public List<Player> getAvailablePlayersByPositionAsList(Position position) {
		List<Player> byPosition = new ArrayList<Player>();
		for (Player player : playersById.values()) {
			if (player != null) {
				if (position.equals(player.getPosition()) && player.getDraftingDetails().isAvailable()) {
					byPosition.add(player);
				}
			}
		}
		Collections.sort(byPosition, new PlayerADPComparator());
		return byPosition;
	}
	
	public NFLTeam getTeam(String abbrev) {
		return teamsByAbbreviation.get(abbrev);
	}

	public void setCurrentPlayerValue() {
		for (Player p : playersById.values()) {
			p.getDraftingDetails().setCurrentPlayerValue(draftState.getPickNumber() - Integer.valueOf(p.getRankMetadata().getAdp()));
		}
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
