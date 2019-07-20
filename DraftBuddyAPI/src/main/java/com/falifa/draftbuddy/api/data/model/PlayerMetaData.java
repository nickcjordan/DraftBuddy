package com.falifa.draftbuddy.api.data.model;

import com.falifa.draftbuddy.api.data.deserializer.PlayerMetaDataDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PlayerMetaDataDeserializer.class)
public class PlayerMetaData {
	
	private String playerId;
	private String name;
	private String position;
	private String nflTeamAbbr;
	private String nflTeamId;
	private String imageUrl;
	private String smallImageUrl;
	private String byeWeek;
	
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getNflTeamAbbr() {
		return nflTeamAbbr;
	}
	public void setNflTeamAbbr(String nflTeamAbbr) {
		this.nflTeamAbbr = nflTeamAbbr;
	}
	public String getNflTeamId() {
		return nflTeamId;
	}
	public void setNflTeamId(String nflTeamId) {
		this.nflTeamId = nflTeamId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSmallImageUrl() {
		return smallImageUrl;
	}
	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}
	public String getByeWeek() {
		return byeWeek;
	}
	public void setByeWeek(String byeWeek) {
		this.byeWeek = byeWeek;
	}
	
}
