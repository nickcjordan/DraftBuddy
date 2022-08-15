package com.falifa.draftbuddy.api.sleeper.model;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SleeperDraftSettings {

	@JsonProperty("teams")
	private Integer teams;
	@JsonProperty("slots_wr")
	private Integer slotsWr;
	@JsonProperty("slots_te")
	private Integer slotsTe;
	@JsonProperty("slots_rb")
	private Integer slotsRb;
	@JsonProperty("slots_qb")
	private Integer slotsQb;
	@JsonProperty("slots_k")
	private Integer slotsK;
	@JsonProperty("slots_flex")
	private Integer slotsFlex;
	@JsonProperty("slots_def")
	private Integer slotsDef;
	@JsonProperty("rounds")
	private Integer rounds;
	@JsonProperty("reversal_round")
	private Integer reversalRound;
	@JsonProperty("player_type")
	private Integer playerType;
	@JsonProperty("pick_timer")
	private Integer pickTimer;
	@JsonProperty("nomination_timer")
	private Integer nominationTimer;
	@JsonProperty("cpu_autopick")
	private Integer cpuAutopick;
	@JsonProperty("autostart")
	private Integer autostart;
	@JsonProperty("autopause_start_time")
	private Integer autopauseStartTime;
	@JsonProperty("autopause_end_time")
	private Integer autopauseEndTime;
	@JsonProperty("autopause_enabled")
	private Integer autopauseEnabled;
	@JsonProperty("alpha_sort")
	private Integer alphaSort;

	@JsonProperty("teams")
	public Integer getTeams() {
	return teams;
	}

	@JsonProperty("teams")
	public void setTeams(Integer teams) {
	this.teams = teams;
	}

	@JsonProperty("slots_wr")
	public Integer getSlotsWr() {
	return slotsWr;
	}

	@JsonProperty("slots_wr")
	public void setSlotsWr(Integer slotsWr) {
	this.slotsWr = slotsWr;
	}

	@JsonProperty("slots_te")
	public Integer getSlotsTe() {
	return slotsTe;
	}

	@JsonProperty("slots_te")
	public void setSlotsTe(Integer slotsTe) {
	this.slotsTe = slotsTe;
	}

	@JsonProperty("slots_rb")
	public Integer getSlotsRb() {
	return slotsRb;
	}

	@JsonProperty("slots_rb")
	public void setSlotsRb(Integer slotsRb) {
	this.slotsRb = slotsRb;
	}

	@JsonProperty("slots_qb")
	public Integer getSlotsQb() {
	return slotsQb;
	}

	@JsonProperty("slots_qb")
	public void setSlotsQb(Integer slotsQb) {
	this.slotsQb = slotsQb;
	}

	@JsonProperty("slots_k")
	public Integer getSlotsK() {
	return slotsK;
	}

	@JsonProperty("slots_k")
	public void setSlotsK(Integer slotsK) {
	this.slotsK = slotsK;
	}

	@JsonProperty("slots_flex")
	public Integer getSlotsFlex() {
	return slotsFlex;
	}

	@JsonProperty("slots_flex")
	public void setSlotsFlex(Integer slotsFlex) {
	this.slotsFlex = slotsFlex;
	}

	@JsonProperty("slots_def")
	public Integer getSlotsDef() {
	return slotsDef;
	}

	@JsonProperty("slots_def")
	public void setSlotsDef(Integer slotsDef) {
	this.slotsDef = slotsDef;
	}

	@JsonProperty("rounds")
	public Integer getRounds() {
	return rounds;
	}

	@JsonProperty("rounds")
	public void setRounds(Integer rounds) {
	this.rounds = rounds;
	}

	@JsonProperty("reversal_round")
	public Integer getReversalRound() {
	return reversalRound;
	}

	@JsonProperty("reversal_round")
	public void setReversalRound(Integer reversalRound) {
	this.reversalRound = reversalRound;
	}

	@JsonProperty("player_type")
	public Integer getPlayerType() {
	return playerType;
	}

	@JsonProperty("player_type")
	public void setPlayerType(Integer playerType) {
	this.playerType = playerType;
	}

	@JsonProperty("pick_timer")
	public Integer getPickTimer() {
	return pickTimer;
	}

	@JsonProperty("pick_timer")
	public void setPickTimer(Integer pickTimer) {
	this.pickTimer = pickTimer;
	}

	@JsonProperty("nomination_timer")
	public Integer getNominationTimer() {
	return nominationTimer;
	}

	@JsonProperty("nomination_timer")
	public void setNominationTimer(Integer nominationTimer) {
	this.nominationTimer = nominationTimer;
	}

	@JsonProperty("cpu_autopick")
	public Integer getCpuAutopick() {
	return cpuAutopick;
	}

	@JsonProperty("cpu_autopick")
	public void setCpuAutopick(Integer cpuAutopick) {
	this.cpuAutopick = cpuAutopick;
	}

	@JsonProperty("autostart")
	public Integer getAutostart() {
	return autostart;
	}

	@JsonProperty("autostart")
	public void setAutostart(Integer autostart) {
	this.autostart = autostart;
	}

	@JsonProperty("autopause_start_time")
	public Integer getAutopauseStartTime() {
	return autopauseStartTime;
	}

	@JsonProperty("autopause_start_time")
	public void setAutopauseStartTime(Integer autopauseStartTime) {
	this.autopauseStartTime = autopauseStartTime;
	}

	@JsonProperty("autopause_end_time")
	public Integer getAutopauseEndTime() {
	return autopauseEndTime;
	}

	@JsonProperty("autopause_end_time")
	public void setAutopauseEndTime(Integer autopauseEndTime) {
	this.autopauseEndTime = autopauseEndTime;
	}

	@JsonProperty("autopause_enabled")
	public Integer getAutopauseEnabled() {
	return autopauseEnabled;
	}

	@JsonProperty("autopause_enabled")
	public void setAutopauseEnabled(Integer autopauseEnabled) {
	this.autopauseEnabled = autopauseEnabled;
	}

	@JsonProperty("alpha_sort")
	public Integer getAlphaSort() {
	return alphaSort;
	}

	@JsonProperty("alpha_sort")
	public void setAlphaSort(Integer alphaSort) {
	this.alphaSort = alphaSort;
	}

	
}
