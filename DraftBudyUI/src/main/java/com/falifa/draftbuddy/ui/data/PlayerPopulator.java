package com.falifa.draftbuddy.ui.data;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.model.PlayerTO;
import com.falifa.draftbuddy.api.model.PositionStatsDetails;
import com.falifa.draftbuddy.ui.constants.DataSourcePaths;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticCategory;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticValue;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;

@Component
public class PlayerPopulator {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerPopulator.class);
	
	private static final String ALL = "ALL";
	private static final String MISC = "MISC";
	private static final String TOTAL_FANTASY_POINTS = "FPTS";

	@Value("${weeksInSeason}")
	private int weeksInSeason;

	@Autowired
	private PositionalStatsBuilder positionalStatsBuilder;
	
	public void populatePlayerProjectedTotalsFields(Player player) {
		try  {
			Map<String, StatisticCategory> stats = player.getProjectedRawStatsDetails().getStats();
			if (stats.size() > 0) {
				String projectedTotal = stats.containsKey(ALL) ? stats.get(ALL).getStat(TOTAL_FANTASY_POINTS) : stats.containsKey(MISC) ? stats.get(MISC).getStat(TOTAL_FANTASY_POINTS) : null;
				if (projectedTotal != null) {
					int totalVal = Double.valueOf(projectedTotal).intValue();
					Double avg = totalVal / Double.valueOf(weeksInSeason);
					player.getPositionalStats().setProjectedTotalPoints(String.valueOf(totalVal));
					player.getPositionalStats().setProjectedAveragePointsPerGame(String.valueOf(avg));
				} else {
					log.error("ERROR :: first null pointer trying to extract total projected points for player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId());
				}
			} else {
				log.debug("No FantasyPros projection data found when trying to sum projection totals :: player={} :: id={}", player.getPlayerName(), player.getFantasyProsId());
			}
		} catch (NullPointerException e) {
			log.error("ERROR :: null pointer trying to extract total projected points for player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId());
		} catch (Exception e) {
			log.error("ERROR setting total projected points for player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId(), e);
		}
	}
	
	public void populatePlayerPriorTotalsFields(Player player) {
		try  {
			Map<String, StatisticCategory> stats = player.getPriorRawStatsDetails().getStats();
			if (stats.size() > 0) {
				String priorTotal = stats.containsKey(MISC) ? stats.get(MISC).getStat(TOTAL_FANTASY_POINTS) : null;
				if (priorTotal != null) {
					int totalVal = Double.valueOf(priorTotal).intValue();
					Double avg = totalVal / Double.valueOf(player.getPriorRawStatsDetails().getWeeksOfData());
					player.getPositionalStats().setPriorTotalPoints(String.valueOf(totalVal));
					player.getPositionalStats().setPriorAveragePointsPerGame(String.valueOf(avg));
				} else {
					log.debug("ERROR :: first null pointer trying to extract total prior points for player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId());
				}
			} else {
				log.debug("No FantasyPros projection data found when trying to sum projection totals :: player={} :: id={}", player.getPlayerName(), player.getFantasyProsId());
			}
		} catch (NullPointerException e) {
			log.error("ERROR :: null pointer trying to extract total projected points for player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId());
		} catch (Exception e) {
			log.error("ERROR setting total projected points for player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId(), e);
		}
	}

	public void populatePlayerWithPriorStatsFromTO(Player player, PlayerTO to) {
		try  {
			player.getPictureMetadata().setSmallPicLink(to.getSmallImageUrl());
			player.getPictureMetadata().setPicLink(to.getImageUrl());
			handleStatsByWeek(player, to.getStatsByWeek());
		} catch (NullPointerException e) {
			log.error("ERROR :: null pointer trying to populate stats from API :: player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId());
		} catch (Exception e) {
			log.error("ERROR trying to populate stats from API :: player=" + player.getPlayerName() + " :: id=" + player.getFantasyProsId(), e);
		}
	}

	private void handleStatsByWeek(Player player, Map<String, PositionStatsDetails> statsByWeek) {
			 try {
				 player.getPriorRawStatsDetails().setWeeksOfData(statsByWeek.size());
				 if (player.getPosition() != null) {
					 switch (player.getPosition()) {
						 case QUARTERBACK : positionalStatsBuilder.buildQuarterbackStats(statsByWeek, player); break;
						 case RUNNINGBACK : positionalStatsBuilder.buildRunningbackStats(statsByWeek, player); break;
						 case WIDERECEIVER : positionalStatsBuilder.buildWideReceiverStats(statsByWeek, player); break;
						 case TIGHTEND : positionalStatsBuilder.buildTightEndStats(statsByWeek, player); break;
						 case DEFENSE : positionalStatsBuilder.buildDefenseStats(statsByWeek, player); break;
						 case KICKER : positionalStatsBuilder.buildKickerStats(statsByWeek, player); break;
					 }
				 } else {
					 log.debug("No Position assigned to player when trying to populate player stats from API weekly stats map :: player={} :: id={}", player.getPlayerName(), player.getFantasyProsId());
				 }
			 } catch (Exception e) {
				 log.error("ERROR trying to populate player stats from API weekly stats map :: player={} :: id={} :: error message={}", player.getPlayerName(), player.getFantasyProsId(), e.getMessage());
			 }
		 }

	public void populateProjectedStatsMap(Player player) {
		 for (StatisticCategory category : player.getProjectedRawStatsDetails().getStatsList()) {
			 for (StatisticValue stat : category.getColumns()) {
				 category.getStats().put(stat.getName(), stat);
			 }
		 }
	}
	
}
