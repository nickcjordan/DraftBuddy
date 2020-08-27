package com.falifa.draftbuddy.ui.prep.scraper.extractor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.PlayerNote;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;
import com.jaunt.Element;
import com.jaunt.NotFound;

@Component
public class NotesExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(NotesExtractor.class);

	public boolean extractPlayerNotesDataFromElement(Element div) {
		int failCount = 0;
		List<String> ids = new ArrayList<String>();
		for (Element playerDetail : div.findEach("<div>")) { // each player node
			try {
				String fantasyProsId = playerDetail.getAt("class").split(" ")[1].split("-")[2]; // grab fantasy pros id from top level div metadata
				ids.add(fantasyProsId);
				if (!handlePlayerDetail(fantasyProsId, playerDetail)) {
					failCount++;
				}
			} catch (NotFound e) {
				log.error("Error parsing ID for player row :: " + playerDetail.toString(), e);
				failCount++;
			}
		}
		log.info("All player notes have been parsed :: failed to update notes for {} player details", failCount);
		return (failCount < 200);
	}

	private boolean handlePlayerDetail(String fantasyProsId, Element playerDetail) {
		Player playerToPopulate = PlayerCache.getPlayer(fantasyProsId);
		if (playerToPopulate != null) {
			try {
				Element row = playerDetail.findFirst("<table>").findFirst("<tbody>").findFirst("<tr>");
				Element detail = row.findFirst("<td class=\"text\">").findFirst("<div class=\"extra\">");
				String noteText = detail.findFirst("<div class=\"player-note\">").getTextContent();
				if (!noteText.trim().isEmpty()) {
					String timestamp = detail.findFirst("<span class=\"pull-right timestamp\">").getTextContent();
					playerToPopulate.getNotesMetadata().addNote(new PlayerNote(timestamp, noteText, "FantasyPros"));
				}
				String imgLocation = row.findFirst("<td class=\"photo\">").findFirst("<img>").getAtString("src");
				if (StringUtils.isNotEmpty(imgLocation) && StringUtils.isEmpty(playerToPopulate.getPictureMetadata().getSmallPicLink())) {
					playerToPopulate.getPictureMetadata().setSmallPicLink(imgLocation);
				}
				PlayerCache.addOrUpdatePlayer(playerToPopulate);
				return true;
			} catch (Exception e) {
				log.error("ERROR parsing player notes :: " + playerDetail.toString(), e);
			}
		} else {
			log.debug("ERROR parsing notes :: No player found in PlayerCache for fantasyProsId={}", fantasyProsId);
		}
		return false;
	}
	
}
