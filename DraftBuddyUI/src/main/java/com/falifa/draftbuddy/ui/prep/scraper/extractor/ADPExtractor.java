package com.falifa.draftbuddy.ui.prep.scraper.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.jaunt.Element;
import com.jaunt.NotFound;

@Component
public class ADPExtractor {

	private static final Logger log = LoggerFactory.getLogger(ADPExtractor.class);

	public boolean extractPlayerADPFromTableElement(Element table) throws NotFound {
		int failCount = 0;
		List<String> namesNotFound = new ArrayList<String>();
		for (Element playerRow : table.findFirst("<tbody>").findEach("<tr>")) { // each player node
			try {
				Element dataElement = playerRow.findFirst("<td class=\"player-label\">").findFirst("<a href=\"#\"");
				String fantasyProsId = dataElement.getAt("class").split(" ")[1].split("-")[2]; // grab fantasy pros id from top level div metadata
				String errorResultName = handlePlayerRow(fantasyProsId, playerRow);
				if (errorResultName != null) {
					namesNotFound.add(errorResultName);
				}
			} catch (NotFound e) {
				log.error("Error parsing ID for player row :: {} :: {}", playerRow.toString(), e.getMessage());
				failCount++;
			}
		}
		log.info("All ADP players have been parsed :: fail count = {} :: notFound count = {} :: notFound names = [{}]", failCount, namesNotFound.size(), namesNotFound.stream().collect(Collectors.joining(", ")));
		return (failCount < 5);
	}

	private String handlePlayerRow(String fantasyProsId, Element playerRow) throws NotFound {
		String name = playerRow.findFirst("<td class=\"player-label\">").findFirst("<a class=\"player-name\"").getTextContent();
		if (PlayerCache.isInCache(fantasyProsId)) {
			Player player = PlayerCache.getPlayer(fantasyProsId);
			player.getRankMetadata().setAdp(playerRow.findFirst("<td>").getTextContent());
			PlayerCache.addOrUpdatePlayer(player);
		} else {
			return name;
		}
		return null;
	}

}
