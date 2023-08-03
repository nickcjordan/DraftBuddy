package com.falifa.draftbuddy.ui.prep.scraper.extractor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.model.team.NflTeamStats;
import com.falifa.draftbuddy.ui.prep.NFLTeamCache;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.jaunt.Element;
import com.jaunt.NotFound;

@Component
public class TeamTargetsExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(TeamTargetsExtractor.class);
	
	public boolean extractTeamTargetDataFromElement(Element table) throws NotFound {
		boolean allSuccess = true;
		for (Element teamRow : table.findFirst("<tbody>").findEach("<tr>")) { // each player node
			try {
				allSuccess &= handleTeamRow(teamRow);
			} catch (Exception e) {
				log.error("Error team target row :: " + teamRow.toString());
			}
		}
		log.info("All Team Target data has been parsed");
		return allSuccess;
	}

	private boolean handleTeamRow(Element row) {
		
		List<Element> details = row.getChildElements();
		Element nameElemet = details.get(0);
		Element nameLink = nameElemet.getChildElements().get(0);
		String name = nameLink.getTextContent();
		
		NFLTeam team = NFLTeamCache.getNflTeamByFullTeamName(name);
		if (team != null) {
			NflTeamStats stats = team.getStats();
			try {
				stats.setWrTargets(Integer.valueOf(details.get(1).getTextContent()));
				stats.setWrTargetPercentage(Double.valueOf(details.get(2).getTextContent()));
				stats.setRbTargets(Integer.valueOf(details.get(3).getTextContent()));
				stats.setRbTargetPercentage(Double.valueOf(details.get(4).getTextContent()));
				stats.setTeTargets(Integer.valueOf(details.get(5).getTextContent()));
				stats.setTeTargetPercentage(Double.valueOf(details.get(6).getTextContent()));
				stats.setTotalTargets(Integer.valueOf(details.get(7).getTextContent()));
				return true;
			} catch (Exception e) { log.error("ERROR trying to extract team targets data from html", e); }
		} else {
			log.debug("Did not find team in temporary storage :: name={} :: row={}", name, row.toString());
		}
		return false;
	}
}
