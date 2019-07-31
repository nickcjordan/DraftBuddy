package com.falifa.draftbuddy.ui.scraper.extractor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.jaunt.Element;
import com.jaunt.NotFound;

@Component
public class ADPExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(ADPExtractor.class);
	
	@Autowired
	private JsonDataFileManager playerManager;

	public boolean extractPlayerADPFromTableElement(Element table) throws NotFound {
		boolean allSuccess = true;
		for (Element playerRow : table.findFirst("<tbody>").findEach("<tr>")) { // each player node
			try {
				Element dataElement = playerRow.findFirst("<td class=\"player-label\">").findFirst("<a href=\"#\"");
				String fantasyProsId = dataElement.getAt("class").split(" ")[1].split("-")[2]; // grab fantasy pros id from top level div metadata
				handlePlayerRow(fantasyProsId, playerRow);
			} catch (NotFound e) {
//				log.error("Error parsing ID for player row :: {} :: {}", playerRow.toString(), e.getMessage());
				allSuccess = false;
			}
		}
		log.info("All ADP players have been parsed");
		return allSuccess;
	}

	private void handlePlayerRow(String fantasyProsId, Element playerRow) throws NotFound {
		if (playerManager.playerDoesNotExistInTemporaryStorage(fantasyProsId)) {
			String name = playerRow.findFirst("<td class=\"player-label\">").findFirst("<a class=\"player-name\"").getTextContent();
			String pprAdp = playerRow.findFirst("<td>").getTextContent();
			String teamNameAbbrev = playerRow.findFirst("<td class=\"player-label\">").findFirst("<small>").getTextContent();
			Player newPlayer = new Player();
			newPlayer.setFantasyProsId(fantasyProsId);
			newPlayer.setPlayerName(name);
			newPlayer.getRankMetadata().setAdp(pprAdp);
			newPlayer.setTeam(NflTeamMetadata.findNflTeamFromAbbreviation(teamNameAbbrev));
			playerManager.addUpdatedPlayer(fantasyProsId, newPlayer);
		} 
	}

}
