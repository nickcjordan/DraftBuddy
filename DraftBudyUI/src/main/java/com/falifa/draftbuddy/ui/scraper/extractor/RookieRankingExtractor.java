package com.falifa.draftbuddy.ui.scraper.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.jaunt.Element;
import com.jaunt.NotFound;

@Component
public class RookieRankingExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(RookieRankingExtractor.class);
	
	@Autowired
	private JsonDataFileManager playerManager;

	public boolean extractRookieRankingDataFromElement(Element table) throws NotFound {
		boolean allSuccess = true;
		for (Element playerRow : table.findFirst("<tbody>").findEach("<tr>")) { // each player node
			try {
				String fantasyProsId = playerRow.getAt("data-id"); // grab fantasy pros id from top level div metadata
				allSuccess &= handlePlayerRow(fantasyProsId, playerRow);
			} catch (NotFound e) {
				log.debug("Error parsing ID for player row :: " + playerRow.toString());
			}
		}
		log.info("All Rookie rankings have been parsed");
		return allSuccess;
	}

	private boolean handlePlayerRow(String fantasyProsId, Element playerRow) {
		Player rookieToPopulate = playerManager.getPlayerFromTemporaryStorage(fantasyProsId);
		if (rookieToPopulate != null) {
			rookieToPopulate.getDraftingDetails().setRookie(true);
			try {
				rookieToPopulate.getRankMetadata().setRookieRanking(playerRow.findFirst("<td>").getTextContent());
				return true;
			} catch (NotFound e) {}
		} else {
			log.debug("Did not find rookie in temporary storage :: fantasyProsId={} :: row={}", fantasyProsId, playerRow.toString());
			return true;
		}
		return false;
	}

}
