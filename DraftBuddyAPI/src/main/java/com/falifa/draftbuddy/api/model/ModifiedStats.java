package com.falifa.draftbuddy.api.model;

public class ModifiedStats {

	private String weekNumber;
	private double oldFantasyPointTotal;
	private double newFantasyPointTotal;
	private double finalPointTotal;
	
	public ModifiedStats() {}
	
	public ModifiedStats(String weekNumber, double oldFantasyPointTotal) {
		this.weekNumber = weekNumber;
		this.oldFantasyPointTotal = oldFantasyPointTotal;
	}
	
	public String getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(String weekNumber) {
		this.weekNumber = weekNumber;
	}
	public double getOldFantasyPointTotal() {
		return oldFantasyPointTotal;
	}
	public void setOldFantasyPointTotal(double oldFantasyPointTotal) {
		this.oldFantasyPointTotal = oldFantasyPointTotal;
	}
	public double getNewFantasyPointTotal() {
		return newFantasyPointTotal;
	}
	public void setNewFantasyPointTotal(double newFantasyPointTotal) {
		this.newFantasyPointTotal = newFantasyPointTotal;
	}
	public double getFinalPointTotal() {
		return finalPointTotal;
	}
	public void setFinalPointTotal(double finalPointTotal) {
		this.finalPointTotal = finalPointTotal;
	}
	
}
