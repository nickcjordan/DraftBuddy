package com.falifa.draftbuddy.ui.prep.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.DraftZone;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.PlayerTrends;
import com.falifa.draftbuddy.ui.model.player.TrendAnalysis;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;

@Component
public class PlayerTrendsManager {
	
	@Autowired
	private TrendsHelper helper;
	
	/* Round Definitions:
	 * 	- Early: 1-30
	 * 	- Dead Zone: 31-60
	 * 	- Middle: 61-108
	 * 	- Late: 109-180
	 */

	public void buildTrendsForPlayerFromRawData(Player player, NFLTeam team) {
		PlayerTrends trends = new PlayerTrends();
		player.setTrends(trends);
		
		setDraftZone(trends, player);
		trends.hasRookieQb = hasRookieQb(player, team);
		buildPositionMatesCounts(trends, player, team);
		trends.setDepthChartPosition(helper.getDepthChartPosition(player, team));
		
		helper.setAverageRushAttemptsPerGame(player);
		helper.setAverageReceptionsPerGame(player);
		helper.setTargetShare(player, team);
	}
	
	
	public void buildTrendsForPlayerThatRelyOnTeamTrends(Player player, NFLTeam team) {
		if (player.getPosition() == Position.QUARTERBACK) {
			handleQuarterBackTrends(player, team);
		} else if (player.getPosition() == Position.RUNNINGBACK) {
			handleRunningBackTrends(player, team);
		} else if (player.getPosition() == Position.WIDERECEIVER) {
			handleWideReceiverTrends(player, team);
		} else if (player.getPosition() == Position.TIGHTEND) {
			handleTightEndTrends(player, team);
		}
	}


	private void handleQuarterBackTrends(Player player, NFLTeam team) {
		if (player.getSleeperData() != null) {
			
			if (player.getTrends().isInLateRounds()) {
				// Aim for first- and second-year passers 
				Integer yearsInTheLeague = player.getSleeperData().getYearsExp();
				if (yearsInTheLeague == 0 || yearsInTheLeague == 1) {
					player.getTrendAnalysis().add(new TrendAnalysis("1st or 2nd year QB in Late Rounds", 1));
				}
				
				// Look for quarterbacks with pass-catching teammates who have high average draft positions
				if (team.getTrends().getPassCatcherDraftRank() < 17) { // top half of league
					player.getTrendAnalysis().add(new TrendAnalysis("Late Round QB with high average ADP of pass catchers", 1));
				}
				
			}
			
			// Konami code says "rushing" qb has > 5.5 rushing attempts per game
			Double avgRushAttemptsPerGame = player.getPositionalStats().getPriorAverageRushAttemptsPerGame();
			if (avgRushAttemptsPerGame > 5.5) {
				player.getTrendAnalysis().add(new TrendAnalysis("Rushing QB (" + avgRushAttemptsPerGame + " attempts per game > 5.5)", 1));
			}
		}
	}


	private void handleTightEndTrends(Player player, NFLTeam team) {
		if (!player.getTrends().isInEarlyRounds()) { // non-elite
			// TE1 breakouts
			Integer positionalAdp = helper.getPositionalADP(player);
			if (positionalAdp <= 12) {
				// Target team top pass-catchers
				List<Player> passCatchers = helper.getPassCatchersByADP(team);
				if (passCatchers.get(0).getFantasyProsId().equals(player.getFantasyProsId())) {
					player.getTrendAnalysis().add(new TrendAnalysis("TE1 that is top pass catcher for team", 1));
				}
				
				
				// Strong offensive situation is a big plus
				if (team.getTrends().getDraftRank() <= 16) { // top half of teams
					player.getTrendAnalysis().add(new TrendAnalysis("TE1 with top half offense (" + team.getTrends().getDraftRank() + " by Avg ADP)", 1));
				}
			} else {
				// Offensive situation not a huge focus, but pairing with a top-six quarterback by ADP is
				if (helper.hasTopSixQbByADP(team)) {
					player.getTrendAnalysis().add(new TrendAnalysis("TE with top 6 QB by ADP", 1));
				}
				
				// Target second- or third-year tight ends
				if (player.getSleeperData() != null && player.getSleeperData().getYearsExp() != null) {
					Integer exp = player.getSleeperData().getYearsExp();
					if (exp == 1 || exp == 2) {
						player.getTrendAnalysis().add(new TrendAnalysis("2nd or 3rd year TE", 1));
					}
				}
			}
			
		}
	}


