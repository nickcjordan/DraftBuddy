package com.falifa.draftbuddy.ui.scraper.extractor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.NflTeam;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.jaunt.Element;
import com.jaunt.NotFound;

@Component
public class RankingExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(RankingExtractor.class);
	
	private int currentTier = 0;
	private int tierStartIndex = 1;
	private Map<String, Player> players;
	Map<String, String> tierPositionMap;
	
	@Autowired
	private JsonDataFileManager dataManager;
	
	private void initializeDefaults() { 
		currentTier = 0;
		tierStartIndex = 1;
		players = new HashMap<String, Player>();
		tierPositionMap = new HashMap<String, String>();
	}

	public Map<String, Player> extractPlayerRankingDataFromTableElement(Element table) {
		initializeDefaults();
		try {
			extractFromTable(table);
		} catch (Exception e) {
			log.error("Error extracting Ranking data from HTML :: " + e.getMessage(), e);
		} 
		return players;
	}

	private void extractFromTable(Element table) throws NotFound {
		for (Element row : table.findFirst("<tbody>").findEvery("<tr>")) { 
			try {
				if (row.hasAttribute("style") && row.getAt("style").contains("display: none")) { continue; } 								// if empty row :: skip
				else if (row.hasAttribute("class") && row.getAt("class").contains("tier-row")) { parseTierNumberRow(row); }	// if tier row :: set tier number
				else if (row.hasAttribute("class") && row.getAt("class").contains("player-row")) { parsePlayerDataRow(row); } 	// if player row :: build player
//				else { log.info("No useful info found on <tr> line of HTML :: {}", row.toString()); }											// skip
			} catch (Exception e) {
				log.error("Error building player from HTML :: " + row.toString(), e);
			}
		}
	}

	private void parsePlayerDataRow(Element row) throws NotFound {
		Player p = new Player();
		tierStartIndex++;
		p.setTier(currentTier);
		buildPlayerFromDataTableRow(p, row.findEvery("<td>").iterator());
		players.put(p.getFantasyProsId(), p);
//		log.info("Player built from HTML table row :: {} :: {} ",  p.getId(), p.getPlayerName());
	}

	private void buildPlayerFromDataTableRow(Player p, Iterator<Element> iter) throws NotFound {
		if (iter.hasNext()) { String rank = iter.next().getTextContent(); p.getRankMetadata().setOverallRank(rank); p.setId(rank); } // overall ppr rank :: set both id and rank this 
		if (iter.hasNext()) { handleMetaDataRow(iter.next().findFirst("<input>"), p); } // show/hide checkbox :: acts as data source for ID, NAME, TEAM, & POSITION
		if (iter.hasNext()) { iter.next(); } // name label :: skip because I already have the name
		if (iter.hasNext()) { p.getRankMetadata().setPositionRank(iter.next().getTextContent().replace(p.getPosition().getAbbrev(), "")); } // position rank
		if (iter.hasNext()) { p.setBye(iter.next().getTextContent()); } // bye week
		if (iter.hasNext()) { p.getRankMetadata().setBestRank(iter.next().getTextContent()); } // best ranking
		if (iter.hasNext()) { p.getRankMetadata().setWorstRank(iter.next().getTextContent()); } // worst ranking
		if (iter.hasNext()) { p.getRankMetadata().setAvgRank(iter.next().getTextContent()); } // avg ranking
		if (iter.hasNext()) { p.getRankMetadata().setStdDev(iter.next().getTextContent()); } // std dev
		if (iter.hasNext()) { p.getRankMetadata().setAdp(getCorrectAdp(iter.next().getTextContent())); } // adp
		if (iter.hasNext()) { p.getRankMetadata().setVsAdp(iter.next().getTextContent()); } // vs adp
		p.getDraftingDetails().setCurrentPlayerValue((0 - Integer.valueOf(p.getRankMetadata().getAdp()))); // initial value
	}

	private void handleMetaDataRow(Element dataElement, Player p) throws NotFound {
		p.setFantasyProsId(dataElement.getAt("data-id"));
		p.setPlayerName(dataElement.getAt("data-name"));
		p.setTeam(NflTeam.findNflTeamFromAbbreviation(dataElement.getAt("data-team")));
		String pos = dataElement.getAt("data-position");
		p.setPosition(Position.get(pos));
		if (pos.equalsIgnoreCase(Position.DEFENSE.getAbbrev())) {
			dataManager.addTeamToNfl(p.getFantasyProsId(), p.getTeam());
		}
	}

	private void parseTierNumberRow(Element row) throws NotFound {
		currentTier++;
//		String tierNum = row.findFirst("<td>").getTextContent().split(" ")[1];
		String tierNum = row.getAt("data-tier");
		tierPositionMap.put(tierNum, String.valueOf(tierStartIndex));
	}
	
	public String getCorrectAdp(String s) {
		String num = (s.equals("NA") || s.trim().equals("") || s.equals("-")) ? "1111" : s;
		return String.valueOf(Math.round(Double.valueOf(num.replace(",", ""))));
	}
}
