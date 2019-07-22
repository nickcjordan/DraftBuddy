package com.falifa.draftbuddy.ui.results;

import java.util.List;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.exception.FalifaException;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.NFL;
import com.falifa.draftbuddy.ui.model.player.Player;

public class DraftResultStatistics {

	private Drafter drafter;
	private int averageOverallRank;
	private int qbRank;
	private int rbRank;
	private int wrRank;
	private int teRank;
	private int kRank;
	private int dstRank;
	private boolean draftedHandcuff;

	public DraftResultStatistics(Drafter drafter) {
		super();
		this.drafter = drafter;
		this.averageOverallRank = findAverageOverallRank(drafter.getDraftedTeam().getPositionLists());
		this.draftedHandcuff = findIfDraftedHandcuff();
	}

	private boolean findIfDraftedHandcuff() {
		List<Player> rbs = drafter.getDraftedTeam().getPlayersByPosition(Position.RUNNINGBACK);
		try { 
			Player handcuff = NFL.getPlayer(rbs.get(0).getHandcuff());
			return ((handcuff != null) && (rbs.contains(handcuff)));
		} catch (Exception e) {return false;}
	}

	private int findAverageOverallRank(List<List<Player>> positions) {
		int sum = 0;
		for (List<Player> position : positions) {
			if (position.isEmpty()) continue;
			int positionAverage = findPositionAv(position);
			sum += positionAverage;
			setPosRank(positionAverage, position.get(0).getPosition());
		}
		return (sum/positions.size());
	}

	private int findPositionAv(List<Player> position) {
		int posSum = 0;
		for (Player player : position) {
			posSum += Integer.parseInt(player.getPos_rank());
		}
		return (position.size() > 0) ?(posSum/position.size()) : 0;
	}

	private void setPosRank(int posAv, Position pos) {
		switch (pos) {
		case QUARTERBACK 		: this.qbRank = posAv; break;
		case RUNNINGBACK 		: this.rbRank = posAv; break;
		case WIDERECEIVER 		: this.wrRank = posAv; break;
		case TIGHTEND			: this.teRank = posAv; break;
		case KICKER				 	: this.kRank = posAv; break;
		case DEFENSE			 	: this.dstRank = posAv; break;
		default: break;
		}
	}

	public Drafter getDrafter() {
		return drafter;
	}

	public int getAverageOverallRank() {
		return averageOverallRank;
	}

	public int getQbRank() {
		return qbRank;
	}

	public int getRbRank() {
		return rbRank;
	}

	public int getWrRank() {
		return wrRank;
	}

	public int getTeRank() {
		return teRank;
	}

	public int getkRank() {
		return kRank;
	}

	public int getDstRank() {
		return dstRank;
	}

	public boolean draftedHandcuff() {
		return draftedHandcuff;
	}
	
}
