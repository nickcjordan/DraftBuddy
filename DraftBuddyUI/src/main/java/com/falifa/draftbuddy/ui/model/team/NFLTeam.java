package com.falifa.draftbuddy.ui.model.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.TeamSOSData;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NFLTeam {

	private String fantasyProsId;
	private NflTeamMetadata team;
	private Map<Position, List<Player>> playersByPosition;
	private List<Player> players;
	private NFLTeamSOSData sosData;
	public NflTrends trends;
	public NflTeamStats stats;

	public NflTeamStats getStats() {
		return stats;
	}

	public void setStats(NflTeamStats stats) {
		this.stats = stats;
	}

	public NflTrends getTrends() {
		return trends;
	}

	public void setTrends(NflTrends trends) {
		this.trends = trends;
	}

	public NFLTeamSOSData getSosData() {
		return sosData;
	}

	public void setSosData(NFLTeamSOSData sosData) {
		this.sosData = sosData;
	}

	public void setFantasyProsId(String fantasyProsId) {
		this.fantasyProsId = fantasyProsId;
	}

	public void setTeam(NflTeamMetadata team) {
		this.team = team;
	}

	public void setPlayersByPosition(Map<Position, List<Player>> playersByPosition) {
		this.playersByPosition = playersByPosition;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public NFLTeam() {
		// initFields();
	}

	public NFLTeam(String fantasyProsId, NflTeamMetadata team) {
		this.fantasyProsId = fantasyProsId;
		this.team = team;
		initFields();
	}

	private void initFields() {
		playersByPosition = new HashMap<Position, List<Player>>();
		playersByPosition.put(Position.QUARTERBACK, new ArrayList<Player>());
		playersByPosition.put(Position.RUNNINGBACK, new ArrayList<Player>());
		playersByPosition.put(Position.WIDERECEIVER, new ArrayList<Player>());
		playersByPosition.put(Position.TIGHTEND, new ArrayList<Player>());
		playersByPosition.put(Position.KICKER, new ArrayList<Player>());
		playersByPosition.put(Position.DEFENSE, new ArrayList<Player>());
		this.players = new ArrayList<Player>();
		this.stats = new NflTeamStats();
		this.trends = new NflTrends();
	}

	public List<Player> getPlayers() {
		return players;
	}

	@JsonIgnore
	public List<Player> getPlayersByPosition(Position p) {
		return playersByPosition.get(p);
	}

	@JsonIgnore
	public List<Player> getPlayersByPosition(String pos) {
		return playersByPosition.get(Position.get(pos.toUpperCase()));
	}

	public Map<Position, List<Player>> getPlayersByPosition() {
		return playersByPosition;
	}

	public String getFantasyProsId() {
		return fantasyProsId;
	}

	public NflTeamMetadata getTeam() {
		return team;
	}

	public void addPlayer(Player player) {
		List<Player> positionPlayers = playersByPosition.get(player.getPosition());
		if (positionPlayers != null) {
			positionPlayers.add(player);
		}
		players.add(player);
	}
}
