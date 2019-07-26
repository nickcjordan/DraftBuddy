package com.falifa.draftbuddy.ui.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.NFL;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.Team;
import com.falifa.draftbuddy.ui.model.player.Player;

public class TeamBuilder {
	
	public static HashMap<String, String> mascotToTeamNameMapping = new HashMap<String, String>();
	public static HashMap<String, String> teamNameToMascotMapping = new HashMap<String, String>();

	static {
		setNameMappings();
	}
	
//	public static Team buildTeamFromInput(int id, List<String> split) {
//		return new NFLTeam(split.get(0), split.get(1), split.get(2), id);
//	}
//	
//	public static String getProperTeamName(String teamName) {
//		String name = teamName.length() > 3 ? mascotToTeamNameMapping.get(teamName) : teamName;
//		return name == null ? teamName : name;
//	}
//
//	public static String getMascotByTeamName(String teamName) {
//		return teamNameToMascotMapping.get(teamName);
//	}
//	
//	public static String getTeamNameByMascot(String mascot) {
//		return mascotToTeamNameMapping.get(mascot);
//	}
	
	private static void setNameMappings() {
		mascotToTeamNameMapping.put("Dolphins", "MIA");
		mascotToTeamNameMapping.put("Raiders", "OAK");
		mascotToTeamNameMapping.put("Cowboys", "DAL");
		mascotToTeamNameMapping.put("Packers", "GB");
		mascotToTeamNameMapping.put("Texans", "HOU");
		mascotToTeamNameMapping.put("Broncos", "DEN");
		mascotToTeamNameMapping.put("Saints", "NO");
		mascotToTeamNameMapping.put("Titans", "TEN");
		mascotToTeamNameMapping.put("Lions", "DET");
		mascotToTeamNameMapping.put("Steelers", "PIT");
		mascotToTeamNameMapping.put("Bears", "CHI");
		mascotToTeamNameMapping.put("Vikings", "MIN");
		mascotToTeamNameMapping.put("Jaguars", "JAC");
		mascotToTeamNameMapping.put("Panthers", "CAR");
		mascotToTeamNameMapping.put("Buccaneers", "TB");
		mascotToTeamNameMapping.put("Chiefs", "KC");
		mascotToTeamNameMapping.put("Chargers", "LAC");
		mascotToTeamNameMapping.put("49ers", "SF");
		mascotToTeamNameMapping.put("Ravens", "BAL");
		mascotToTeamNameMapping.put("Colts", "IND");
		mascotToTeamNameMapping.put("Bills", "BUF");
		mascotToTeamNameMapping.put("Cardinals", "ARI");
		mascotToTeamNameMapping.put("Browns", "CLE");
		mascotToTeamNameMapping.put("Redskins", "WAS");
		mascotToTeamNameMapping.put("Falcons", "ATL");
		mascotToTeamNameMapping.put("Giants", "NYG");
		mascotToTeamNameMapping.put("Jets", "NYJ");
		mascotToTeamNameMapping.put("Rams", "LAR");
		mascotToTeamNameMapping.put("Seahawks", "SEA");
		mascotToTeamNameMapping.put("Bengals", "CIN");
		mascotToTeamNameMapping.put("Eagles", "PHI");
		mascotToTeamNameMapping.put("Patriots", "NE");
		mascotToTeamNameMapping.put("Free Agent", "FA");
		
		for (Entry<String, String> entry : mascotToTeamNameMapping.entrySet()) {
			teamNameToMascotMapping.put(entry.getValue(), entry.getKey());
		}
	}

}
