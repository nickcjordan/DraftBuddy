package com.falifa.draftbuddy.ui.model;

public class DraftPick {

	int pick;
	int round;
	Drafter drafter;
	Player player;
	
	public DraftPick(int pick, int round, Drafter drafter, Player player) {
		super();
		this.pick = pick;
		this.round = round;
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
