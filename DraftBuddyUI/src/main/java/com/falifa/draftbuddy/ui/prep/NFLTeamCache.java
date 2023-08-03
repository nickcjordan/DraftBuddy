package com.falifa.draftbuddy.ui.prep;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_NFL_TEAMS_JSON_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_PLAYERS_JSON_FILE_PATH;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.MasterTeamTO;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.prep.scraper.extractor.NullStatisticKeySerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NFLTeamCache {
	
	private static final Logger log = LoggerFactory.getLogger(NFLTeamCache.class);
	
	private static Map<String, NFLTeam> nflTeamsByAbbreviation;

	static {
		nflTeamsByAbbreviation = new ConcurrentHashMap<String, NFLTeam>();
		nflTeamsByAbbreviation.put("FA", new NFLTeam("", NflTeamMetadata.FREE_AGENTS));
	}
	
	public static boolean updateNflJsonFileWithCachedData() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializerProvider().setNullKeySerializer(new NullStatisticKeySerializer());
			mapper.writeValue(new File(MASTER_NFL_TEAMS_JSON_FILE_PATH), new MasterTeamTO(nflTeamsByAbbreviation));
			log.info("Master team structure json initialized successfully");
			return true;
		} catch (Exception e) {
			log.error("ERROR writing json files", e);
			return false;
		}
	}
	
	public static void addTeamToNfl(String teamId, NflTeamMetadata team) {
		nflTeamsByAbbreviation.put(team.getAbbreviation(), new NFLTeam(teamId, team));
	}
	
	public static void addPlayerToNflTeam(Player player, NflTeamMetadata teamMeta) {
		NFLTeam nflTeam = nflTeamsByAbbreviation.get(teamMeta.getAbbreviation());
		if (nflTeam != null) {
			nflTeam.addPlayer(player);
			nflTeamsByAbbreviation.put(teamMeta.getAbbreviation(), nflTeam);
		} else {
			log.error("ERROR no NFL team found for {}", teamMeta.getAbbreviation());
		}
	}

	public static Map<String, NFLTeam> getNflTeamsByAbbreviation() {
		return nflTeamsByAbbreviation;
	}

	public static void setNflTeamsByAbbreviation(Map<String, NFLTeam> nflTeamsByAbbreviation) {
		NFLTeamCache.nflTeamsByAbbreviation = nflTeamsByAbbreviation;
	}
	
	public static NFLTeam getNflTeamByFullTeamName(String name) {
		for (NFLTeam team : nflTeamsByAbbreviation.values()) {
			if (team.getTeam().getFullName().toLowerCase().equals(name.toLowerCase()) 
					|| name.toLowerCase().contains(team.getTeam().getMascot().toLowerCase())) {
				return team;
			}
		}
		return null;
	}
	
	

}
