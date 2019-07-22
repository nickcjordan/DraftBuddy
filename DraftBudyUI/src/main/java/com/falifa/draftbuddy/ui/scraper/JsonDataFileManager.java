package com.falifa.draftbuddy.ui.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_PLAYERS_JSON_FILE_PATH;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonDataFileManager {
	
	private static final Logger log = LoggerFactory.getLogger(JsonDataFileManager.class);
	
	private Map<String, Player> players;
	
	public JsonDataFileManager() {
		this.players = new ConcurrentHashMap<String, Player>();
	}

	public boolean addPlayerDataToExisting(Map<String, Player> playerData) {
		players.putAll(playerData);
		return true;
//		boolean success = true;
//		for (Entry<String, Player> entry : playerData.entrySet()) {
//			try {
//				if (players.containsKey(entry.getKey())) {
//					log.info("Populating fields of existing player {} and adding merged to temporary storage", entry.getValue().getPlayerName());
//					Player merged = copyPropertiesToExisting(entry.getValue(), players.get(entry.getKey()));
//					players.put(merged.getFantasyProsId(), merged);
//				} else {
//					log.info("Adding new player {} to temporary storage", entry.getValue().getPlayerName());
//					players.put(entry.getKey(), entry.getValue());
//				}
//			} catch (Exception e) {
//				log.error("Error adding player data to temporary storage");
//				success = false;
//			}
//		}
//		return success;
	}

	public Player getPlayerFromTemporaryStorage(String fantasyProsId) {
		return players.get(fantasyProsId);
	}

	public void addUpdatedPlayer(String fantasyProsId, Player player) {
		 players.put(fantasyProsId, player);
	}

	public boolean updateJsonCacheFilesWithParsedData() {
		try {
			new ObjectMapper().writeValue(new File(MASTER_PLAYERS_JSON_FILE_PATH), new MasterPlayersTO(players));
			log.info("Master players json successfully updated");
			return true;
		} catch (Exception e) {
			log.error("ERROR writing master players json", e);
			return false;
		} 
	}

//	private Player copyPropertiesToExisting(Player copyFrom, Player copyTo) {
//		Map<String, String> fieldsToAdd = new HashMap<String, String>();
//		
//	}

}
