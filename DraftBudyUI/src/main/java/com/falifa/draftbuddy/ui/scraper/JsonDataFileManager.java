package com.falifa.draftbuddy.ui.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.DataSourcePaths;
import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.MasterTeamTO;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.extractor.NullStatisticKeySerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonDataFileManager {
	
	private static final Logger log = LoggerFactory.getLogger(JsonDataFileManager.class);
	
	private Map<String, Player> players;
	private Map<String, NFLTeam> nflTeams;
	
	public JsonDataFileManager() {
		this.players = new ConcurrentHashMap<String, Player>();
		this.nflTeams = new ConcurrentHashMap<String, NFLTeam>();
		nflTeams.put("FA", new NFLTeam("", NflTeamMetadata.FREE_AGENTS));
	}

	public boolean addPlayerDataToExisting(Map<String, Player> playerData) {
		players.putAll(playerData);
		return true;
	}

	public Player getPlayerFromTemporaryStorage(String fantasyProsId) {
		return players.get(fantasyProsId);
	}

	public void addUpdatedPlayer(String fantasyProsId, Player player) {
		 players.put(fantasyProsId, player);
	}

	public boolean updateJsonCacheFilesWithParsedData() {
		try {
			parseAndDownloadImagesOfPlayersForOffline();
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializerProvider().setNullKeySerializer(new NullStatisticKeySerializer());
			mapper.writeValue(new File(MASTER_PLAYERS_JSON_FILE_PATH), new MasterPlayersTO(players));
			log.info("Master players json successfully updated");
			
			mapper.writeValue(new File(MASTER_NFL_TEAMS_JSON_FILE_PATH), new MasterTeamTO(nflTeams));
			log.info("Master team structure json initialized successfully");
			return true;
		} catch (Exception e) {
			log.error("ERROR writing json files", e);
			return false;
		} 
	}
	
	public boolean playerDoesNotExistInTemporaryStorage(String fantasyProsId) {
		return !players.containsKey(fantasyProsId);
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public Map<String, NFLTeam> getNflTeams() {
		return nflTeams;
	}
	
	public void addTeamToNfl(String fantasyProsId, NflTeamMetadata team) {
		nflTeams.put(team.getAbbreviation(), new NFLTeam(fantasyProsId, team));
	}
	
	private boolean parseAndDownloadImagesOfPlayersForOffline() {
		boolean success = true;
		for (Player p : getPlayers().values()) {
			String filePath = DataSourcePaths.PLAYER_IMAGES_BASE_FILE_PATH + p.getFantasyProsId() + ".png";
			try {
				String picLink = p.getPictureMetadata().getPicLink();
				if (picLink != null) {
					downloadFileFromUrl(picLink, filePath);
					p.getPictureMetadata().setPicLocation(filePath);
					success &= true;
				}
			} catch (Exception e) {
				log.error("ERROR trying to download image file at " + p.getPictureMetadata().getPicLink() + " to " + filePath, e);
				success = false;
			}
		}
		return success;
	}
	
	public boolean downloadFileFromUrl(String sourceUrl, String destPath) {
		try (InputStream in = new URL(sourceUrl).openStream()) {
			Files.copy(in, new File(destPath).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			log.info("Successfully downloaded file at url {} to file path {}", sourceUrl, destPath);
			return true;
		} catch (Exception e) {
			log.error("ERROR downloading file at url {} to file path {} :: message={}", sourceUrl, destPath, e.getMessage());
			return false;
		}
	}
	
}
