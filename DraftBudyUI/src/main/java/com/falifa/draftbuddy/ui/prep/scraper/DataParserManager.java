package com.falifa.draftbuddy.ui.prep.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.model.FantasyFootballerPlayerTO;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.PlayerNote;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.data.ModelUpdater;
import com.falifa.draftbuddy.ui.prep.data.StrategyFileHandler;

@Component
public class DataParserManager {
	
	private static final Logger log = LoggerFactory.getLogger(DataParserManager.class);
	
	@Autowired
	private HtmlDataFileParser htmlParser;
	
	@Autowired
	private HtmlDataExtractor extractor;
	
	@Autowired
	private JsonDataFileManager jsonFileManager;
	
	@Autowired
	private StrategyFileHandler strategyHandler;
	
	@Autowired
	private ModelUpdater modelUpdater;
	
	@Autowired
	private DataPopulator dataPopulator;

	public boolean parseAllDataSources() {
		boolean success = true;
		success &= parseAndUpdateFantasyProsRankings();
		dataPopulator.populatePlayerNameMap();
		dataPopulator.populateManuallySetFieldValuesAndPutOnTeam();
		dataPopulator.populateNflTeamSpecificFieldValues();
		dataPopulator.populatePlayersWithTags();
		dataPopulator.setCurrentPlayerValue();
		dataPopulator.updateDraftStrategyDataFromFile();

		success = parseFantasyProsFiles(success);
		success &= jsonFileManager.parseAndUpdateStatsFromAPI();
		success &= jsonFileManager.downloadAndSetPlayerImages();
		success &= parseAndUpdateFantasyFootballers();
		
		// new stuff
		
		modelUpdater.clearFiltersAndSorts();
		
		// done
		success &= PlayerCache.updateJsonCacheFilesWithParsedData();
		
		return success;
	}

	private boolean parseFantasyProsFiles(boolean success) {
		success &= parseAndUpdateFantasyProsADP();
		success &= parseAndUpdateFantasyProsRookiesRankings();
		success &= parseAndUpdateFantasyProsPositionalProjections();
		success &= parseAndUpdateFantasyProsTargetLeaders();
		success &= parseAndUpdateFantasyProsNotes();
		return success;
	}

	private boolean parseAndUpdateFantasyProsRankings() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsRankings();
		Map<String, Player> playerData = extractor.extractPlayerDataFromFantasyProsRankings(htmlTable);
		return PlayerCache.addPlayerDataToExisting(playerData);
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
		try {
			Map<String, Player> updated = new HashMap<String, Player>();
			addTagsAndNotesFromFantasyFootballersUpdates(updated);
			addExistingTagsFromFile(updated);
			cycleThroughAllPlayersAndCheckForNewCoach(updated);
			strategyHandler.updateTagFileWithCleanedUpResults(buildSortedSetFromUpdates(updated));
		} catch (Exception e) {
			log.error("ERROR parsing Fantasy Footballer stats :: message={}", e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private void cycleThroughAllPlayersAndCheckForNewCoach(Map<String, Player> updated) {
		 for (Player p : PlayerCache.getPlayers().values()) {
			 if (p.getTeam().isNewCoach()) {
				 Player toUpdate = updated.getOrDefault(p.getFantasyProsId(), p);
				 toUpdate.getDraftingDetails().addTags(Tag.NEW_COACH.getTag());
				 updated.put(toUpdate.getFantasyProsId(), toUpdate);
			 }
		 }
	}

	private void addTagsAndNotesFromFantasyFootballersUpdates(Map<String, Player> updated) {
		for (FantasyFootballerPlayerTO update : htmlParser.parseListOfPlayerDataFromFantasyFootballers()) {
			Player p = dataPopulator.getPlayerFromName(update.getName());
//			playerManager.
			if (p == null) {
				log.error("ERROR did not find player from FantasyFootballers file :: name={}", update.getName());
			} else {
				Player toUpdate = updated.getOrDefault(p.getFantasyProsId(), p);
				strategyHandler.addTagsToPlayer(toUpdate, update.getTag().getTag());
				addPlayerNoteIfMissing(update, toUpdate);
				updated.put(toUpdate.getFantasyProsId(), toUpdate);
			}
		}
	}

	private void addExistingTagsFromFile(Map<String, Player> updated) {
		try {
			for (List<String> split : strategyHandler.getSplitLinesFromFile(TAGS_CUSTOM_PATH, true)) {
				Player p = dataPopulator.getPlayerFromName(split.get(0));
				if (p != null) {
					Player toUpdate = updated.getOrDefault(p.getFantasyProsId(), p);
					strategyHandler.addTagsToPlayer(toUpdate, split.get(1));
					updated.put(toUpdate.getFantasyProsId(), toUpdate);
				} else {
					log.error("ERROR cleaning up players :: Player not found = {}", split.get(0));
				}
			}
		} catch (Exception e) {
			log.error("ERROR cleaning up tags", e);
		}
	}
	
	private TreeSet<Player> buildSortedSetFromUpdates(Map<String, Player> updated) {
		TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
		for (Player p : updated.values()) {
			sorted.add(p);
			PlayerCache.addOrUpdatePlayer(p);
		}
		return sorted;
	}

	private void addPlayerNoteIfMissing(FantasyFootballerPlayerTO update, Player p) {
		if (update.getText() != null) {
			String compareText = p.getNotesMetadata().getNotes().stream().map((x) -> x.getTimestamp() + x.getSource()).collect(Collectors.joining(","));
			if (!compareText.contains(update.getTag().name())) {
				p.getNotesMetadata().addNote(new PlayerNote(update.getTag().name(), update.getText(), "Fantasy Footballers"));
			}
		}
	}
	
}
