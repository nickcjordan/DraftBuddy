package com.falifa.draftbuddy.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.constants.NflTeam;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;

public class NFLTeam {

	private String fantasyProsId;
	private NflTeam team;
	private Map<Position, List<Player>> playersByPosition;
	private List<Player> players;
	
	public NFLTeam(String fantasyProsId, NflTeam team) {
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
	
	public List<Player> getAllPlayers() {
		return players;
	}
	
	public List<Player> getPlayersByPosition(Position p) {
		return playersByPosition.get(p);
	}

	public String getFantasyProsId() {
		return fantasyProsId;
	}

	public NflTeam getTeam() {
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
