package com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers;

import com.falifa.draftbuddy.ui.constants.Position;

public class FFBallersConsolidatedProjection {

	private String playerId;
	private Position position;
	private Integer overallPoints;
	private Double avgPoints;
	private Integer positionRank;
	private Integer passingTouchdowns;
	private Integer passingYards;
	private Integer rushingYards;
	private Integer rushingTouchdowns;
	private Integer receptions;
	private Integer receivingYards;
	private Integer receivingTouchdowns;
	private Integer interceptionsThrown;
	private Integer fumblesLost;
	private Integer risk;
	private String name;
	private String blurb;

	public Double getAvgPoints() {
		return avgPoints;
	}

	public void setAvgPoints(Double avgPoints) {
		this.avgPoints = avgPoints;
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Integer getOverallPoints() {
		return overallPoints;
	}

	public void setOverallPoints(Integer overallPoints) {
		this.overallPoints = overallPoints;
	}

	public Integer getPassingYards() {
		return passingYards;
	}

	public void setPassingYards(Integer passingYards) {
		this.passingYards = passingYards;
	}

	public String getBlurb() {
		return blurb;
	}

	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}

	public String getDynastyBlurb() {
		return dynastyBlurb;
	}

	public void setDynastyBlurb(String dynastyBlurb) {
		this.dynastyBlurb = dynastyBlurb;
	}

	private String dynastyBlurb;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public Integer getPassingTouchdowns() {
		return passingTouchdowns;
	}

	public void setPassingTouchdowns(Integer passingTouchdowns) {
		this.passingTouchdowns = passingTouchdowns;
	}

	public Integer getRushingYards() {
		return rushingYards;
	}

	public void setRushingYards(Integer rushingYards) {
		this.rushingYards = rushingYards;
	}

	public Integer getRushingTouchdowns() {
		return rushingTouchdowns;
	}

	public void setRushingTouchdowns(Integer rushingTouchdowns) {
		this.rushingTouchdowns = rushingTouchdowns;
	}

	public Integer getReceptions() {
		return receptions;
	}

	public void setReceptions(Integer receptions) {
		this.receptions = receptions;
	}

	public Integer getReceivingYards() {
		return receivingYards;
	}

	public void setReceivingYards(Integer receivingYards) {
		this.receivingYards = receivingYards;
	}

	public Integer getReceivingTouchdowns() {
		return receivingTouchdowns;
	}

	public void setReceivingTouchdowns(Integer receivingTouchdowns) {
		this.receivingTouchdowns = receivingTouchdowns;
	}

	public Integer getInterceptionsThrown() {
		return interceptionsThrown;
	}

	public void setInterceptionsThrown(Integer interceptionsThrown) {
		this.interceptionsThrown = interceptionsThrown;
	}

	public Integer getFumblesLost() {
		return fumblesLost;
	}

	public void setFumblesLost(Integer fumblesLost) {
		this.fumblesLost = fumblesLost;
	}

	public Integer getRisk() {
		return risk;
	}

	public void setRisk(Integer risk) {
		this.risk = risk;
	}

	public Integer getPositionRank() {
		return positionRank;
	}

	public void setPositionRank(Integer positionRank) {
		this.positionRank = positionRank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
