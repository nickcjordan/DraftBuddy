package com.falifa.draftbuddy.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NFLTeam {

	private String fantasyProsId;
	private NflTeamMetadata team;
	private Map<Position, List<Player>> playersByPosition;
	private List<Player> players;
	
	public NFLTeam() { initFields(); }
	
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
