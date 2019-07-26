package com.falifa.draftbuddy.ui.manager;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_NFL_TEAMS_JSON_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_PLAYERS_JSON_FILE_PATH;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.comparator.PlayerADPComparator;
import com.falifa.draftbuddy.ui.comparator.PlayerRankComparator;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.MasterTeamTO;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NFLTeamManager {

	private static final Logger log = LoggerFactory.getLogger(NFLTeamManager.class);

	private Map<String, Player> playersById;
	private Map<String, NFLTeam> teamsByAbbreviation;

	public NFLTeamManager() {
		initializeNFL();
	}

	public void initializeNFL() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			MasterPlayersTO playersTO = mapper.readValue(new File(MASTER_PLAYERS_JSON_FILE_PATH), MasterPlayersTO.class);
			playersById = playersTO.getPlayers();

			MasterTeamTO teamsTO = mapper.readValue(new File(MASTER_NFL_TEAMS_JSON_FILE_PATH), MasterTeamTO.class);
			teamsByAbbreviation = teamsTO.getTeams();

			for (Player player : playersById.values()) {
				NFLTeam team = teamsByAbbreviation.get(player.getTeam().getAbbreviation());
				if (team != null) {
					team.addPlayer(player);
				}
			}

		} catch (Exception e) {
			log.error("ERROR initializing master players from json", e);
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
			if (player.getPosition().equals(position) && player.getDraftingDetails().isAvailable()) {
				byPosition.add(player);
			}
		}
		Collections.sort(byPosition, new PlayerADPComparator());
		return byPosition;
	}
	
	public NFLTeam getTeam(String abbrev) {
		return teamsByAbbreviation.get(abbrev);
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
