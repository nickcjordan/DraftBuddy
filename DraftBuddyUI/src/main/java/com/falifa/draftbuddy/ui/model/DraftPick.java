package com.falifa.draftbuddy.ui.model;

import com.falifa.draftbuddy.ui.model.player.Player;

public class DraftPick {

	int pick;
	int round;
	int roundIndex;
	Drafter drafter;
	Player player;
	
	public DraftPick(int pick, int round, int roundIndex, Drafter drafter, Player player) {
		super();
		this.pick = pick;
		this.round = round;
		this.roundIndex = roundIndex;
		this.drafter = drafter;
		this.player = player;
	}

	public int getPick() {
		return pick;
	}

	public void setPick(int pick) {
		this.pick = pick;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getRoundIndex() {
		return roundIndex;
	}

	public void setRoundIndex(int roundIndex) {
		this.roundIndex = roundIndex;
	}

	public Drafter getDrafter() {
		return drafter;
	}

	public void setDrafter(Drafter drafter) {
		this.drafter = drafter;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
