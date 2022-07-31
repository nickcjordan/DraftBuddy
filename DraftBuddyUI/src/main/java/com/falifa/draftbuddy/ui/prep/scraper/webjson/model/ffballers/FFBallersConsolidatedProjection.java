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
	private int flexRank;
	private int flexGrade;
	private String flexGradeBadgeClass;

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

	public int getFlexRank() {
		return flexRank;
	}

	public void setFlexRank(int flexRank) {
		this.flexRank = flexRank;
	}

	public int getFlexGrade() {
		return flexGrade;
	}

	public void setFlexGrade(int flexGrade) {
		this.flexGrade = flexGrade;
		this.setFlexGradeBadgeClass(getGradeBadgeClass(flexGrade)); 
	}
	
	private static String getGradeBadgeClass(int value) {
		if (value == 0) { return "badge-empty";
		} else if (value <= 20) { return "badge-graded-darkest-red";
		} else if (value <= 24) { return "badge-graded-darker-red";
		} else if (value <= 28) { return "badge-graded-dark-red";
		} else if (value <= 32) { return "badge-graded-darkish-red";
		} else if (value <= 36) { return "badge-graded-red";
		} else if (value <= 40) { return "badge-graded-light-red";
		} else if (value <= 44) { return "badge-graded-lighter-red";
		} else if (value <= 48) { return "badge-graded-lightest-red";
		} else if (value <= 52) { return "badge-graded-white";
		} else if (value <= 56) { return "badge-graded-lightest-green";
		} else if (value <= 60) { return "badge-graded-lighter-green";
		} else if (value <= 63) { return "badge-graded-light-green";
		} else if (value <= 66) { return "badge-graded-green";
		} else if (value <= 69) { return "badge-graded-darkish-green";
		} else if (value <= 72) { return "badge-graded-dark-green";
		} else if (value <= 75) { return "badge-graded-darker-green";
		} else { return "badge-graded-darkest-green";
		}
	}

	public String getFlexGradeBadgeClass() {
		return flexGradeBadgeClass;
	}

	public void setFlexGradeBadgeClass(String flexGradeBadgeClass) {
		this.flexGradeBadgeClass = flexGradeBadgeClass;
	}

}
