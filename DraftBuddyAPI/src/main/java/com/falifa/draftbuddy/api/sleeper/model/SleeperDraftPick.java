package com.falifa.draftbuddy.api.sleeper.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class SleeperDraftPick {

	@JsonProperty("round")
	private Integer round;
	@JsonProperty("roster_id")
	private Object rosterId;
	@JsonProperty("player_id")
	private String playerId;
	@JsonProperty("picked_by")
	private String pickedBy;
	@JsonProperty("pick_no")
	private Integer pickNo;
	@JsonProperty("is_keeper")
	private Object isKeeper;
	@JsonProperty("draft_slot")
	private Integer draftSlot;
	@JsonProperty("draft_id")
	private String draftId;
	@JsonProperty("metadata")
	private SleeperPlayerMetadata metadata;

	public SleeperPlayerMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(SleeperPlayerMetadata metadata) {
		this.metadata = metadata;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Object getRosterId() {
		return rosterId;
	}

	public void setRosterId(Object rosterId) {
		this.rosterId = rosterId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPickedBy() {
		return pickedBy;
	}

	public void setPickedBy(String pickedBy) {
		this.pickedBy = pickedBy;
	}

	public Integer getPickNo() {
		return pickNo;
	}

	public void setPickNo(Integer pickNo) {
		this.pickNo = pickNo;
	}

	public Object getIsKeeper() {
		return isKeeper;
	}

	public void setIsKeeper(Object isKeeper) {
		this.isKeeper = isKeeper;
	}

	public Integer getDraftSlot() {
		return draftSlot;
	}

	public void setDraftSlot(Integer draftSlot) {
		this.draftSlot = draftSlot;
	}

	public String getDraftId() {
		return draftId;
	}

	public void setDraftId(String draftId) {
		this.draftId = draftId;
	}

}