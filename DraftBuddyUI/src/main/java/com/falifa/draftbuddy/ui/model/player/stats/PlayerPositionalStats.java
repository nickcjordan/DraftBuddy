package com.falifa.draftbuddy.ui.model.player.stats;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.falifa.draftbuddy.ui.draft.compare.StatCategoryByWeekComparator;

public class PlayerPositionalStats {
	
	private String projectedTotalPoints;
	private String projectedAveragePointsPerGame;
	private String priorTotalPoints;
	private String priorAveragePointsPerGame;
	private String priorTotalTargets;
	private String priorAverageTargetsPerGame;
	private Map<String, StatisticCategory> priorStatsByWeekNumber; 
	private Integer totalGamesWithFantasyPointsPriorYear;
	private Double priorAverageRushAttemptsPerGame;
	private Double priorAverageReceptionsPerGame;
	private Double priorTargetSharePercentage;
	
	public PlayerPositionalStats() {
		this.priorStatsByWeekNumber = new TreeMap<String, StatisticCategory>(new StatCategoryByWeekComparator());
	}
	
	public String getProjectedTotalPoints() {
		return projectedTotalPoints;
	}
	public void setProjectedTotalPoints(String projectedTotalPoints) {
		this.projectedTotalPoints = projectedTotalPoints;
	}
	public String getProjectedAveragePointsPerGame() {
		return projectedAveragePointsPerGame;
	}
	public void setProjectedAveragePointsPerGame(String projectedAveragePointsPerGame) {
		this.projectedAveragePointsPerGame = projectedAveragePointsPerGame;
	}
	public String getPriorTotalPoints() {
		return priorTotalPoints;
	}
	public void setPriorTotalPoints(String priorTotalPoints) {
		this.priorTotalPoints = priorTotalPoints;
	}
	public String getPriorAveragePointsPerGame() {
		return priorAveragePointsPerGame;
	}
	public void setPriorAveragePointsPerGame(String priorAveragePointsPerGame) {
		this.priorAveragePointsPerGame = priorAveragePointsPerGame;
	}
	public String getPriorTotalTargets() {
		return priorTotalTargets;
	}
	public void setPriorTotalTargets(String priorTotalTargets) {
		this.priorTotalTargets = priorTotalTargets;
	}
	public String getPriorAverageTargetsPerGame() {
		return priorAverageTargetsPerGame;
	}
	public void setPriorAverageTargetsPerGame(String priorAverageTargetsPerGame) {
		this.priorAverageTargetsPerGame = priorAverageTargetsPerGame;
	}
	public Map<String, StatisticCategory> getPriorStatsByWeekNumber() {
		return priorStatsByWeekNumber;
	}
	public void setPriorStatsByWeekNumber(Map<String, StatisticCategory> priorStatsByWeekNumber) {
		this.priorStatsByWeekNumber = priorStatsByWeekNumber;
	}
	public void addWeekStatCategory(StatisticCategory cat) {
		priorStatsByWeekNumber.put(cat.getName(), cat);
	}

	public Integer getTotalGamesWithFantasyPointsPriorYear() {
		return totalGamesWithFantasyPointsPriorYear == null ? 0 : totalGamesWithFantasyPointsPriorYear;
	}

	public void setTotalGamesWithFantasyPointsPriorYear(Integer totalGamesWithFantasyPointsPriorYear) {
		this.totalGamesWithFantasyPointsPriorYear = totalGamesWithFantasyPointsPriorYear;
	}

	public Double getPriorAverageRushAttemptsPerGame() {
		return priorAverageRushAttemptsPerGame == null ? 0.0 : priorAverageRushAttemptsPerGame;
	}

	public void setPriorAverageRushAttemptsPerGame(Double priorAverageRushAttemptsPerGame) {
		this.priorAverageRushAttemptsPerGame = priorAverageRushAttemptsPerGame;
	}

	public Double getPriorAverageReceptionsPerGame() {
		return priorAverageReceptionsPerGame == null ? 0.0 : priorAverageReceptionsPerGame;
	}

	public void setPriorAverageReceptionsPerGame(Double priorAverageReceptionsPerGame) {
		this.priorAverageReceptionsPerGame = priorAverageReceptionsPerGame;
	}

	public Double getPriorTargetSharePercentage() {
		return priorTargetSharePercentage == null ? 0.0 : priorTargetSharePercentage;
	}

	public void setPriorTargetSharePercentage(Double priorTargetSharePercentage) {
		this.priorTargetSharePercentage = priorTargetSharePercentage;
	}
	
}
