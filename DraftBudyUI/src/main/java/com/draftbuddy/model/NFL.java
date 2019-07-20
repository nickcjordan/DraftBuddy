package com.draftbuddy.model;

import static com.draftbuddy.constants.CSVFieldMapping.PLAYER_NAME;
import static com.draftbuddy.constants.CSVFieldMapping.TEAM_NAME;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.StringUtils;

import com.draftbuddy.Log;
import com.draftbuddy.builder.NFLBuilder;
import com.draftbuddy.comparator.AlphabetizedTeamComparator;
import com.draftbuddy.comparator.PlayerADPComparator;
import com.draftbuddy.comparator.PlayerRankComparator;
import com.draftbuddy.constants.Position;

public class NFL {

	static HashMap<String, Player> players;
	static HashMap<Integer, Player> playersById;
	static HashMap<String, Team> teams;
	static HashMap<Integer, Team> teamsById;
	
	static {
		initNFL();
	}
	
	public static void initNFL() {
		NFLBuilder builder = new NFLBuilder();
		players = builder.getPlayers();
		playersById = builder.getPlayersById();
		teams = builder.getTeams();
		teamsById = builder.getTeamsById();
		try {
			builder.addNotesToPlayers();
			builder.addTagsToPlayers();
			builder.addOLineRankingsToPlayers();
			builder.addTargetsToPlayers();
			builder.addPictureLinksToPlayers();
			builder.setPlayersToTarget();
			builder.setProjections();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Player> getPlayerMap(){
		return players;
	}
	
	public static  HashMap<String, Team> getTeamMap(){
		return teams;
	}
	
	public static List<Team> getTeamList(){
		ArrayList<Team> teamList = new ArrayList<Team>();
		for (Team team : teamsById.values()) {
			teamList.add(team);
		}
		Collections.sort(teamList, new AlphabetizedTeamComparator());
		return teamList;
	}
	
	public static Team getTeam(int id) {
		return teamsById.get(id);
	}
	
	public Team getTeam(String name) {
		return teams.get(name);
	}
	
	public static List<Player> getAvailablePlayersByPositionAsList(Position position){
		List<Player> byPosition = new ArrayList<Player>();
		for (Player player : players.values()){
			if (player.getPosition().equals(position) && player.isAvailable()){
				byPosition.add(player);
			}
		}
		Collections.sort(byPosition, new PlayerADPComparator());
		return byPosition;
	}
	
	private static List<Player> getAllAvailablePlayers(Comparator<Player> comparator){
		List<Player> allAvailable = new ArrayList<Player>();
		for (Player player : players.values()){
			if (player.isAvailable()){
				allAvailable.add(player);
			}
		}
		Collections.sort(allAvailable, comparator);
		return allAvailable;
	}
	
	public static List<Player> getAllAvailablePlayersByADP(){
		return getAllAvailablePlayers(new PlayerADPComparator());
	}
	
	public static List<Player> getAllAvailablePlayersByRank(){
		return getAllAvailablePlayers(new PlayerRankComparator());
	}
	
	public static List<Player> getAllPickedPlayersList(){
		List<Player> allPicked = new ArrayList<Player>();
		for (Player player : players.values()){
			if (!player.isAvailable()){
				allPicked.add(player);
			}
		}
		Collections.sort(allPicked, new PlayerADPComparator());
		return allPicked;
	}
	
	public static Player getPlayer(List<String> split) {
		return (players.get(split.get(1)) == null) ? players.get(extractTeamName(split)) : players.get(split.get(1));
	}
	
	private static String extractTeamName(List<String> split) {
		String name = split.get(TEAM_NAME.getIndex());
		if (StringUtils.isEmpty(name) || name.equals("NA") || name.equals("-")) {
			String[] splitText = split.get(PLAYER_NAME.getIndex()).split(" ");
			split.set(TEAM_NAME.getIndex(), splitText[splitText.length - 1]); // "Dallas Cowboys" --> split.set("Cowboys")
		}
		return split.get(TEAM_NAME.getIndex());
	}
	
	public static Player getPlayer(int id) {
		return playersById.get(id);
	}

	public static Player getPlayer(String p) {
		Player player = players.get(p);
		if (player == null) {
			throw new RuntimeException("Player " + p + " not found in players map");
		}
		return player;
	}

	public static void resetPlayers() {
		for(Player player : getPlayerMap().values()) {
			resetPlayer(player);
		}
	}

	private static void resetPlayer(Player player) {
		player.setAvailable(true);
		player.setRoundDrafted(0);
	}
	
}
