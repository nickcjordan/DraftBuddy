package com.falifa.draftbuddy.ui.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.data.StrategyFileHandler;
import com.falifa.draftbuddy.ui.drafting.sort.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.PlayerNote;
import com.falifa.draftbuddy.ui.model.to.FantasyFootballerPlayerTO;

@Component
public class DataParserManager {
	
	private static final Logger log = LoggerFactory.getLogger(DataParserManager.class);
	
	@Autowired
	private NFLTeamManager nfl;
	
	@Autowired
	private HtmlDataFileParser htmlParser;
	
	@Autowired
	private HtmlDataExtractor extractor;
	
	@Autowired
	private JsonDataFileManager updater;
	
	@Autowired
	private StrategyFileHandler strategyHandler;

	public boolean parseAllDataSources() {
		boolean success = true;
		success &= parseAndUpdateFantasyProsRankings();
		if (success == false) { 
			log.error("Failed to update parseAndUpdateFantasyProsRankings");
		}
		success &= parseAndUpdateFantasyProsNotes();
		if (success == false) { 
			log.error("Failed to update parseAndUpdateFantasyProsNotes");
		}
		success &= parseAndUpdateFantasyProsADP();
		if (success == false) { 
			log.error("Failed to update parseAndUpdateFantasyProsADP");
		}
		success &= parseAndUpdateFantasyProsRookiesRankings();
		if (success == false) { 
			log.error("Failed to update parseAndUpdateFantasyProsRookiesRankings");
		}
		success &= parseAndUpdateFantasyProsPositionalProjections();
		if (success == false) { 
			log.error("Failed to update parseAndUpdateFantasyProsPositionalProjections");
		}
		success &= parseAndUpdateFantasyProsTargetLeaders();
		if (success == false) { 
			log.error("Failed to update parseAndUpdateFantasyProsTargetLeaders");
		}
		success &= updater.parseAndUpdateStatsFromAPI();
		if (success == false) { 
			log.error("Failed to update parseAndUpdateStatsFromAPI");
		}
		success &= updater.downloadAndSetPlayerImages();
		if (success == false) { 
			log.error("Failed to update downloadAndSetPlayerImages");
		}
		
		success &= parseAndUpdateFantasyFootballers();
		updater.updateJsonCacheFilesWithParsedData();
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
	
	private boolean parseAndUpdateFantasyProsTargetLeaders() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsTargetLeaders();
		return extractor.extractPlayerDataFromFantasyProsTargetLeaders(htmlTable);
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
	
	private boolean parseAndUpdateFantasyFootballers() {
		List<FantasyFootballerPlayerTO> updates = htmlParser.parseListOfPlayerDataFromFantasyFootballers();
		Map<String, Player> updated = new HashMap<String, Player>();
		for (FantasyFootballerPlayerTO update : updates) {
			Player p = nfl.getPlayerFromName(update.getName());
			if (p == null) {
				log.error("ERROR did not find player from FantasyFootballers file :: name={}", update.getName());
			} else {
				if (updated.containsKey(p.getFantasyProsId())) {
					Player toUpdate = updated.get(p.getFantasyProsId());
					strategyHandler.addTagsToPlayer(toUpdate, update.getTag().getTag());
					if (update.getText() != null) {
						toUpdate.getNotesMetadata().addNote(new PlayerNote(update.getTag().name(), update.getText(), "Fantasy Footballers"));
					}
				} else {
					strategyHandler.addTagsToPlayer(p, update.getTag().getTag());
					updated.put(p.getFantasyProsId(), p);
					if (update.getText() != null) {
						p.getNotesMetadata().addNote(new PlayerNote(update.getTag().name(), update.getText(), "Fantasy Footballers"));
					}
				}
			}
		}
		
		try {
			for (List<String> split : strategyHandler.getSplitLinesFromFile(TAGS_CUSTOM_PATH, true)) {
				Player p = nfl.getPlayerFromName(split.get(0));
				if (p != null) {
					if (updated.containsKey(p.getFantasyProsId())) {
						Player toUpdate = updated.get(p.getFantasyProsId());
						strategyHandler.addTagsToPlayer(toUpdate, split.get(1));
					} else {
						strategyHandler.addTagsToPlayer(p, split.get(1));
						updated.put(p.getFantasyProsId(), p);
					}
				} else {
					log.error("ERROR cleaning up players :: Player not found = {}", split.get(0));
				}
			}
		} catch (Exception e) {
			log.error("ERROR cleaning up tags", e);
		}
		TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
		for (Player p : updated.values()) {
			sorted.add(p);
			updater.addUpdatedPlayer(p.getFantasyProsId(), p);
		}
		try {
			strategyHandler.updateTagFileWithCleanedUpResults(sorted);
		} catch (IOException e) {
			e.printStackTrace(); // TODO
			return false;
		}
		return true;
//		return extractor.extractPlayerDataFromFantasyProsPositionalProjections(position, htmlTable);
	}
}
