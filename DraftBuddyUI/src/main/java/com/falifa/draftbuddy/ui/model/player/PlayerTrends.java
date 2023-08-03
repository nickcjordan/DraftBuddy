package com.falifa.draftbuddy.ui.model.player;

import com.falifa.draftbuddy.ui.constants.DraftZone;

public class PlayerTrends {
	
	public boolean hasRookieQb;

	public Integer positionMatesInEarlyRounds;
	public Integer positionMatesInDeadZone;
	public Integer positionMatesInMiddleRounds;
	public Integer positionMatesInLateRounds;
	
	// 1st, 2nd, 3rd, etc within position based on ADP
	public Integer depthChartPosition;
	
	public boolean isInEarlyRounds = false;
	public boolean isInDeadZone = false;
	public boolean isInMiddleRounds = false;
	public boolean isInLateRounds = false;
	// EARLY, DEADZONE, MIDDLE, LATE
	public DraftZone draftZone;
	
	public Integer getDepthChartPosition() {
		return depthChartPosition;
	}
	public void setDepthChartPosition(Integer depthChartPosition) {
		this.depthChartPosition = depthChartPosition;
	}
	public boolean isHasRookieQb() {
		return hasRookieQb;
	}
	public void setHasRookieQb(boolean hasRookieQb) {
		this.hasRookieQb = hasRookieQb;
	}
	public Integer getPositionMatesInEarlyRounds() {
		return positionMatesInEarlyRounds;
	}
	public void setPositionMatesInEarlyRounds(Integer positionMatesInEarlyRounds) {
		this.positionMatesInEarlyRounds = positionMatesInEarlyRounds;
	}
	public Integer getPositionMatesInDeadZone() {
		return positionMatesInDeadZone;
	}
	public void setPositionMatesInDeadZone(Integer positionMatesInDeadZone) {
		this.positionMatesInDeadZone = positionMatesInDeadZone;
	}
	public Integer getPositionMatesInMiddleRounds() {
		return positionMatesInMiddleRounds;
	}
	public void setPositionMatesInMiddleRounds(Integer positionMatesInMiddleRounds) {
		this.positionMatesInMiddleRounds = positionMatesInMiddleRounds;
	}
	public Integer getPositionMatesInLateRounds() {
		return positionMatesInLateRounds;
	}
	public void setPositionMatesInLateRounds(Integer positionMatesInLateRounds) {
		this.positionMatesInLateRounds = positionMatesInLateRounds;
	}
	public boolean isInEarlyRounds() {
		return isInEarlyRounds;
	}
	public void setInEarlyRounds(boolean isInEarlyRounds) {
		this.isInEarlyRounds = isInEarlyRounds;
	}
	public boolean isInDeadZone() {
		return isInDeadZone;
	}
	public void setInDeadZone(boolean isInDeadZone) {
		this.isInDeadZone = isInDeadZone;
	}
	public boolean isInMiddleRounds() {
		return isInMiddleRounds;
	}
	public void setInMiddleRounds(boolean isInMiddleRounds) {
		this.isInMiddleRounds = isInMiddleRounds;
	}
	public boolean isInLateRounds() {
		return isInLateRounds;
	}
	public void setInLateRounds(boolean isInLateRounds) {
		this.isInLateRounds = isInLateRounds;
	}
	public DraftZone getDraftZone() {
		return draftZone;
	}
	public void setDraftZone(DraftZone draftZone) {
		this.draftZone = draftZone;
	}
	
	

}