	private void handleWideReceiverTrends(Player player, NFLTeam team) {
		if (player.getTrends().isInEarlyRounds()) {
			// avoid super old wideouts (>=10 years in league)
			if (player.getSleeperData() != null && player.getSleeperData().getYearsExp() >= 10) {
				player.getTrendAnalysis().add(new TrendAnalysis("Old WR in Early Rounds (" + player.getSleeperData().getYearsExp() + "years exp)", -1));
			}
			
		}
		else if (player.getTrends().isInDeadZone()) {
			// target year 1-3 players
			if (player.getSleeperData() != null) {
				Integer yearsExp = player.getSleeperData().getYearsExp();
				if (yearsExp < 3) {
					player.getTrendAnalysis().add(new TrendAnalysis("Year 1-3 WR in Alive Zone (" + player.getSleeperData().getYearsExp() + "years exp)", 1));
				} 
			}
			
			// Ideally avoid number-two pass-catchers by team ADP; Can get them if there are three from a team getting drafted before the alive zone ends
			List<Player> passCatchers = helper.getPassCatchersByADP(team);
			int index = 0;
			for (int i = 0; i < passCatchers.size(); i++) {
				if (passCatchers.get(i).getFantasyProsId().equals(player.getFantasyProsId())) {
					index = i;
				}
			}
			if (index > 0) {
				int deadZoneOrEarlierCount = 0;
				for (Player catcher : passCatchers) {
					if (catcher.getTrends().isInEarlyRounds() || catcher.getTrends().isInDeadZone()) {
						deadZoneOrEarlierCount++;
					}
				}
				if (deadZoneOrEarlierCount >= 3) {
					player.getTrendAnalysis().add(new TrendAnalysis("WR from team with 3+ pass catchers through Dead Zone", 1));
				} else {
					player.getTrendAnalysis().add(new TrendAnalysis("#2 or higher pass catcher by team ADP", -1));
				}
			}
			
			
			// dont want mobile qb
			if (helper.hasMobileQb(team)) {
				player.getTrendAnalysis().add(new TrendAnalysis("Has a rushing QB (>5.5 attempts per game)", -1));
			}
			
			// dont want rookie qb
			if (player.getTrends().isHasRookieQb()) {
				player.getTrendAnalysis().add(new TrendAnalysis("Has a rookie QB", -1));
			}
		}
		else if (player.getTrends().isInMiddleRounds()) {
			// target ambiguous situations (teams with no early-round pass-catchers, but multiple going in the middle rounds)
			List<Player> passCatchers = helper.getPassCatchersByADP(team);
			int earlyRoundCount = 0;
			int deadZoneCount = 0;
			int middleRoundCount = 0;
			for (Player catcher : passCatchers) {
				if (catcher.getTrends().isInEarlyRounds()) {
					earlyRoundCount++;
				} else if (catcher.getTrends().isInDeadZone()) {
					deadZoneCount++;
				} else if (catcher.getTrends().isInMiddleRounds()) {
					middleRoundCount++;
				}
			}
			if (earlyRoundCount == 0 && deadZoneCount == 0 && middleRoundCount > 1) {
				player.getTrendAnalysis().add(new TrendAnalysis("WR on team with no Early Round or Dead Zone pass-catchers, but multiple in the Middle Rounds", 1));
			} 
			
			// aim for year 1, 2, 5, and 6
			if (player.getSleeperData() != null) {
				Integer yearsExp = player.getSleeperData().getYearsExp();
				if (yearsExp == 0 || yearsExp == 1 || yearsExp == 4 || yearsExp == 5) {
					player.getTrendAnalysis().add(new TrendAnalysis("Year 1, 2, 5, or 6 WR in Middle Rounds (" + player.getSleeperData().getYearsExp() + "years exp)", 1));
				} 
			}
			// dont want rookie qb
			if (player.getTrends().isHasRookieQb()) {
				player.getTrendAnalysis().add(new TrendAnalysis("Has a rookie QB", -1));
			}
		}
		else if (player.getTrends().isInLateRounds()) {
			// dont want players past year 5
			if (player.getSleeperData() != null) {
				Integer yearsExp = player.getSleeperData().getYearsExp();
				if (yearsExp > 5) {
					player.getTrendAnalysis().add(new TrendAnalysis("Avoid players past year 5 in Late Rounds (" + player.getSleeperData().getYearsExp() + "years exp)", -1));
				} 
			}
			
			// look for rookies
			if (player.isRookie()) {
				player.getTrendAnalysis().add(new TrendAnalysis("Rookie WR in Late Rounds", 1));
			}
		}
		
		// target good offenses
		if (team.getTrends().getDraftRank() <= 16) { // top half of teams
			player.getTrendAnalysis().add(new TrendAnalysis("WR in top half offense (" + team.getTrends().getDraftRank() + " by Avg ADP)", 1));
		}
	}


