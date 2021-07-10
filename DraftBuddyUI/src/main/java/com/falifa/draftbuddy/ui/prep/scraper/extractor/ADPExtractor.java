package com.falifa.draftbuddy.ui.prep.scraper.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
	
	private static final String PLUS_SIGN = "&#43;";
	private static final CharSequence MINUS_SIGN = "&#45;";

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
			String adp = playerRow.findFirst("<td>").getTextContent();
			player.getRankMetadata().setAdp(adp);
			int adpDiff = Integer.valueOf(adp) - Integer.valueOf(player.getRankMetadata().getOverallRank());
			player.getRankMetadata().setVsAdp(String.valueOf(adpDiff));
			player.getDraftingDetails().setVsValueBadgeClass(getValueBadgeClass(adpDiff));
			player.getDraftingDetails().setCurrentPlayerValue((0 - Integer.valueOf(adp))); // initial value
			PlayerCache.addOrUpdatePlayer(player);
		} else {
			return name;
		}
		return null;
	}
	
	private String getValueBadgeClass(int value) {
		if (value <= -40) { return "neg-40";
		} else if (value <= -20) { return "neg-20";
		} else if (value <= -10) { return "neg-10";
		} else if (value <= -5) { return "neg-5";
		} else if (value <= -2) { return "neg-2";
		} else if (value <= 2) { return "even";
		} else if (value <= 5) { return "pos-2";
		} else if (value <= 10) { return "pos-5";
		} else if (value <= 20) { return "pos-10";
		} else if (value <= 40) { return "pos-20";
		} else { return "pos-40";
		}
	}

}
