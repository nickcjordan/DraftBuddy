package com.falifa.draftbuddy.ui.scraper;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class DataParserManager {
	
	
	private static final Logger log = LoggerFactory.getLogger(DataParserManager.class);

	
	@Autowired
	private HtmlDataFileParser htmlParser;
	
	@Autowired
	private HtmlDataExtractor extractor;
	
	@Autowired
	private JsonDataFileManager updater;

	public boolean parseAllDataSources() {
		boolean success = true;
		success &= parseAndUpdateFantasyProsRankings();
		success &= parseAndUpdateFantasyProsNotes();
		success &= parseAndUpdateFantasyProsADP();
		success &= parseAndUpdateFantasyProsRookiesRankings();
		success &= parseAndUpdateFantasyProsPositionalProjections();
		
		updater.updateJsonCacheFilesWithParsedData();
		
//		if (success) {
//			log.info("All data sources have been updated :: calling off to save all player data json to cache file...");
//			success &= updater.updateJsonCacheFilesWithParsedData();
//		} else {
//			log.error("ERROR occured when trying to update all data sources, json cache will not get updated");
//		}
		return success;
	}
	
	private boolean parseAndUpdateFantasyProsRankings() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsRankings();
		Map<String, Player> playerData = extractor.extractPlayerDataFromFantasyProsRankings(htmlTable);
		return updater.addPlayerDataToExisting(playerData);
	}

	private boolean parseAndUpdateFantasyProsNotes() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsNotes();
		return extractor.extractPlayerDataFromFantasyProsNotes(htmlTable);
	}
	
	private boolean parseAndUpdateFantasyProsADP() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsADP();
		return extractor.extractPlayerDataFromFantasyProsADP(htmlTable);
	}
	
	private boolean parseAndUpdateFantasyProsRookiesRankings() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsRookiesRankings();
		return extractor.extractPlayerDataFromFantasyProsRookiesRankings(htmlTable);
	}

	private boolean parseAndUpdateFantasyProsProjectionsByPosition(String position) {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsPositionalProjections(position);
		return extractor.extractPlayerDataFromFantasyProsPositionalProjections(position, htmlTable);
	}


	
	private boolean parseAndUpdateFantasyProsPositionalProjections() {
		boolean success = true;
		for (Position pos : Position.values()) {
			success &= parseAndUpdateFantasyProsProjectionsByPosition(pos.getAbbrev().toLowerCase());
		}
		return success;
	}
	
}
