package com.falifa.draftbuddy.ui.builder;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.DST_PROJECTIONS_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.ECR_FANTASYPROS_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.K_PROJECTIONS_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.NFL_TEAM_NAMES_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.OLINERANK_TO_PLAYER_MAPPING_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYERNOTES_CUSTOM_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYERNOTES_EXPERTS_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYERS_TO_TARGET_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYER_NOTES_HTML_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYER_PICTURES_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PREVIOUS_SEASON_TARGET_SHARE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.QB_PROJECTIONS_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.RB_PROJECTIONS_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TE_PROJECTIONS_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.WR_PROJECTIONS_PATH;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.constants.CSVFieldMapping;
import com.falifa.draftbuddy.ui.controller.BaseController;
import com.falifa.draftbuddy.ui.io.DataFileReader;
import com.falifa.draftbuddy.ui.io.HTMLParser;
import com.falifa.draftbuddy.ui.model.NFL;
import com.falifa.draftbuddy.ui.model.Player;
import com.falifa.draftbuddy.ui.model.Team;
import com.jaunt.Element;
import com.jaunt.UserAgent;

public class NFLBuilder {

	HashMap<String, Player> players;
	HashMap<Integer, Player> playersById;
	HashMap<String, Team> teams;
	HashMap<Integer, Team> teamsById;
	private DataFileReader dataReader;
	HTMLParser parser;

	public NFLBuilder() {
		players = new HashMap<String, Player>();
		teams = new HashMap<String, Team>();
		playersById = new HashMap<Integer, Player>();
		teamsById = new HashMap<Integer, Team>();
		dataReader = new DataFileReader();
		parser = new HTMLParser();
		buildTeams();
	}

	private void buildTeams() {
		try {
			addTeamsToTeamLists();
			addPlayersToPlayerLists();
		} catch (FileNotFoundException e) {
			Log.err("NFL could not be built.");
		}
	}
	
	public HashMap<String, Player> getPlayers() {
		return players;
	}
	public HashMap<Integer, Player> getPlayersById() {
		return playersById;
	}
	public HashMap<String, Team> getTeams() {
		return teams;
	}
	public HashMap<Integer, Team> getTeamsById() {
		return teamsById;
	}

//	private void addPlayersToPlayerLists() throws FileNotFoundException {
//		for (List<String> split : dataReader.getSplitLinesFromFile(ECR_FANTASYPROS_PATH, true, ",")) {
//			if (split.get(CSVFieldMapping.POS.getIndex()).contains("TOL")) { // TOL == Total Offensive Line?
//				continue;
//			}
//			try {
//				Player player = PlayerBuilder.buildPlayer(split);
//				players.put(player.getPlayerName(), player);
//				TeamBuilder.addPlayerToTeam(player, teams.get(player.getTeamName()));
//				playersById.put(player.getId(), player);
//			} catch (Exception e) {
//				Log.err("ERROR adding player to players list: " + split.get(CSVFieldMapping.PLAYER_NAME.getIndex()) + " :: " + e.getMessage());
//			}
//		}
//	}
	
	private void addPlayersToPlayerLists() throws FileNotFoundException {
		for (Player player : parser.getPlayersFromHtml()) {
//			Player player = PlayerBuilder.buildPlayer(split);
			players.put(player.getPlayerName(), player);
			TeamBuilder.addPlayerToTeam(player, teams.get(player.getTeamName()));
			playersById.put(player.getId(), player);
		}
	}

	private void addTeamsToTeamLists() throws FileNotFoundException {
		int id = 1;
		for (List<String> split : dataReader.getSplitLinesFromFile(NFL_TEAM_NAMES_PATH, true, ",")) {
			Team team = TeamBuilder.buildTeamFromInput(id++, split);
//			if (team.getAbbrev().equals("FA")) {
//				System.out.println();
//			}
			teams.put(team.getName(), team);
			teams.put(team.getAbbrev(), team);
			teamsById.put(team.getId(), team);
		}
	}

