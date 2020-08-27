package com.falifa.draftbuddy.ui.prep.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.DRAFT_STRATEGY_BY_ROUND_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYER_TAGS_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.draft.compare.PlayerADPComparator;
import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.model.NFLTeam;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.NFLTeamCache;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.api.PlayerNameMatcher;
import com.falifa.draftbuddy.ui.prep.data.StrategyFileHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
public class DataPopulator {
	
	private static final Logger log = LoggerFactory.getLogger(DataPopulator.class);
	
	private static final String PLUS_SIGN = "&#43;";
	private static final CharSequence MINUS_SIGN = "&#45;";

	private Map<String, String> playerNameToIdMap = new HashMap<String, String>();
	
	@Autowired
	private PlayerNameMatcher nameMatcher;
	
	@Autowired
	private StrategyFileHandler strategyHandler;
	
	@Autowired
	private DraftState draftState;
	
	public Map<String, String> getPlayerNameToIdMap() {
		return playerNameToIdMap;
	}

	public void setPlayerNameToIdMap(Map<String, String> playerNameToIdMap) {
		this.playerNameToIdMap = playerNameToIdMap;
	}

	public void populatePlayerNameMap() {
		if (!CollectionUtils.isEmpty(PlayerCache.getPlayers())) {
			for (Player player : PlayerCache.getPlayers().values()) {
				if (player.getPlayerName() != null) {
					playerNameToIdMap.put(nameMatcher.filter(player.getPlayerName()), player.getFantasyProsId());
					nameMatcher.addAlternateNames(player.getPlayerName(), player.getFantasyProsId());
				}
			}
		}
	}

	public void populateManuallySetFieldValuesAndPutOnTeam() {
		if (!CollectionUtils.isEmpty(PlayerCache.getPlayers()) && !CollectionUtils.isEmpty(NFLTeamCache.getNflTeamsByAbbreviation())) {
			for (Player player : PlayerCache.getPlayers().values()) {
				populateRemainingFieldsWithDefaultValues(player);
				if (player.getTeam() != null) {
					NFLTeam team = NFLTeamCache.getNflTeamsByAbbreviation().get(player.getTeam().getAbbreviation());
					if (team != null) {
						team.addPlayer(player);
					}
				}
			}
		}
	}

	public void populateNflTeamSpecificFieldValues() {
		if (!CollectionUtils.isEmpty(NFLTeamCache.getNflTeamsByAbbreviation())) {
			for (NFLTeam team : NFLTeamCache.getNflTeamsByAbbreviation().values()) {
				for (Position pos : Position.values()) {
					try {
						List<Player> positionPlayers = getPlayersByPosition(pos, team);
						Collections.sort(positionPlayers, new PlayerADPComparator());
						Player starter = positionPlayers.get(0);
						for (Player backup : positionPlayers.subList(1, positionPlayers.size() - 1)) {
							starter.getDraftingDetails().addBackup(backup);
						}
					} catch (Exception e) {
						log.debug("Could not populate backups for team {} and position {}", team.getTeam().getFullName(), pos.getAbbrev());
					}
				}
			}
		}
	}
	
	private List<Player> getPlayersByPosition(Position p, NFLTeam team) {
		return team.getPlayersByPosition(p);
	}

	private void populateRemainingFieldsWithDefaultValues(Player player) {
		player.getDraftingDetails().setAvailable(true);
		if (player.getRankMetadata().getOverallRank() == null) {
			player.getRankMetadata().setOverallRank(String.valueOf(PlayerCache.getPlayers().size()));
		}
		if (player.getRankMetadata().getVsAdp() != null) {
			String vs = player.getRankMetadata().getVsAdp();
			Integer vsVal = null;
			if (StringUtils.isNotEmpty(vs)) {
				if (vs.contains(PLUS_SIGN)) {
					vsVal = Integer.valueOf(vs.replace(PLUS_SIGN, "").replace(".0", "")).intValue();
				} else if (vs.contains(MINUS_SIGN)) {
					vsVal = -Integer.valueOf(vs.replace(MINUS_SIGN, "").replace(".0", "")).intValue();
				} else {
					vsVal = Integer.valueOf(vs.replace(".0", "")).intValue();
				}
				if (vsVal != null) {
					player.getDraftingDetails().setVsValueBadgeClass(getValueBadgeClass(vsVal));
					player.getRankMetadata().setVsAdp(String.valueOf(vsVal));
				}
			}
		}
	}
	
	public void populatePlayersWithTags() {
		try {
			for (List<String> split : strategyHandler.getSplitLinesFromFile(PLAYER_TAGS_FILE_PATH, true)) {
				if (split.size() > 1) {
					Player p = getPlayerFromName(split.get(0));
					if (p != null) {
						strategyHandler.addTagsToPlayer(p, split.get(1));
					} else {
						log.error("Could not find player for name={}", split.get(0));
					}
				}
			}
		} catch (Exception e) {
			log.error("ERROR populating players with tags", e);
		}
		cleanupTags();
	}

	private void cleanupTags() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			for (List<String> split : strategyHandler.getSplitLinesFromFile(TAGS_CUSTOM_PATH, true)) {
				Player p = getPlayerFromName(split.get(0));
				if (p != null) {
					sorted.add(p);
				} else {
					log.error("ERROR cleaning up players :: Player not found = {}", split.get(0));
				}
			}
			strategyHandler.updateTagFileWithCleanedUpResults(sorted);
		} catch (Exception e) {
			log.error("ERROR cleaning up tags", e);
		}
	}
	
	public Player getPlayerFromName(String playerName) {
		String id = nameMatcher.findIdForClosestMatchingName(playerName);
		if (id != null) {
			log.debug("Getting player from name :: Found ID={} from player name={}", id, playerName);
			if (PlayerCache.getPlayers().containsKey(id)) {
				return PlayerCache.getPlayers().get(id);
			}
		}
		return null;
	}
	
	public void setCurrentPlayerValue() {
		for (Player p : PlayerCache.getPlayers().values()) {
			int value = draftState.getPickNumber() - Integer.valueOf(p.getRankMetadata().getAdp());
			p.getDraftingDetails().setCurrentPlayerValue(value);
			p.getDraftingDetails().setCurrentPlayerValueBadgeClass(getValueBadgeClass(value));
		}
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
