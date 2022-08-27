package com.falifa.draftbuddy.ui.prep.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.model.team.NFLTeamSOSData;
import com.falifa.draftbuddy.ui.prep.NFLTeamCache;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.data.ModelUpdater;
import com.falifa.draftbuddy.ui.prep.data.StrategyFileHandler;
import com.falifa.draftbuddy.ui.prep.data.model.SleeperADP;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.FFBallersAPI;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.WebJsonExtractor;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.WebJsonPlayerConverter;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ECRData;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.TeamSOSData;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersAPIData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataConsolidator {
	
	private static final Logger log = LoggerFactory.getLogger(DataConsolidator.class);
	
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
	private DataPopulatorHelper dataPopulator;
	
	@Autowired
	private WebJsonExtractor jsonExtractor;
	
	@Autowired
	private WebJsonPlayerConverter playerConverter;
	
	@Autowired
	private FFBallersAPI ballersApi;
	
	@Autowired
	private SleeperADPAPI sleeper;

	public boolean parseAllDataSources() {
		boolean success = true;
		success &= parseAndUpdateFantasyProsRankings();
		dataPopulator.populatePlayerNameMap();
		dataPopulator.populateManuallySetFieldValuesAndPutOnTeam();
		dataPopulator.populateNflTeamSpecificFieldValues();
		dataPopulator.populatePlayersWithTags();
		dataPopulator.setCurrentPlayerValue();

		success &= parseAndUpdateFantasyProsStrengthOfSchedule();
		success &= parseAndUpdateFantasyFootballersRankings();
		
		success = parseFantasyProsFiles(success);
		success &= jsonFileManager.parseAndUpdateStatsFromAPI();
		success &= jsonFileManager.downloadAndSetPlayerImages();
		success &= parseAndUpdateFantasyFootballers();
		success &= parseAndUpdateSleeperADP();
		
		
		modelUpdater.clearFiltersAndSorts();
		success &= PlayerCache.updatePlayerJsonFileWithCachedData();
		success &= NFLTeamCache.updateNflJsonFileWithCachedData();
		return success;
	}

	private boolean parseAndUpdateSleeperADP() {
		List<SleeperADP> adp = sleeper.getADP();
		for (int i = 0; i < adp.size(); i++) {
			SleeperADP val = adp.get(i);
			Player player = dataPopulator.getPlayerFromName(val.getName());
			if (player != null) {
				player.getRankMetadata().setSleeperAdpVal(String.valueOf(val.getAdp()));
				player.getRankMetadata().setSleeperAdp(String.valueOf(i));
			}
		}
		return true;
	}

	private boolean parseAndUpdateFantasyFootballersRankings() {
//		String htmlTable = htmlParser.parseTableDataFromFantasyFootballersQBRankings();
//		allPositionSuccess &= extractor.extracPlayerDataFromFantasyProsADP(htmlTable);
		
		FFBallersAPIData ballersData = ballersApi.getFFBallersData();
		
		Map<String, Player> playerData = playerConverter.convertToPlayerData(ballersData);
		return PlayerCache.addPlayerDataToExisting(playerData);
		
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
//		String htmlTable = htmlParser.parseTableDataFromFantasyProsRankings();
//		Map<String, Player> playerData = extractor.extractPlayerDataFromFantasyProsRankings(htmlTable);
		
		ECRData ecrData = jsonExtractor.getECRDataFromFile();
		
		Map<String, Player> playerData = playerConverter.convertToPlayerData(ecrData);
		return PlayerCache.addPlayerDataToExisting(playerData);
	}
	
	private boolean parseAndUpdateFantasyProsStrengthOfSchedule() {
		boolean allSuccess = true;
		Map<String, LinkedHashMap> sosData = jsonExtractor.getSOSDataFromFile();
		for (Entry<String, LinkedHashMap> data : sosData.entrySet()) {
			allSuccess &= updateTeamWithSOSData(data);
		}
		return allSuccess;
//		Map<String, Player> playerData = playerConverter.convertToPlayerData(ecrData);
//		return PlayerCache.addPlayerDataToExisting(playerData);
	}

	private boolean updateTeamWithSOSData(Entry<String, LinkedHashMap> data) {
		try {
			NFLTeam team = NFLTeamCache.getNflTeamsByAbbreviation().get(data.getKey());
			TeamSOSData sosData = buildSOSData(data.getValue());
			NFLTeamSOSData nflSosData = buildNFLSOSData(sosData);
			team.setSosData(nflSosData);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private NFLTeamSOSData buildNFLSOSData(TeamSOSData sosData) {
		NFLTeamSOSData data = new NFLTeamSOSData();
		data.setGames(sosData.getGames());
		
		data.setQbRank(sosData.getQbRank());
		data.setRbRank(sosData.getRbRank());
		data.setWrRank(sosData.getWrRank());
		data.setTeRank(sosData.getTeRank());
		data.setDstRank(sosData.getDstRank());
		data.setkRank(sosData.getkRank());
		
		data.setQbStars(sosData.getQbStars());
		data.setRbStars(sosData.getRbStars());
		data.setWrStars(sosData.getWrStars());
		data.setTeStars(sosData.getTeStars());
		data.setDstStars(sosData.getDstStars());
		data.setkStars(sosData.getkStars());
		return data;
	}

	private TeamSOSData buildSOSData(LinkedHashMap map) {
		TeamSOSData data = new TeamSOSData();
		data.setGames((Integer)(map.get("games")));
		
		data.setQbRank((Integer)(map.get("qb_rank")));
		data.setRbRank((Integer)(map.get("rb_rank")));
		data.setWrRank((Integer)(map.get("wr_rank")));
		data.setTeRank((Integer)(map.get("te_rank")));
		data.setDstRank((Integer)(map.get("dst_rank")));
		data.setkRank((Integer)(map.get("k_rank")));
		
		data.setQbStars(getDouble(map.get("qb_stars")));
		data.setRbStars(getDouble(map.get("rb_stars")));
		data.setWrStars(getDouble(map.get("wr_stars")));
		data.setTeStars(getDouble(map.get("te_stars")));
		data.setDstStars(getDouble(map.get("dst_stars")));
		data.setkStars(getDouble(map.get("k_stars")));
		return data;
	}
	
	private Double getDouble(Object val) {
		try {
			return (Double) (val);
		} catch (Exception e) {
			return new Double((double) ((Integer) val));
		}
	}

	private boolean parseAndUpdateFantasyProsADP() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsADP();
		return extractor.extractPlayerDataFromFantasyProsADP(htmlTable);
	}

	private boolean parseAndUpdateFantasyProsNotes() {
		String htmlTable = htmlParser.parseTableDataFromFantasyProsNotes();
		return extractor.extractPlayerDataFromFantasyProsNotes(htmlTable);
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
				if (p != null && split.get(1) != null) {
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
