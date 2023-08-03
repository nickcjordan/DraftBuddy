package com.falifa.draftbuddy.ui.prep.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.TrendAnalysis;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.model.team.NflTrends;

@Component
public class TeamTrendsManager {
	

	@Autowired
	private TrendsHelper helper;
	
	/* Round Definitions:
	 * 	- Early: 1-30
	 * 	- Dead Zone: 31-60
	 * 	- Middle: 61-108
	 * 	- Late: 109-180
	 */
	
	public void buildAllTeamTrends(Collection<NFLTeam> collection) {
		List<NFLTeam> teams = new ArrayList<NFLTeam>(collection);
		for (NFLTeam team : teams) {
			buildTrendsForTeam(team);
		}
		
		sortByPassCatcherDraftZoneScore(teams);
		int passCatcherRank = 1;
		System.out.println("\nPass Catcher Draft Rank:");
		for (NFLTeam team : teams) {
			team.getTrends().setPassCatcherDraftRank(passCatcherRank++);
			System.out.println(team.getTrends().getPassCatcherDraftRank() + ") " + team.getTeam().getFullName() + " :: " + team.getTrends().getPassCatcherDraftZoneScore() + " :: " + team.getTrends().getPassCatcherAverageAdp());
		}
		
		sortByDraftZoneScore(teams);
		int rank = 1;
		System.out.println("\nAll Player Draft Rank:");
		for (NFLTeam team : teams) {
			team.getTrends().setDraftRank(rank++);
			System.out.println(team.getTrends().getDraftRank() + ") " + team.getTeam().getFullName() + " :: " + team.getTrends().getDraftZoneScore() + " :: " + team.getTrends().getAverageAdp());
		}
		
	}


	private void sortByDraftZoneScore(List<NFLTeam> teams) {
		Collections.sort(teams, new Comparator<NFLTeam>() {
		    public int compare(NFLTeam a, NFLTeam b) {
		        int val = b.getTrends().getDraftZoneScore().compareTo(a.getTrends().getDraftZoneScore());
		        if (val == 0) {
		        	return a.getTrends().getAverageAdp().compareTo(b.getTrends().getAverageAdp());
		        } else {
		        	return val;
		        }
		    }
		});
	}
	
	private void sortByPassCatcherDraftZoneScore(List<NFLTeam> teams) {
		Collections.sort(teams, new Comparator<NFLTeam>() {
		    public int compare(NFLTeam a, NFLTeam b) {
		        int val = b.getTrends().getPassCatcherDraftZoneScore().compareTo(a.getTrends().getPassCatcherDraftZoneScore());
		        if (val == 0) {
		        	return a.getTrends().getPassCatcherAverageAdp().compareTo(b.getTrends().getPassCatcherAverageAdp());
		        } else {
		        	return val;
		        }
		    }
		});
	}
	
	
	// If a team has no early-round pass-catchers, but they have multiple going in the middle rounds - target them
	public void buildTrendsForTeam(NFLTeam team) {
		team.setTrends(new NflTrends());
		lookForTeamsWithNoEarlyCatchersButHaveMultipleMiddleRoundCatchers(team);
		calculateTeamDraftZoneScore(team);
		calculateTeamADPAverage(team);
	}

	private void calculateTeamDraftZoneScore(NFLTeam team) {
		int score = 0;
		int passCatcherScore = 0;
		
		Player startingQb = helper.getHighestDraftedPlayerFromPosition(team.getPlayersByPosition(Position.QUARTERBACK)); // 1 QB
		score += getDraftZoneScoreFromPlayer(startingQb);
		
		List<Player> sortedRbs = helper.sortPlayersByADP(team.getPlayersByPosition(Position.RUNNINGBACK)); // 2 RB
		score += getDraftZoneScoreFromPlayer(sortedRbs.get(0));
		score += getDraftZoneScoreFromPlayer(sortedRbs.get(1));
		
		List<Player> sortedWrs = helper.sortPlayersByADP(team.getPlayersByPosition(Position.WIDERECEIVER)); // 3 WR
		score += getDraftZoneScoreFromPlayer(sortedWrs.get(0));
		passCatcherScore += getDraftZoneScoreFromPlayer(sortedWrs.get(0));
		score += getDraftZoneScoreFromPlayer(sortedWrs.get(1));
		passCatcherScore += getDraftZoneScoreFromPlayer(sortedWrs.get(1));
		score += getDraftZoneScoreFromPlayer(sortedWrs.get(2));
		passCatcherScore += getDraftZoneScoreFromPlayer(sortedWrs.get(2));
		
		List<Player> tightends = team.getPlayersByPosition(Position.TIGHTEND);
		helper.sortPlayersByADP(tightends);
		if (tightends.size() > 0) {
			Player startingTe = tightends.get(0); // 1 TE
			score += getDraftZoneScoreFromPlayer(startingTe);
			passCatcherScore += getDraftZoneScoreFromPlayer(startingTe);
		}
		
		team.getTrends().setDraftZoneScore(score);
		team.getTrends().setPassCatcherDraftZoneScore(passCatcherScore);
	}
	
