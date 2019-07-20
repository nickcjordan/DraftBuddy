package com.falifa.draftbuddy.api.model;

import java.util.HashMap;
import java.util.Map;

public class Player {
	
	private String playerId;
	private String playerPosition;
	private boolean isOnUserTeam;
	private String playerName;
	private String teamName;
	private String playerRank;
	private String positionRank;
	private String byeWeek;
	private String nflTeamId;
	private String imageUrl;
	private String smallImageUrl;
	private Map<String, PositionStatsDetails> statsByWeek;
	private Map<String, ModifiedStats> modifiedStats;
	
	public Player() {
		this.statsByWeek = new HashMap<String, PositionStatsDetails>();
		this.modifiedStats = new HashMap<String, ModifiedStats>();
	}

	public Player(String id) {
		this.statsByWeek = new HashMap<String, PositionStatsDetails>();
		this.modifiedStats = new HashMap<String, ModifiedStats>();
		this.playerId = id;
	}
	public String getPlayerId() {
		return playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public String getPlayerPosition() {
		return playerPosition;
	}
	public boolean isOnUserTeam() {
		return isOnUserTeam;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public String getPlayerRank() {
		return playerRank;
	}
	
	public String getPositionRank() {
		return positionRank;
	}
	
	public String getByeWeek() {
		return byeWeek;
	}
	
	public String getNflTeamId() {
		return nflTeamId;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getSmallImageUrl() {
		return smallImageUrl;
	}
	
	public Map<String, PositionStatsDetails> getStatsByWeek() {
		return statsByWeek;
	}
	
	public Map<String, ModifiedStats> getModifiedStats() {
		return modifiedStats;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public void setPlayerPosition(String position) {
		this.playerPosition = position;
	}

	public void setOnUserTeam(boolean isOnUserTeam) {
		this.isOnUserTeam = isOnUserTeam;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setPlayerRank(String playerRank) {
		this.playerRank = playerRank;
	}

	public void setPositionRank(String positionRank) {
		this.positionRank = positionRank;
	}

	public void setByeWeek(String byeWeek) {
		this.byeWeek = byeWeek;
	}

	public void setNflTeamId(String nflTeamId) {
		this.nflTeamId = nflTeamId;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public void setStatsByWeek(Map<String, PositionStatsDetails> statsByWeek) {
		this.statsByWeek = statsByWeek;
	}

	public void setModifiedStats(Map<String, ModifiedStats> modifiedStats) {
		this.modifiedStats = modifiedStats;
	}
	
	
}
