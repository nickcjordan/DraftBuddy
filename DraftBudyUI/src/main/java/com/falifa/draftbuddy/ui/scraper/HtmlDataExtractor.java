package com.falifa.draftbuddy.ui.scraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.extractor.NotesExtractor;
import com.falifa.draftbuddy.ui.scraper.extractor.RankingExtractor;
import com.jaunt.Element;
import com.jaunt.UserAgent;

@Component
public class HtmlDataExtractor {
	
	
	private static final Logger log = LoggerFactory.getLogger(HtmlDataExtractor.class);
	
	@Autowired
	private RankingExtractor rankingExtractor;
	
	@Autowired
	private NotesExtractor notesExtractor;

	public Map<String, Player> extractPlayerDataFromFantasyProsRankings(String htmlTable) {
		Map<String, Player> playerData = new HashMap<String, Player>();
		UserAgent userAgent = new UserAgent();
		try {
			playerData = rankingExtractor.extractPlayerRankingDataFromTableElement(userAgent.openContent(htmlTable).findFirst("<table>"));
		} catch (Exception e) {
			log.error("Error extracting Ranking data from html table", e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return playerData;
	}

//	public Map<String, Player> extractPlayerDataFromFantasyProsADP(String htmlTable) {
//		return null; // TODO
//	}

	public void extractPlayerDataFromFantasyProsNotes(String htmlTable) {
		UserAgent userAgent = new UserAgent();
		try {
			notesExtractor.extractPlayerNotesDataFromElement(userAgent.openContent(htmlTable).findFirst("<div>"));
		} catch (Exception e) {
			log.error("Error extracting Ranking data from html table", e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
	}

	public Map<String, Player> extractPlayerDataFromFantasyProsRookiesRankings(String htmlTable) {
		return null; // TODO
	}

	public Map<String, Player> extractPlayerDataFromFantasyProsPositionalProjections(String position, String htmlTable) {
		return null; // TODO
	}

}