	private int getDraftZoneScoreFromPlayer(Player p) {
		return (p.getTrends() != null && p.getTrends().getDraftZone() != null) 
				? p.getTrends().getDraftZone().getScore() : 0;
	}
	
	private void calculateTeamADPAverage(NFLTeam team) {
		int score = 0;
		int passCatcherScore = 0;
		int count = 0;
		int passCatcherCount = 0;
		int minScore = 1;
		
		Player startingQb = helper.getHighestDraftedPlayerFromPosition(team.getPlayersByPosition(Position.QUARTERBACK)); // 1 QB
		if (startingQb.getTrends().getDraftZone() != null && startingQb.getTrends().getDraftZone().getScore() > minScore) {
			score += Integer.valueOf(startingQb.getRankMetadata().getAdp());
			count++;
		}
		
		List<Player> sortedRbs = helper.sortPlayersByADP(team.getPlayersByPosition(Position.RUNNINGBACK)); // 2 RB
		
		for (Player p : sortedRbs) {
			if (p.getTrends().getDraftZone() != null && p.getTrends().getDraftZone().getScore() > minScore) {
				score += Integer.valueOf(p.getRankMetadata().getAdp());
				count++;
			}
		}
		
		List<Player> sortedWrs = helper.sortPlayersByADP(team.getPlayersByPosition(Position.WIDERECEIVER)); // 3 WR
		
		for (Player p : sortedWrs) {
			if (p.getTrends().getDraftZone() != null && p.getTrends().getDraftZone().getScore() > minScore) {
				score += Integer.valueOf(p.getRankMetadata().getAdp());
				count++;
				passCatcherScore += Integer.valueOf(p.getRankMetadata().getAdp());
				passCatcherCount++;
			}
		}
		
		
		List<Player> tightends = team.getPlayersByPosition(Position.TIGHTEND);
		helper.sortPlayersByADP(tightends);
		if (tightends.size() > 0) {
			Player startingTe = tightends.get(0); // 1 TE
			if (startingTe.getTrends().getDraftZone() != null && startingTe.getTrends().getDraftZone().getScore() > minScore) {
				score += Integer.valueOf(startingTe.getRankMetadata().getAdp());
				count++;
				passCatcherScore += Integer.valueOf(startingTe.getRankMetadata().getAdp());
				passCatcherCount++;
			}
		}
		
		Double avg = (double) ((double)score / count);
		Double rounded = ((double)Math.round(avg * 100))/100;
		
		team.getTrends().setAverageAdp(rounded);
		
		Double pcAvg = (double) ((double)passCatcherScore / passCatcherCount);
		Double pcRounded = ((double)Math.round(pcAvg * 100))/100;
		team.getTrends().setPassCatcherAverageAdp(pcRounded);
	}
	

	private void lookForTeamsWithNoEarlyCatchersButHaveMultipleMiddleRoundCatchers(NFLTeam team) {
		List<Player> early = new ArrayList<Player>();
		List<Player> middle = new ArrayList<Player>();
		for (Player player : team.getPlayersByPosition(Position.WIDERECEIVER)) {
			if (player.getTrends() != null) {
				if (player.getTrends().isInEarlyRounds()) { early.add(player); }
				else if (player.getTrends().isInMiddleRounds()) { middle.add(player); }
			} else {
				System.out.println();
			}
		}
		for (Player player : team.getPlayersByPosition(Position.TIGHTEND)) {
			if (player.getTrends() != null) {
				if (player.getTrends().isInEarlyRounds()) { early.add(player); }
				else if (player.getTrends().isInMiddleRounds()) { middle.add(player); }
			}
		}
		if (early.size() == 0 && middle.size() > 1) {
			System.out.println("TARGET PASS CATCHERS ON " + team.getTeam().getFullName());
			for (Player p : middle) {
				System.out.println("\t" + p.getPlayerName());
				p.getTrendAnalysis().add(new TrendAnalysis("Middle round WR/TE on team with 0 early round WR/TE", 1));
			}
			System.out.println();
		}
	}
	
	
	

}