	private void handleRunningBackTrends(Player player, NFLTeam team) {
		if (player.getTrends().isInEarlyRounds()) {
			// Target backs without top-100 or even top-150 teammates
			lookForRbWithNoTopPositionMates(player, team);
		}
		else if (player.getTrends().isInDeadZone()) {
			// deadzone rookies
			if (player.getDraftingDetails().isRookie()) {
				player.getTrendAnalysis().add(new TrendAnalysis("Rookie RB in Dead Zone", 1));
			}
			
			// better offense = better breakout
			if (team.getTrends().getDraftRank() <= 16) { // top half of teams
				player.getTrendAnalysis().add(new TrendAnalysis("Dead Zone RB in top half offense (" + team.getTrends().getDraftRank() + "by Avg ADP)", 1));
			}
		}
		else if (player.getTrends().isInMiddleRounds()) {
			// ambiguous backfields "RB2 with a dead-zone teammate"
			List<Player> potentialDeadZoneMates = helper.getPositionMatesFromZone(DraftZone.DEAD_ZONE, player, team);
			if (potentialDeadZoneMates.size() > 0) {
				player.getTrendAnalysis().add(new TrendAnalysis("RB2 with a Dead Zone position mate (" + potentialDeadZoneMates.get(0).getPlayerName() + ")", 1));
			}
			
			// avoid if they have early round teammates
			Player potentialEarlyRoundMate = getEarlyRoundPositionMate(player, team);
			if (potentialEarlyRoundMate != null) {
				player.getTrendAnalysis().add(new TrendAnalysis("RB2 with an Early Round position mate (" + potentialEarlyRoundMate.getPlayerName() + ")", -1));
			}
			
			// avoid if they have rookie qb
			if (player.getTrends().isHasRookieQb()) {
				player.getTrendAnalysis().add(new TrendAnalysis("Has a rookie QB", -1));
			}
		}
		else if (player.getTrends().isInLateRounds()) {
			// ambiguous backfields "RB2 with a dead-zone teammate"
			List<Player> potentialDeadZoneMates = helper.getPositionMatesFromZone(DraftZone.DEAD_ZONE, player, team);
			if (potentialDeadZoneMates.size() > 0) {
				player.getTrendAnalysis().add(new TrendAnalysis("RB2 with a Dead Zone position mate (" + potentialDeadZoneMates.get(0).getPlayerName() + ")", 1));
			}
			// Old running backs are fine, but rookies provide great second-half production
			if (player.isRookie()) { 
				player.getTrendAnalysis().add(new TrendAnalysis("Rookie RB in Late Rounds", 1));
			}
			// focus on rb with good offense
			if (team.getTrends().getDraftRank() <= 16) { // top half of teams
				player.getTrendAnalysis().add(new TrendAnalysis("Late Round RB in top half offense (" + team.getTrends().getDraftRank() + "by Avg ADP)", 1));
			}
		}
		
		// pass catchers (at least 10% target share)
		if (player.getPositionalStats() != null && player.getPositionalStats().getPriorTargetSharePercentage() != null && player.getPositionalStats().getPriorTargetSharePercentage() >= 10.0) {
			player.getTrendAnalysis().add(new TrendAnalysis("Pass Catching RB (>10% target share: " + player.getPositionalStats().getPriorTargetSharePercentage() + "%)", 1));
		}
		
		
	}
	
