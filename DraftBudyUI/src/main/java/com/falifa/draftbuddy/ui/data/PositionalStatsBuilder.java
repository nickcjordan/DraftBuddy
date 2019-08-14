package com.falifa.draftbuddy.ui.data;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.model.PositionStatsDetails;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticCategory;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticValue;

@Component
public class PositionalStatsBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(PositionalStatsBuilder.class);

	private static final String FANTASY_POINTS = "FPTS";
	private static final String PASSING = "PASSING";
	private static final String RECEIVING = "RECEIVING";
	private static final String RUSHING = "RUSHING";
	private static final String MISC = "MISC";
	private static final String ALL = "ALL";
	private static final String ZERO = "0";

	public void buildQuarterbackStats(Map<String, PositionStatsDetails> statsByWeek, Player player) {
		zeroOutPriorTotal(player, MISC);
		for (Entry<String, PositionStatsDetails> entry : statsByWeek.entrySet()) {
			addStatValuesToTotalsForPassing(entry.getValue(), player);
			addStatValuesToTotalsForRushing(entry.getValue(), player);
			addStatValuesToTotalsForMisc(entry.getValue(), player);
			addWeeklyScoringForPlayer(entry, player);
		}
	}

	public void buildRunningbackStats(Map<String, PositionStatsDetails> statsByWeek, Player player) {
		zeroOutPriorTotal(player, MISC);
		for (Entry<String, PositionStatsDetails> entry : statsByWeek.entrySet()) {
			addStatValuesToTotalsForReceiving(entry.getValue(), player);
			addStatValuesToTotalsForRushing(entry.getValue(), player);
			addStatValuesToTotalsForMisc(entry.getValue(), player);
			addWeeklyScoringForPlayer(entry, player);
		}
	}

	public void buildWideReceiverStats(Map<String, PositionStatsDetails> statsByWeek, Player player) {
		zeroOutPriorTotal(player, MISC);
		for (Entry<String, PositionStatsDetails> entry : statsByWeek.entrySet()) {
			addStatValuesToTotalsForReceiving(entry.getValue(), player);
			addStatValuesToTotalsForRushing(entry.getValue(), player);
			addStatValuesToTotalsForMisc(entry.getValue(), player);
			addWeeklyScoringForPlayer(entry, player);
		}
	}

	public void buildTightEndStats(Map<String, PositionStatsDetails> statsByWeek, Player player) {
		zeroOutPriorTotal(player, MISC);
		for (Entry<String, PositionStatsDetails> entry : statsByWeek.entrySet()) {
			addStatValuesToTotalsForReceiving(entry.getValue(), player);
			addStatValuesToTotalsForRushing(entry.getValue(), player);
			addStatValuesToTotalsForMisc(entry.getValue(), player);
			addWeeklyScoringForPlayer(entry, player);
		}
	}

	public void buildDefenseStats(Map<String, PositionStatsDetails> statsByWeek, Player player) {
		zeroOutPriorTotal(player, ALL);
		for (Entry<String, PositionStatsDetails> entry : statsByWeek.entrySet()) {
			addStatValuesToTotalsForAll(entry.getValue(), player);
			addWeeklyScoringForPlayer(entry, player);
		}
	}

	public void buildKickerStats(Map<String, PositionStatsDetails> statsByWeek, Player player) {
		zeroOutPriorTotal(player, ALL);
		for (Entry<String, PositionStatsDetails> entry : statsByWeek.entrySet()) {
			addStatValuesToTotalsForKicker(entry.getValue(), player);
			addStatValuesToTotalsForAll(entry.getValue(), player);
			addWeeklyScoringForPlayer(entry, player);
		}
	}
	
	private void zeroOutPriorTotal(Player player, String categoryName) {
		try {
			player.getPriorRawStatsDetails().getStatCategory(categoryName).getStatisticValue(FANTASY_POINTS).setValue(ZERO);
		} catch (Exception e) {
			log.debug("ERROR setting prior point totals to 0 for player={}", player.getPlayerName());
		}
	}

	private void addStatValuesToTotalsForKicker(PositionStatsDetails week, Player player) {
		StatisticCategory category = player.getPriorRawStatsDetails().getStatCategory(ALL);
		 if (category == null) {
			 category = new StatisticCategory();
			 category.setName(ALL);
			 player.getPriorRawStatsDetails().addStatCategory(category);
		 }
		 category.setColspan(category.getStats().size());
		 addValueToStatCategoryTotal("FG_0_20", week.getFg_0_20(), category);
		 addValueToStatCategoryTotal("FG_20_30", week.getFg_20_30(), category);
		 addValueToStatCategoryTotal("FG_30_40", week.getFg_30_40(), category);
		 addValueToStatCategoryTotal("FG_40_50", week.getFg_40_50(), category);
		 addValueToStatCategoryTotal("FG_50_PLUS", week.getFg_50_plus(), category);
		 addValueToStatCategoryTotal("extraPoints", week.getExtraPoints(), category);
	}

	private void addStatValuesToTotalsForPassing(PositionStatsDetails week, Player player) {
		 StatisticCategory category = player.getPriorRawStatsDetails().getStatCategory(PASSING);
		 if (category == null) {
			 category = new StatisticCategory();
			 category.setName(PASSING);
			 player.getPriorRawStatsDetails().addStatCategory(category);
		 }
		 category.setColspan(category.getStats().size());
		 addValueToStatCategoryTotal("ATT", week.getPassAttempts(), category);
		 addValueToStatCategoryTotal("CMP", week.getPassCompletions(), category);
		 addValueToStatCategoryTotal("YDS", week.getPassYards(), category);
		 addValueToStatCategoryTotal("TDS", week.getPassTouchdowns(), category);
		 addValueToStatCategoryTotal("INTS", week.getInterceptions(), category);
	}
	
	private void addStatValuesToTotalsForRushing(PositionStatsDetails week, Player player) {
		 StatisticCategory category = player.getPriorRawStatsDetails().getStatCategory(RUSHING);
		 if (category == null) {
			 category = new StatisticCategory();
			 category.setName(RUSHING);
			 player.getPriorRawStatsDetails().addStatCategory(category);
		 }
		 category.setColspan(category.getStats().size());
		 addValueToStatCategoryTotal("ATT", week.getRushAttempts(), category);
		 addValueToStatCategoryTotal("YDS", week.getRushYards(), category);
		 addValueToStatCategoryTotal("TDS", week.getRushTouchdowns(), category);
	}
	
	private void addStatValuesToTotalsForReceiving(PositionStatsDetails week, Player player) {
		 StatisticCategory category = player.getPriorRawStatsDetails().getStatCategory(RECEIVING);
		 if (category == null) {
			 category = new StatisticCategory();
			 category.setName(RECEIVING);
			 player.getPriorRawStatsDetails().addStatCategory(category);
		 }
		 category.setColspan(category.getStats().size());
		 addValueToStatCategoryTotal("REC", week.getReceptions(), category);
		 addValueToStatCategoryTotal("YDS", week.getReceivingYards(), category);
		 addValueToStatCategoryTotal("TDS", week.getReceivingTouchdowns(), category);
	}
	
	private void addStatValuesToTotalsForMisc(PositionStatsDetails week, Player player) {
		 StatisticCategory category = player.getPriorRawStatsDetails().getStatCategory(MISC);
		 if (category == null) {
			 category = new StatisticCategory();
			 category.setName(MISC);
			 player.getPriorRawStatsDetails().addStatCategory(category);
		 }
		 category.setColspan(category.getStats().size());
		 addValueToStatCategoryTotal(FANTASY_POINTS, week.getTotalPointsScored(), category);
	}
	
	private void addStatValuesToTotalsForAll(PositionStatsDetails week, Player player) {
		 StatisticCategory category = player.getPriorRawStatsDetails().getStatCategory(ALL);
		 if (category == null) {
			 category = new StatisticCategory();
			 category.setName(ALL);
			 player.getPriorRawStatsDetails().addStatCategory(category);
		 }
		 category.setColspan(category.getStats().size());
		 addValueToStatCategoryTotal(FANTASY_POINTS, week.getTotalPointsScored(), category);
	}

	private void addValueToStatCategoryTotal(String stat, int addition, StatisticCategory category) {
		StatisticValue statistic = category.getStatisticValue(stat);
		if (statistic != null) {
			statistic.setValue(String.valueOf(Integer.valueOf(category.getStat(stat)) + addition));
		} else {
			category.addColumn(stat, String.valueOf(addition));
		}
	}
	
	private void addValueToStatCategoryTotal(String stat, double addition, StatisticCategory category) {
		StatisticValue statistic = category.getStatisticValue(stat);
		if (statistic != null) {
			statistic.setValue(String.valueOf(Double.valueOf(category.getStat(stat)) + addition));
		} else {
			category.addColumn(stat, String.valueOf(addition));
		}
	}

	private void addWeeklyScoringForPlayer(Entry<String, PositionStatsDetails> entry, Player player) {
		StatisticCategory weeklyStat = new StatisticCategory();
		weeklyStat.setName(entry.getKey());
		weeklyStat.setColspan(1);
		weeklyStat.addColumn("FPTS", String.format("%.0f", entry.getValue().getTotalPointsScored()));
		player.getPositionalStats().addWeekStatCategory(weeklyStat);
	}
}
