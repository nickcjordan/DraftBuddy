package com.falifa.draftbuddy.ui.scraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.extractor.ADPExtractor;
import com.falifa.draftbuddy.ui.scraper.extractor.NotesExtractor;
import com.falifa.draftbuddy.ui.scraper.extractor.PositionalProjectionsExtractor;
import com.falifa.draftbuddy.ui.scraper.extractor.RankingExtractor;
import com.falifa.draftbuddy.ui.scraper.extractor.RookieRankingExtractor;
import com.jaunt.UserAgent;

@Component
public class HtmlDataExtractor {
	
	private static final Logger log = LoggerFactory.getLogger(HtmlDataExtractor.class);

	@Autowired
	private ADPExtractor adpExtractor;
	
	@Autowired
	private RankingExtractor rankingExtractor;
	
	@Autowired
	private NotesExtractor notesExtractor;
	
	@Autowired
	private RookieRankingExtractor rookieRankingExtractor;
	
	@Autowired
	private PositionalProjectionsExtractor positionalProjectionsExtractor;
	

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

	public boolean extractPlayerDataFromFantasyProsADP(String htmlTable) {
		UserAgent userAgent = new UserAgent();
		boolean success = false;
		try {
			success = adpExtractor.extractPlayerADPFromTableElement(userAgent.openContent(htmlTable).findFirst("<table>"));
		} catch (Exception e) {
			log.error("Error extracting ADP data from html table", e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return success;
	}

	public boolean extractPlayerDataFromFantasyProsNotes(String htmlTable) {
		UserAgent userAgent = new UserAgent();
		boolean success = false;
		try {
			success = notesExtractor.extractPlayerNotesDataFromElement(userAgent.openContent(htmlTable).findFirst("<div>"));
		} catch (Exception e) {
			log.error("Error extracting Ranking data from html table", e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return success;
	}

	public boolean extractPlayerDataFromFantasyProsRookiesRankings(String htmlTable) {
		UserAgent userAgent = new UserAgent();
		boolean success = false;
		try {
			success = rookieRankingExtractor.extractRookieRankingDataFromElement(userAgent.openContent(htmlTable).findFirst("<table>"));
		} catch (Exception e) {
			log.error("Error extracting Ranking data from html table", e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return success;
	}

	public boolean extractPlayerDataFromFantasyProsPositionalProjections(String position, String htmlTable) {
		UserAgent userAgent = new UserAgent();
		boolean success = false;
		try {
			success = positionalProjectionsExtractor.extractPositionalProjectionDataFromElement(userAgent.openContent(htmlTable).findFirst("<table>"));
		} catch (Exception e) {
			log.error("Error extracting Positional Projections data from html table :: " + position, e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return success;
	}

}