	private Player getEarlyRoundPositionMate(Player player, NFLTeam team) {
		List<Player> mates = helper.getPositionMatesFromZone(DraftZone.EARLY_ROUNDS, player, team);
		return mates.size() > 0 ? mates.get(0) : null;
	}


	private void lookForRbWithNoTopPositionMates(Player player, NFLTeam team) {
		List<Player> matesInTopHundred = new ArrayList<Player>();
		List<Player> matesInTopHundredFifty = new ArrayList<Player>();
		for (Player mate : team.getPlayersByPosition(Position.RUNNINGBACK)) {
			if (!player.getFantasyProsId().equals(mate.getFantasyProsId())) {
				if (Integer.valueOf(mate.getRankMetadata().getAdp()) < 101) {
					matesInTopHundred.add(mate);
				}
				if (Integer.valueOf(mate.getRankMetadata().getAdp()) < 151) {
					matesInTopHundredFifty.add(mate);
				}
			}
		}
		if (matesInTopHundredFifty.size() == 0) {
			player.getTrendAnalysis().add(new TrendAnalysis("Early round RB without top 150 ADP position mate", 1));
		} else if (matesInTopHundred.size() == 0) {
			player.getTrendAnalysis().add(new TrendAnalysis("Early round RB without top 100 ADP position mate", 1));
		} 
	}


	private void setDraftZone(PlayerTrends trends, Player player) {
		if (helper.isInEarlyRounds(player)) {
			trends.setInEarlyRounds(true);
			trends.setDraftZone(DraftZone.EARLY_ROUNDS);
		} else if (helper.isInDeadZone(player)) {
			trends.setInDeadZone(true);
			trends.setDraftZone(DraftZone.DEAD_ZONE);
		} else if (helper.isInMiddleRounds(player)) {
			trends.setInMiddleRounds(true);
			trends.setDraftZone(DraftZone.MIDDLE_ROUNDS);
		} else if (helper.isInLateRounds(player)) {
			trends.setInLateRounds(true);
			trends.setDraftZone(DraftZone.LATE_ROUNDS);
		} else {
			trends.setDraftZone(DraftZone.DART_THROW);
		}
		
	}


	private void buildPositionMatesCounts(PlayerTrends trends, Player player, NFLTeam team) {
		int early = 0;
		int deadzone = 0;
		int middle = 0;
		int late = 0;
		for (Player mate : team.getPlayersByPosition(player.getPosition())) {
			if (!mate.getFantasyProsId().equals(player.getFantasyProsId())) {
				if (helper.isInEarlyRounds(mate)) { early++; }
				else if (helper.isInDeadZone(mate)) { deadzone++; }
				else if (helper.isInMiddleRounds(mate)) { middle++; }
				else if (helper.isInLateRounds(mate)) { late++; }
			}
		}
		trends.positionMatesInEarlyRounds = early;
		trends.positionMatesInDeadZone = deadzone;
		trends.positionMatesInMiddleRounds = middle;
		trends.positionMatesInLateRounds = late;
	}


	private boolean hasRookieQb(Player player, NFLTeam team) {
		Player startingQb = helper.getHighestDraftedPlayerFromPosition(team.getPlayersByPosition(Position.QUARTERBACK));
		return startingQb.getDraftingDetails().isRookie();
	}




}
