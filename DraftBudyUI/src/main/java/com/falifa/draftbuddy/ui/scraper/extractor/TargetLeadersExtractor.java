package com.falifa.draftbuddy.ui.scraper.extractor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;

@Component
public class TargetLeadersExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(TargetLeadersExtractor.class);
	
	@Autowired
	private JsonDataFileManager playerManager;

	public boolean extractTargetLeaderDataFromElement(Element table) throws NotFound {
		boolean allSuccess = true;
		for (Element playerRow : table.findFirst("<tbody>").findEach("<tr>")) { // each player node
			try {
				String fantasyProsId = playerRow.getAt("class").split("-")[2]; // grab fantasy pros id from top level div metadata
				allSuccess &= handlePlayerRow(fantasyProsId, playerRow);
			} catch (NotFound e) {
				log.error("Error parsing ID for player row :: " + playerRow.toString());
			}
		}
		log.info("All Target Leaders data has been parsed");
		return allSuccess;
	}

	private boolean handlePlayerRow(String fantasyProsId, Element playerRow) {
		Player playerToPopulate = playerManager.getPlayerFromTemporaryStorage(fantasyProsId);
		if (playerToPopulate != null) {
			try {
				List<Element> details = playerRow.getChildElements();
				playerToPopulate.getPositionalStats().setPriorTotalTargets(details.get(details.size() - 2).getTextContent()); // second to last column in row
				playerToPopulate.getPositionalStats().setPriorAverageTargetsPerGame(details.get(details.size() - 1).getTextContent()); // last column in row
				return true;
			} catch (Exception e) { log.error("ERROR trying to extract target leaders data from html", e); }
		} else {
			log.debug("Did not find player in temporary storage :: fantasyProsId={} :: row={}", fantasyProsId, playerRow.toString());
		}
		return false;
	}
}