	public void addNotesToPlayers() throws FileNotFoundException {
		// https://www.fantasypros.com/nfl/notes/draft-overall.php?type=PPR
		parser.addNotesFromHtml();
		
		for (List<String> split : dataReader.getSplitLinesFromFile(PLAYERNOTES_EXPERTS_PATH, true, ",")) {
			try {
				PlayerBuilder.addAdditionalNote(split);
			} catch (Exception e) {
				Log.err("Could not add notes: " + e.getMessage());
			}
		}
		for (List<String> split : dataReader.getSplitLinesFromFile(PLAYERNOTES_CUSTOM_PATH, true, ",")) {
			try {
				PlayerBuilder.addAdditionalNote(split);
			} catch (Exception e) {
				Log.err("Could not add notes: " + e.getMessage());
			}
		}
	}

	public void addTagsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(TAGS_CUSTOM_PATH, true, ",")) {
			try {
				PlayerBuilder.addTag(split);
			} catch (Exception e) {
				Log.err("Could not set tags for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void addOLineRankingsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(OLINERANK_TO_PLAYER_MAPPING_PATH, true, ",")) {
			try {
				PlayerBuilder.addOLineRankings(split);
			} catch (Exception e) {
				Log.err("Could not set o line rank for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void addTargetsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(PREVIOUS_SEASON_TARGET_SHARE_PATH, true, ",")) {
			try {
				PlayerBuilder.addPlayerTargets(split);
			} catch (Exception e) {
				Log.err("Could not set targets for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void addPictureLinksToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(PLAYER_PICTURES_PATH, true, ",")) {
			try {
				PlayerBuilder.addPlayerPicLinks(split);
			} catch (Exception e) {
				Log.err("Could not set picture links for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void setPlayersToTarget() throws FileNotFoundException {
		for (String name : dataReader.getLinesFromFile(PLAYERS_TO_TARGET_PATH)) {
			try {
				PlayerBuilder.setPlayerAsATarget(name);
			} catch (Exception e) {
				Log.err("Could not set player as a target: " + name + " :: " + e.getMessage());
			}
		}
	}
	
	public void setProjections() throws FileNotFoundException {
		setPlayerProjections(QB_PROJECTIONS_PATH); //"Player","Team","ATT","CMP","YDS","TDS","INTS","ATT","YDS","TDS","FL","FPTS"
		setPlayerProjections(RB_PROJECTIONS_PATH); //"Player","Team","ATT","YDS","TDS","REC","YDS","TDS","FL","FPTS"
		setPlayerProjections(WR_PROJECTIONS_PATH); //"Player","Team","REC","YDS","TDS","ATT","YDS","TDS","FL","FPTS"
		setPlayerProjections(TE_PROJECTIONS_PATH); //"Player","Team","REC","YDS","TDS","FL","FPTS"
		setPlayerProjections(K_PROJECTIONS_PATH); //"Player","Team","FG","FGA","XPT","FPTS"
		setPlayerProjections(DST_PROJECTIONS_PATH); //"Player","Team","SACK","INT","FR","FF","TD","SAFETY","PA","YDS_AGN","FPTS"
	}

	private void setPlayerProjections(String filePath) throws FileNotFoundException {
		List<List<String>> splitLines = dataReader.getSplitLinesFromFile(filePath, false, "\",\"");
		List<String> headers = splitLines.get(0);
		for (List<String> split : splitLines.subList(1, splitLines.size())) {
			try {
				PlayerBuilder.setPlayerProjections(headers, split);
			} catch (Exception e) {
				Log.err("Could not set  projections: " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public static void populateCurrentPlayerValue() {
		for (Player p : NFL.getPlayerMap().values()) {
			p.setCurrentPlayerValue(BaseController.pickNumber - Integer.valueOf(p.getAdp()));
		}
			
	}

}
