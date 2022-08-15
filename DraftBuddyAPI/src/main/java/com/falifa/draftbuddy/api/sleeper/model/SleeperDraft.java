package com.falifa.draftbuddy.api.sleeper.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleeperDraft {

	@JsonProperty("status")
	private String status;
	@JsonProperty("start_time")
	private Long startTime;
	@JsonProperty("slot_to_roster_id")
	private Map<String, String> slotToRosterId;
	@JsonProperty("league_id")
	private String leagueId;
	@JsonProperty("last_picked")
	private Long lastPicked;
	@JsonProperty("last_message_time")
	private Long lastMessageTime;
	@JsonProperty("last_message_id")
	private String lastMessageId;
	@JsonProperty("draft_order")
	private Map<String, String> draftOrder;
	@JsonProperty("draft_id")
	private String draftId;
	@JsonProperty("created")
	private Long created;
	@JsonProperty("settings")
	private SleeperDraftSettings settings;


	public SleeperDraftSettings getSettings() {
		return settings;
	}

	public void setSettings(SleeperDraftSettings settings) {
		this.settings = settings;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("start_time")
	public Long getStartTime() {
		return startTime;
	}

	@JsonProperty("start_time")
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	@JsonProperty("slot_to_roster_id")
	public Map<String, String> getSlotToRosterId() {
		return slotToRosterId;
	}

	@JsonProperty("slot_to_roster_id")
	public void setSlotToRosterId(Map<String, String> slotToRosterId) {
		this.slotToRosterId = slotToRosterId;
	}

	@JsonProperty("league_id")
	public String getLeagueId() {
		return leagueId;
	}

	@JsonProperty("league_id")
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	@JsonProperty("last_picked")
	public Long getLastPicked() {
		return lastPicked;
	}

	@JsonProperty("last_picked")
	public void setLastPicked(Long lastPicked) {
		this.lastPicked = lastPicked;
	}

	@JsonProperty("last_message_time")
	public Long getLastMessageTime() {
		return lastMessageTime;
	}

	@JsonProperty("last_message_time")
	public void setLastMessageTime(Long lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}

	@JsonProperty("last_message_id")
	public String getLastMessageId() {
		return lastMessageId;
	}

	@JsonProperty("last_message_id")
	public void setLastMessageId(String lastMessageId) {
		this.lastMessageId = lastMessageId;
	}

	@JsonProperty("draft_order")
	public Map<String, String> getDraftOrder() {
		return draftOrder;
	}

	@JsonProperty("draft_order")
	public void setDraftOrder(Map<String, String> draftOrder) {
		this.draftOrder = draftOrder;
	}

	@JsonProperty("draft_id")
	public String getDraftId() {
		return draftId;
	}

	@JsonProperty("draft_id")
	public void setDraftId(String draftId) {
		this.draftId = draftId;
	}

	@JsonProperty("created")
	public Long getCreated() {
		return created;
	}

	@JsonProperty("created")
	public void setCreated(Long created) {
		this.created = created;
	}

}
