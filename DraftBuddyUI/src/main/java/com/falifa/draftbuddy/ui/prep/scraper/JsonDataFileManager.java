package com.falifa.draftbuddy.ui.prep.scraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.model.PlayerTO;
import com.falifa.draftbuddy.ui.constants.DataSourcePaths;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.api.ApiDataDelegate;
import com.falifa.draftbuddy.ui.prep.api.PlayerNameMatcher;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.prep.data.PlayerPopulator;

@Component
public class JsonDataFileManager {

	private static final Logger log = LoggerFactory.getLogger(JsonDataFileManager.class);

	// private Map<String, Player> players;
	// private Map<String, NFLTeam> nflTeams;

	private ArrayList<String> playerNameToIdMapFailedToFind;
	private Map<String, PlayerTO> playerStatsTOMap;

	@Autowired
	private DataPopulatorHelper dataPopulator;

	@Autowired
	private ApiDataDelegate dataDelegate;

	@Autowired
	private PlayerPopulator playerPopulator;

	@Autowired
	private PlayerNameMatcher nameMatcher;

	public JsonDataFileManager() {
		this.playerNameToIdMapFailedToFind = new ArrayList<String>();
		this.playerStatsTOMap = new HashMap<String, PlayerTO>();
	}

	public boolean downloadFileFromUrl(String sourceUrl, String destPath) {
		try {
			URLConnection urlConnection = new URL(sourceUrl).openConnection();
			urlConnection.addRequestProperty("User-Agent", "Mozilla");
			urlConnection.setReadTimeout(5000);
			urlConnection.setConnectTimeout(5000);
			try (InputStream in = urlConnection.getInputStream()) {
				Files.copy(in, new File(destPath).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				log.info("Download SUCCESS :: url={} :: file path={}", sourceUrl, destPath);
				return true;
			} catch (Exception e) {
				log.error("ERROR downloading file at url {} to file path {} :: message={}", sourceUrl, destPath, e.getMessage());
				return false;
			}
		} catch (IOException e1) {
			e1.printStackTrace(); // TODO
			return false;
		}
	}

	public boolean parseAndUpdateStatsFromAPI() {
		int count = 0;
		playerStatsTOMap = Optional.ofNullable(dataDelegate.getPlayersMapFromApi()).orElse(playerStatsTOMap);
		for (Entry<String, PlayerTO> entry : playerStatsTOMap.entrySet()) {
			if (entry.getKey() == null) {
				log.error("NULL name found in playerStatsTOMap :: id={}", entry.getValue().getPlayerId());
			} else {
				String id = null;
				id = getCorrectIdFromName(entry.getKey());
				if (PlayerCache.isInCache(id)) {
					Player p = PlayerCache.getPlayer(id);
					playerPopulator.populatePlayerWithPriorStatsFromTO(p, entry.getValue());
					playerPopulator.populatePlayerPriorTotalsFields(p);
					PlayerCache.addOrUpdatePlayer(p);
					count++;
				} else {
					log.debug("ERROR playersById did not contain key={}", id);
					playerNameToIdMapFailedToFind.add(entry.getKey());
				}
			}
		}
		if (playerNameToIdMapFailedToFind.size() > 0) {
			log.error("{} players from API response were not found in existing data from FantasyPros :: [{}]", playerNameToIdMapFailedToFind.size(),
					playerNameToIdMapFailedToFind.stream().collect(Collectors.joining(", ")));
		}
		log.info("Successfully populated stats of {} players out of the {} found in the API response", count, playerStatsTOMap.size());
		playerNameToIdMapFailedToFind.clear();
		return true;
	}

	public boolean downloadAndSetPlayerImages() {
		for (Player p : PlayerCache.getPlayers().values().stream().distinct().collect(Collectors.toList())) {
			downloadPictureFileAndSetField(p);
			PlayerCache.addOrUpdatePlayer(p);
		}
		return true;
	}

	private void downloadPictureFileAndSetField(Player p) {
		String picturePath = p.getPlayerName().replaceAll("[^a-zA-Z]+", "");
		String filePath = DataSourcePaths.PLAYER_IMAGES_BASE_FILE_PATH + picturePath;
		String filePathWithExtension = filePath;
		String webImagePath = DataSourcePaths.PLAYER_IMAGES_FILE_PATH + picturePath;
		String webImagePathWithExtension = webImagePath; // used to load picture in html src
		String picLink = p.getPictureMetadata().getPicLink() == null ? p.getPictureMetadata().getSmallPicLink() : p.getPictureMetadata().getPicLink();

		if (StringUtils.isNotEmpty(picLink)) {
			String ext = picLink.substring(picLink.lastIndexOf(".")); // get file extension from link
			if (ext.contains("com")) {
				ext = ".png";
			}
			filePathWithExtension += ext;
			webImagePathWithExtension += ext;
			try {
				if (!picLink.contains("http")) { // set protocol if not present
					picLink = "http:" + picLink;
				}
				if (!new File(filePathWithExtension).exists()) {
					downloadFileFromUrl(picLink, filePathWithExtension);
				}
				p.getPictureMetadata().setPicLocation(webImagePathWithExtension);
			} catch (Exception e) {
				log.error("ERROR trying to download image file at " + p.getPictureMetadata().getPicLink() + " to " + filePathWithExtension, e);
			}
		} else {
			log.debug("No picture link populated for player {}", p.getPlayerName());
		}

		// if (StringUtils.isEmpty(p.getPictureMetadata().getPicLocation()) &&
		// StringUtils.isNotEmpty(webImagePathWithExtension)) {
		// p.getPictureMetadata().setPicLocation(webImagePathWithExtension);
		// }
		if (new File(filePath + ".png").exists()) {
			p.getPictureMetadata().setPicLocation(webImagePath + ".png");
		} else if (new File(filePath + ".jpg").exists()) {
			p.getPictureMetadata().setPicLocation(webImagePath + ".jpg");
		}
	}

	private String getCorrectIdFromName(String playerName) {
		return (dataPopulator.getPlayerNameToIdMap().containsKey(nameMatcher.filter(playerName))) ? dataPopulator.getPlayerNameToIdMap().get(nameMatcher.filter(playerName))
				: nameMatcher.findIdForClosestMatchingName(playerName);
	}

}
