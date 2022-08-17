package com.falifa.draftbuddy.api.sleeper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SleeperUser {

	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("settings")
	private Object settings;
	@JsonProperty("league_id")
	private String leagueId;
	@JsonProperty("is_owner")
	private Object isOwner;
	@JsonProperty("is_bot")
	private Boolean isBot;
	@JsonProperty("display_name")
	private String displayName;
	@JsonProperty("avatar")
	private String avatar;
	@JsonProperty("metadata")
	private SleeperUserMetadata metadata;
	
	public SleeperUserMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(SleeperUserMetadata metadata) {
		this.metadata = metadata;
	}

	private Integer pickPosition;

	public Integer getPickPosition() {
		return pickPosition;
	}

	public void setPickPosition(Integer pickPosition) {
		this.pickPosition = pickPosition;
	}

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public SleeperUser() {
	}
	
	public SleeperUser(String displayName, Integer pickPosition) {
		super();
		this.pickPosition = pickPosition;
		this.isBot = true;
		this.displayName = displayName;
	}

	/**
	 *
	 * @param settings
	 * @param isBot
	 * @param metadata
	 * @param isOwner
	 * @param leagueId
	 * @param displayName
	 * @param avatar
	 * @param userId
	 */
	public SleeperUser(String userId, String leagueId, Object isOwner,
			Boolean isBot, String displayName, String avatar) {
		super();
		this.userId = userId;
		this.leagueId = leagueId;
		this.isOwner = isOwner;
		this.isBot = isBot;
		this.displayName = displayName;
		this.avatar = avatar;
	}

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("user_id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("settings")
	public Object getSettings() {
		return settings;
	}

	@JsonProperty("settings")
	public void setSettings(Object settings) {
		this.settings = settings;
	}

	@JsonProperty("league_id")
	public String getLeagueId() {
		return leagueId;
	}

	@JsonProperty("league_id")
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	@JsonProperty("is_owner")
	public Object getIsOwner() {
		return isOwner;
	}

	@JsonProperty("is_owner")
	public void setIsOwner(Object isOwner) {
		this.isOwner = isOwner;
	}

	@JsonProperty("is_bot")
	public Boolean getIsBot() {
		return isBot;
	}

	@JsonProperty("is_bot")
	public void setIsBot(Boolean isBot) {
		this.isBot = isBot;
	}

	@JsonProperty("display_name")
	public String getDisplayName() {
		return displayName;
	}

	@JsonProperty("display_name")
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@JsonProperty("avatar")
	public String getAvatar() {
		return avatar;
	}

	@JsonProperty("avatar")
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


}