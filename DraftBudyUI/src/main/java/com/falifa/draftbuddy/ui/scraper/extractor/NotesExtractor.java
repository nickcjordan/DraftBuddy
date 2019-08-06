package com.falifa.draftbuddy.ui.scraper.extractor;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.PlayerNote;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.jaunt.Element;
import com.jaunt.NotFound;

@Component
public class NotesExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(NotesExtractor.class);

	@Autowired
	private JsonDataFileManager playerManager;

	public boolean extractPlayerNotesDataFromElement(Element div) {
		boolean allSuccess = true;
		for (Element playerDetail : div.findEach("<div>")) { // each player node
			try {
				String fantasyProsId = playerDetail.getAt("class").split(" ")[1].split("-")[2]; // grab fantasy pros id from top level div metadata
				allSuccess &= handlePlayerDetail(fantasyProsId, playerDetail);
			} catch (NotFound e) {
				log.error("Error parsing ID for player row :: " + playerDetail.toString(), e);
				allSuccess = false;
			}
		}
		log.info("All player notes have been parsed");
		return allSuccess;
	}

	private boolean handlePlayerDetail(String fantasyProsId, Element playerDetail) {
		Player playerToPopulate = playerManager.getPlayerFromTemporaryStorage(fantasyProsId);
		if (playerToPopulate != null) {
			try {
				Element row = playerDetail.findFirst("<table>").findFirst("<tbody>").findFirst("<tr>");
				Element detail = row.findFirst("<td class=\"text\">").findFirst("<div class=\"extra\">");
				String noteText = detail.findFirst("<div class=\"player-note\">").getTextContent();
				if (!noteText.trim().isEmpty()) {
					String timestamp = detail.findFirst("<span class=\"pull-right timestamp\">").getTextContent();
					playerToPopulate.getNotesMetadata().addNote(new PlayerNote(timestamp, noteText, "FantasyPros"));
					playerManager.addUpdatedPlayer(fantasyProsId, playerToPopulate);
					return true;
				}
				String imgLocation = row.findFirst("<td class=\"photo\">").findFirst("<img>").getAtString("src");
				if (StringUtils.isNotEmpty(imgLocation)) {
					playerToPopulate.getPictureMetadata().setPicLink(imgLocation);
				}
			} catch (Exception e) {
				log.error("ERROR parsing player notes :: " + playerDetail.toString(), e);
			}
		} else {
			log.debug("ERROR parsing notes :: No player found in temporary storage for fantasyProsId={}", fantasyProsId);
		}
		return false;
	}
	
	//TODO add for personal notes

}
