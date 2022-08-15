package com.falifa.draftbuddy.api.sleeper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleeperPlayerMetadata {

	@JsonProperty("years_exp")
	private String yearsExp;
	@JsonProperty("team")
	private String team;
	@JsonProperty("status")
	private String status;
	@JsonProperty("sport")
	private String sport;
	@JsonProperty("position")
	private String position;
	@JsonProperty("player_id")
	private String playerId;
	@JsonProperty("number")
	private String number;
	@JsonProperty("news_updated")
	private String newsUpdated;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("injury_status")
	private String injuryStatus;
	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("years_exp")
	public String getYearsExp() {
		return yearsExp;
	}

	@JsonProperty("years_exp")
	public void setYearsExp(String yearsExp) {
		this.yearsExp = yearsExp;
	}

	@JsonProperty("team")
	public String getTeam() {
		return team;
	}

	@JsonProperty("team")
	public void setTeam(String team) {
		this.team = team;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("sport")
	public String getSport() {
		return sport;
	}

	@JsonProperty("sport")
	public void setSport(String sport) {
		this.sport = sport;
	}

	@JsonProperty("position")
	public String getPosition() {
		return position;
	}

	@JsonProperty("position")
	public void setPosition(String position) {
		this.position = position;
	}

	@JsonProperty("player_id")
	public String getPlayerId() {
		return playerId;
	}

	@JsonProperty("player_id")
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@JsonProperty("number")
	public String getNumber() {
		return number;
	}

	@JsonProperty("number")
	public void setNumber(String number) {
		this.number = number;
	}

	@JsonProperty("news_updated")
	public String getNewsUpdated() {
		return newsUpdated;
	}

	@JsonProperty("news_updated")
	public void setNewsUpdated(String newsUpdated) {
		this.newsUpdated = newsUpdated;
	}

	@JsonProperty("last_name")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("last_name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("injury_status")
	public String getInjuryStatus() {
		return injuryStatus;
	}

	@JsonProperty("injury_status")
	public void setInjuryStatus(String injuryStatus) {
		this.injuryStatus = injuryStatus;
	}

	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}