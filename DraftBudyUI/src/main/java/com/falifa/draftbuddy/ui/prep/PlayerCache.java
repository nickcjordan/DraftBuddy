package com.falifa.draftbuddy.ui.prep;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.MASTER_PLAYERS_JSON_FILE_PATH;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.falifa.draftbuddy.ui.model.MasterPlayersTO;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.scraper.extractor.NullStatisticKeySerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerCache {

	private static final Logger log = LoggerFactory.getLogger(PlayerCache.class);

	private static Map<String, Player> players;

	static {
		players = new HashMap<String, Player>();
	}

	public static boolean addPlayerDataToExisting(Map<String, Player> playerData) {
		for (Player p : playerData.values()) {
			p.getDraftingDetails().setAvailable(true);
		}
		players.putAll(playerData);
		return true;
	}

	public static Player getPlayer(String fantasyProsId) {
		return players.get(fantasyProsId);
	}

	public static void addOrUpdatePlayer(Player player) {
		players.put(player.getFantasyProsId(), player);
	}

	public static boolean updateJsonCacheFilesWithParsedData() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializerProvider().setNullKeySerializer(new NullStatisticKeySerializer());
			mapper.writeValue(new File(MASTER_PLAYERS_JSON_FILE_PATH), new MasterPlayersTO(players));
			log.info("Master players json successfully updated");
			return true;
		} catch (Exception e) {
			log.error("ERROR writing json files", e);
			return false;
		}
	}

	public static boolean isInCache(String fantasyProsId) {
		return players.containsKey(fantasyProsId);
	}

	public static Map<String, Player> getPlayers() {
		return players;
	}

	

}
