package com.falifa.draftbuddy.ui.prep.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.DraftZone;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.constants.StatCategory;
import com.falifa.draftbuddy.ui.draft.compare.PlayerADPComparator;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticCategory;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticValue;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.prep.PlayerCache;

@Component
public class TrendsHelper {
	
	public Player getHighestDraftedPlayerFromPosition(List<Player> players) {
		sortPlayersByADP(players);
		return players.get(0);
	}
	
	public List<Player> sortPlayersByADP(List<Player> players) {
		Collections.sort(players, new PlayerADPComparator());
		return players;
	}
	
	public boolean isInEarlyRounds(Player player) {
		Integer adp = Integer.valueOf(player.getRankMetadata().getAdp());
		return adp <= DraftZone.EARLY_ROUNDS.getEnd();
	}
	
	public boolean isInDeadZone(Player player) {
		Integer adp = Integer.valueOf(player.getRankMetadata().getAdp());
		return adp >= DraftZone.DEAD_ZONE.getStart() && adp <= DraftZone.DEAD_ZONE.getEnd();
	}
	
	public boolean isInMiddleRounds(Player player) {
		Integer adp = Integer.valueOf(player.getRankMetadata().getAdp());
		return adp >= DraftZone.MIDDLE_ROUNDS.getStart() && adp <= DraftZone.MIDDLE_ROUNDS.getEnd();
	}
	
	public boolean isInLateRounds(Player player) {
		Integer adp = Integer.valueOf(player.getRankMetadata().getAdp());
		return adp >= DraftZone.LATE_ROUNDS.getStart() && adp <= DraftZone.LATE_ROUNDS.getEnd();
	}

	public int getDepthChartPosition(Player player, NFLTeam team) {
		List<Player> mates = team.getPlayersByPosition(player.getPosition());
		sortPlayersByADP(mates);
		int current = 1;
		int depth = 0;
		for (Player mate : mates) {
			if (mate.getFantasyProsId().equals(player.getFantasyProsId())) {
				depth = current;
			}
			current++;
		}
		return depth;
	}

	public void setAverageRushAttemptsPerGame(Player player) {

		double returnVal = 0.0;
		Integer gamesPlayed = player.getPositionalStats().getTotalGamesWithFantasyPointsPriorYear();
		if (gamesPlayed != null) {

			StatisticCategory rushingStats = player.getPriorRawStatsDetails().getStatCategory(StatCategory.RUSHING.getValue());
			if (rushingStats != null) {

				for (String key : rushingStats.getStats().keySet()) {
					if (key.equals("ATT")) {
						StatisticValue rushAttempts = rushingStats.getStats().get(key);
						if (rushAttempts != null) {
							int attempts = Integer.valueOf(rushAttempts.getValue());
							double rushAttemptsPerGamePlayed = ((double) attempts / gamesPlayed);
							double rounded = Math.round(rushAttemptsPerGamePlayed * 10) / 10;
							returnVal = rounded;
						}
					}

				}
			}
		}
		player.getPositionalStats().setPriorAverageRushAttemptsPerGame(returnVal);
	}
	
	public void setAverageReceptionsPerGame(Player player) {
		double returnVal = 0.0;
		Integer gamesPlayed = player.getPositionalStats().getTotalGamesWithFantasyPointsPriorYear();
		if (gamesPlayed != null) {

			StatisticCategory catchStats = player.getPriorRawStatsDetails().getStatCategory(StatCategory.RECEIVING.getValue());
			if (catchStats != null) {

				for (String key : catchStats.getStats().keySet()) {
					if (key.equals("REC")) {
						StatisticValue receptions = catchStats.getStats().get(key);
						if (receptions != null) {
							int val = Integer.valueOf(receptions.getValue());
							double valPerGamePlayed = ((double) val / gamesPlayed);
							double rounded = Math.round(valPerGamePlayed * 10) / 10;
							returnVal = rounded;
						}
					}

				}
			}
		}
		player.getPositionalStats().setPriorAverageReceptionsPerGame(returnVal);
	}

	public void setTargetShare(Player player, NFLTeam team) {
		double returnVal = 0.0;
		if (player.getPositionalStats() != null) {
			if (player.getPositionalStats().getPriorTotalTargets() != null) {
				if (team.getStats() != null && team.getStats().getTotalTargets() != null) {
					Integer teamTargets = team.getStats().getTotalTargets();
					int playerTargets = Integer.valueOf(player.getPositionalStats().getPriorTotalTargets());
					double targetShare = ((double) playerTargets / teamTargets)*100;
					double rounded = Math.round(targetShare * 10) / 10;
					returnVal = rounded;
				}
			}
		}
		player.getPositionalStats().setPriorTargetSharePercentage(returnVal);
	}
	
	public List<Player> getPositionMatesFromZone(DraftZone zone, Player player, NFLTeam team) {
		List<Player> matesInZone = new ArrayList<Player>();
		List<Player> mates = team.getPlayersByPosition().get(player.getPosition());
		sortPlayersByADP(mates);
		for (Player mate : mates) {
			if (!mate.getFantasyProsId().equals(player.getFantasyProsId())) {
				if (mate.getTrends().getDraftZone() == zone) {
					matesInZone.add(mate);
				}
			}
		}
		return matesInZone;
	}

	public List<Player> getPassCatchersByADP(NFLTeam team) {
		List<Player> catchers = new ArrayList<Player>();
		catchers.addAll(team.getPlayersByPosition(Position.WIDERECEIVER));
		catchers.addAll(team.getPlayersByPosition(Position.TIGHTEND));
		sortPlayersByADP(catchers);
		return catchers;
	}

	public boolean hasMobileQb(NFLTeam team) {
		Player qb = getHighestDraftedPlayerFromPosition(team.getPlayersByPosition(Position.QUARTERBACK));
		Double avgRushAttemptsPerGame = qb.getPositionalStats().getPriorAverageRushAttemptsPerGame();
		return avgRushAttemptsPerGame > 5.5;
	}

	public boolean hasTopSixQbByADP(NFLTeam team) {
		Player qb = getHighestDraftedPlayerFromPosition(team.getPlayersByPosition(Position.QUARTERBACK));
		Integer positionalAdp = getPositionalADP(qb);
		if (positionalAdp != null && positionalAdp <= 6) {
			return true;
		} else {
			return false;
		}
	}
	
	public Integer getPositionalADP(Player player) {
		List<Player> all = PlayerCache.getPlayers().values().stream().filter(p -> p.getPosition() == player.getPosition()).collect(Collectors.toList());
		sortPlayersByADP(all);
		int index = 1;
		for (Player p : all) {
			if (p.getFantasyProsId().equals(player.getFantasyProsId())) {
				return index;
			} else {
				index++;
			}
		}
		return null;
	}
}
