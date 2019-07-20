package com.draftbuddy.model;

import java.util.List;

public class RoundSpecificStrategy {
	
	private String round;
	private String strategyText;
	private String targetPositions;
	private List<String> targetPlayers;
	
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getStrategyText() {
		return strategyText;
	}
	public void setStrategyText(String strategyText) {
		this.strategyText = strategyText;
	}
	public List<String> getTargetPlayers() {
		return targetPlayers;
	}
	public void setTargetPlayers(List<String> targetPlayers) {
		this.targetPlayers = targetPlayers;
	}
	public String getTargetPositions() {
		return targetPositions;
	}
	public void setTargetPositions(String targetPositions) {
		this.targetPositions = targetPositions;
	}

}
